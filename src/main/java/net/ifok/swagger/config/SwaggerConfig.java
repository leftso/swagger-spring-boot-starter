/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ifok.swagger.config;

import lombok.extern.slf4j.Slf4j;
import net.ifok.swagger.model.SwaggerProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * @Description:   spring fox swagger ?????????????????????????????????UI?????????
 * 
 * @author leftso
 * @date 2020/7/17 9:38
 */
@Configuration
@EnableSwagger2WebMvc
@Slf4j
public class SwaggerConfig implements BeanFactoryAware {
    public final static String DOCKET_POST_SUFFIX="_API_DOCKET_NAME";
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    public SwaggerConfig(){
      log.info("SwaggerConfig init ....");
    }

    @Autowired
    SwaggerProperties swaggerProperties;

    /**
     * ???????????????
     * @return
     */
    private Docket defaultDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //????????????
        docket = docket.enable(swaggerProperties.getEnabled());
        docket = docket.apiInfo(getApiInfo(swaggerProperties.getApiTitle(),swaggerProperties.getApiDescription(),swaggerProperties.getTermsOfServiceUrl(),
                swaggerProperties.getApiVersion(),swaggerProperties.getLicense(),swaggerProperties.getLicenseUrl(),
                swaggerProperties.getContact()));
        //??????????????????
        if (!StringUtils.isEmpty(swaggerProperties.getUrlPattern())){
            docket=docket.select().paths(PathSelectors.ant(swaggerProperties.getUrlPattern())).build();
        }
        //???????????????
        if (Objects.nonNull(swaggerProperties.getPackages())&&swaggerProperties.getPackages().length>0){
            docket=docket.select().apis(basePackage(Arrays.asList(swaggerProperties.getPackages()))).build();
        }
        return docket;
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> apiDockets(){
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> dockets=new ArrayList<>();
        if (!swaggerProperties.getEnabled()){
            //???????????????

            Docket docket = new Docket(DocumentationType.SWAGGER_2);
            //????????????
            docket = docket.enable(swaggerProperties.getEnabled());
            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            dockets.add(docket);
            return dockets;
        }
        Map<String, SwaggerProperties.DocketInfo> group = swaggerProperties.getGroup();
        if (CollectionUtils.isEmpty(group)){
            //????????????
            Docket docket = defaultDocket();
            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            dockets.add(docket);
        }

        //???????????????
        Set<String> groupKeys = group.keySet();
        for (String groupKey : groupKeys) {
            SwaggerProperties.DocketInfo docketInfo = group.get(groupKey);
            Docket docket = getDocket(docketInfo,groupKey);
            configurableBeanFactory.registerSingleton(groupKey+DOCKET_POST_SUFFIX,docket);
            dockets.add(docket);
        }
        return dockets;
    }

    /**
     * ??????docket
     * @param docketInfo
     * @return
     */
    private Docket getDocket(SwaggerProperties.DocketInfo docketInfo,String groupName){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        String docketInfoGroupName = docketInfo.getGroupName();
        if (StringUtils.isEmpty(docketInfoGroupName)){
            docketInfoGroupName=groupName;
        }
        docket.groupName(docketInfoGroupName);
        docket = docket.apiInfo(getApiInfo(docketInfo.getApiTitle(),docketInfo.getApiDescription(),
                docketInfo.getTermsOfServiceUrl(), docketInfo.getApiVersion(),
                docketInfo.getLicense(),docketInfo.getLicenseUrl(),
                docketInfo.getContact()));
        //??????????????????
        if (!StringUtils.isEmpty(docketInfo.getUrlPattern())){
            docket=docket.select().paths(PathSelectors.ant(docketInfo.getUrlPattern())).build();
        }
        //???????????????
        if (Objects.nonNull(docketInfo.getPackages())&&docketInfo.getPackages().length>0){
            docket=docket.select().apis(basePackage(Arrays.asList(docketInfo.getPackages()))).build();
        }
        return docket;
    }

    /**
     * api ??????
     * @param title
     * @param description
     * @param termsOfServiceUrl
     * @param version
     * @param license
     * @param licenseUrl
     * @param contact
     * @return
     */
    private ApiInfo getApiInfo(String title, String description, String termsOfServiceUrl, String version, String license,
                               String licenseUrl, SwaggerProperties.Contact contact){
        return new ApiInfoBuilder().title(title)
                .description(description)
                /***
                 * ??????url??????????????????ip:port
                 **/
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .contact(new Contact(
                        contact.getName(),
                        contact.getUrl(),
                        contact.getEmail())
                )
                .build();
    }


    /**----------------------???????????????????????????????????????-----------------**/
    public static Predicate<RequestHandler> basePackage(final List<String> packages) {
        return input -> declaringClass(input).map(handlerPackage(packages)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final List<String> packages)     {
        return input -> {
            // ??????????????????
            for (String strPackage : packages) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        //?????????????????????
        //noinspection Guava
        return Optional.ofNullable(input.declaringClass());
    }
    /**----------------------???????????????????????????????????????-----------------**/


    /**
     * UI????????????
     *
     * ??????????????????spring.swagger.config.enabledUi=true?????????????????????
     * @return
     */
    @Bean
    UiConfiguration swaggerUIConfig() {
        // @formatter:off
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(0)
                .defaultModelExpandDepth(Integer.MAX_VALUE)
                .defaultModelRendering(ModelRendering.MODEL)
                /**
                 * ?????????????????????????????????
                 */
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.LIST)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null).build();
        // @formatter:on
    }


}
