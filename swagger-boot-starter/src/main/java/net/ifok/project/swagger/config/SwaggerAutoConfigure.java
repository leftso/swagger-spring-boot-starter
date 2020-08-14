package net.ifok.project.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * @description TODO
 * 
 * @author lee
 * @date 2020/8/14 22:44
 **/
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ComponentScan(basePackages = "net.ifok.project.swagger.config")
@ConditionalOnMissingBean(SwaggerAutoConfigure.class)
@ConditionalOnProperty(prefix = "spring.swagger.config",value = "enabled",havingValue = "true")
public class SwaggerAutoConfigure {
    private final SwaggerProperties swaggerProperties;

    @Autowired
    public SwaggerAutoConfigure(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }


}
