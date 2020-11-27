package net.ifok.project.swagger;

import lombok.extern.slf4j.Slf4j;
import net.ifok.project.swagger.config.SwaggerConfig;
import net.ifok.project.swagger.model.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description TODO
 * 
 * @author lee
 * @date 2020/8/14 22:44
 **/
@Configuration
@Import({SwaggerProperties.class, SwaggerConfig.class})
@ConditionalOnMissingBean(SwaggerAutoConfigure.class)
@Slf4j
public class SwaggerAutoConfigure {


    @Autowired
    public SwaggerAutoConfigure() {
        log.info("SwaggerAutoConfigure init ...");
    }


}
