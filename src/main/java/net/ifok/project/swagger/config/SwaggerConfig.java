package net.ifok.project.swagger.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
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
    @Value("${api.document.enabled}")
    boolean enabled;
    /**
     * 数据配置部分
     * @return
     */
    @Bean
    public Docket swaggerDataConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                /**
                 * 是否启用文档
                 */
                .enable(enabled)
                /***
                 * 文档信息
                 */
                .apiInfo(apiInfo())
                .select()
                /***
                 * 扫描需要生成api文档的包
                 */
                /***
                 * 默认提供扫描单个包
                 */
//                .apis(RequestHandlerSelectors.basePackage("net.ifok.project.swagger"))
                /***
                 * 扩展支持多个包
                 */
                .apis(basePackage(Arrays.asList("net.ifok.project.swagger.controller")))
                /**
                 * 包撒选后再进行api url 赛选
                 */
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("开放API接口文档")
                .description("接口文档")
                /***
                 * 将“url”换成自己的ip:port
                 **/
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                .version("1.0.0")
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
