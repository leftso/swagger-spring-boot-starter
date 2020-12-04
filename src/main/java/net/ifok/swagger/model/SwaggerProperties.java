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
package net.ifok.swagger.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description swagger 配置文件
 * 
 * @author leftso
 * @date 2020/8/14 22:13
 **/
@Component
@ConfigurationProperties(prefix = "spring.swagger.config")
@Data
@Slf4j
public class SwaggerProperties {

    public SwaggerProperties(){
        log.debug("SwaggerProperties init ....");
    }
    /**
     * 启用swagger api
     */
    private Boolean enabled=true;
    /***
     * 需要生成文档的 api地址，例如：/api/**,默认/** 所有url都检查
     */
    private String urlPattern="/**";
    /**
     * 需要生成文档的controller包路径，例如com.demo.controller
     */
    private List<String> packages;
    /***
     * api文档名称
     */
    private String apiTitle ="API开放文档";
    /**
     * api文档详细描述
     */
    private String apiDescription ="Api 接口说明";
    /***
     * api接口地址
     */
    private String apiUrl ="http://localhost:8080/swagger-ui.html";
    /***
     * api版本号
     */
    private String apiVersion ="1.0.0";
    /**
     * 版权信息
     */
    private String license;
    /**
     * 版权连接
     */
    private String licenseUrl;
    /**
     * 联系信息
     */
    private Contact contact=new Contact();

    @Data
    public  class  Contact{
        /**
         * 联系人名
         */
        private String name;
        /**
         * 联系网站
         */
        private String url;
        /**
         * 联系人邮箱
         */
        private String email;
    }


}
