package net.ifok.project.swagger.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description swagger 配置文件
 * 
 * @author lee
 * @date 2020/8/14 22:13
 **/
@Component
@ConfigurationProperties(prefix = "spring.swagger.config")
@Data
@Slf4j
public class SwaggerProperties {

    public SwaggerProperties(){
        log.info("SwaggerProperties init ....");
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


}
