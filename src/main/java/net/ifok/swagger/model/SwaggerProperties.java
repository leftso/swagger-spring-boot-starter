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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description swagger 配置文件
 * 
 * @author leftso
 * @date 2020/8/14 22:13
 **/
@Component
@ConfigurationProperties(prefix = "spring.swagger")
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
    /**
     * 文档路径，默认/v2/api-docs
     */
    private String docPath ="/v2/api-docs";
    /***
     * 需要生成文档的 api地址，例如：/api/**,默认/** 所有url都检查
     */
    private String urlPattern="/**";
    /**
     * 需要生成文档的controller包路径，例如com.demo.controller
     */
    private String [] packages;
    /***
     * api文档名称
     */
    private String apiTitle ="API开放文档";
    /**
     * api文档详细描述
     */
    private String apiDescription ="Api 接口说明";
    /***
     * 服务条款URL
     */
    private String termsOfServiceUrl ="";
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
    /***
     * 全局参数
     */
    private List<GlobalParam> globalParams;
    /**
     * 全局http 方法对应状态码返回说明
     */
    private List<GlobalResponseMessage> globalResponseMessages;

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> group = new LinkedHashMap<>();

    @Data
    @NoArgsConstructor
    public static class  Contact{
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


    @Data
    @NoArgsConstructor
    public static class DocketInfo {
        /**
         * 需要生成文档的controller包路径，例如com.demo.controller
         */
        private String[] packages;

        /**
         * swagger会解析的url规则
         **/
        private String urlPattern = "";
        /**
         * 默认值为groupKey
         */
        private String groupName;
        /***
         * api文档名称
         */
        private String apiTitle ="";
        /**
         * api文档详细描述
         */
        private String apiDescription ="";
        /***
         * 服务条款URL
         */
        private String termsOfServiceUrl ="";
        /***
         * api版本号
         */
        private String apiVersion ="";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 联系信息
         */
        private Contact contact = new Contact();

        /***
         * 全局参数
         */
        private List<GlobalParam> globalParams;
    }

    /**
     * 全局参数
     */
    @Data
    @NoArgsConstructor
    public static class GlobalParam{
        /**
         * 参数名称
         */
        String paramName;
        /**
         * 参数说明
         */
        String paramDesc;
        /***
         *
         * 参数例子
         */
        String paramExample;
        /***
         * 参数类型，如：path、query、body、form、header
         */
        String paramType;
        /**
         * 参数类型，默认string，可选int、list
         */
        String dataType="string";
        /**
         * 是否必须
         */
        boolean required=false;
    }


    @Data
    @NoArgsConstructor
    public static class GlobalResponseMessage{
        /**
         * 请求方法
         */
        RequestMethod method;
        /**
         * 状态码-对应状态码说明清单
         */
        List<CodeMessage> codeMessages;

    }

    @Data
    @NoArgsConstructor
    public static class CodeMessage{
        /**
         * http 状态码，如200
         */
        int code;
        /**
         * 状态码说明
         */
        String message;
    }

}
