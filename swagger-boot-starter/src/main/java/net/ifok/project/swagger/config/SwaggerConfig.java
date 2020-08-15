package net.ifok.project.swagger.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


/**
 * @Description:   spring fox swagger 配置，分为数据配置项和UI配置项
 * 
 * @author lee
 * @date 2020/7/17 9:38
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
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
        return new ApiInfoBuilder().title(swaggerProperties.getUiTitle())
                .description(swaggerProperties.getUiDescription())
                /***
                 * 将“url”换成自己的ip:port
                 **/
                .termsOfServiceUrl(swaggerProperties.getUiUrl())
                .version(swaggerProperties.getUiVersion())
                .build();
    }


    /**----------------------下面部分处理多个包扫描问题-----------------**/
    public static Predicate<RequestHandler> basePackage(final List<String> packages) {
        return input -> declaringClass(input).transform(handlerPackage(packages)).or(true);
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

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
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
