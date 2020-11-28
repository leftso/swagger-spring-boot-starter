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
