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
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * @Description:   spring fox swagger 配置，分为数据配置项和UI配置项
 * 
 * @author leftso
 * @date 2020/7/17 9:38
 */
@Configuration
@EnableSwagger2WebMvc
@Slf4j
public class SwaggerConfig {
    public SwaggerConfig(){
      log.info("SwaggerConfig init ....");
    }
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    SwaggerProperties swaggerProperties;
    /**
     * 数据配置部分
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Docket swaggerDataConfig(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //是否启用
        docket = docket.enable(swaggerProperties.getEnabled());
        docket = docket.apiInfo(apiInfo());
        //设置路径匹配
        if (!StringUtils.isEmpty(swaggerProperties.getUrlPattern())){
            docket=docket.select().paths(PathSelectors.ant(swaggerProperties.getUrlPattern())).build();
        }
        //设置包配置
        if (!CollectionUtils.isEmpty(swaggerProperties.getPackages())){
            docket=docket.select().apis(basePackage(swaggerProperties.getPackages())).build();
        }
        return docket;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title(swaggerProperties.getApiTitle())
                .description(swaggerProperties.getApiDescription())
                /***
                 * 将“url”换成自己的ip:port
                 **/
                .termsOfServiceUrl(swaggerProperties.getApiUrl())
                .version(swaggerProperties.getApiVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(
                        swaggerProperties.getContact().getName(),
                        swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail())
                )
                .build();
    }


    /**----------------------下面部分处理多个包扫描问题-----------------**/
    public static Predicate<RequestHandler> basePackage(final List<String> packages) {
        return input -> declaringClass(input).map(handlerPackage(packages)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final List<String> packages)     {
        return input -> {
            // 循环判断匹配
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
        //此版本忽略警告
        //noinspection Guava
        return Optional.ofNullable(input.declaringClass());
    }
    /**----------------------上面部分处理多个包扫描问题-----------------**/


    /**
     * UI配置部分
     *
     * 当配置文件中spring.swagger.config.enabledUi=true时。该配置生效
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
                 * 展示请求接口的响应时间
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
