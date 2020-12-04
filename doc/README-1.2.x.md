### 简介
spring boot 整合 springfox 2.10.5 实现swagger api文档生成。
### 使用说明
在spring boot项目（兼容 2.2.0-2.4.0）
引入依赖
```xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.2.1</version>
</dependency>
```

### spring boot 配置说明
````properties
spring.swagger.config.enabled=true
spring.swagger.config.api-title=API 开放文档
spring.swagger.config.api-description=Api 接口说明
spring.swagger.config.api-url=http://localhost:8080/swagger-ui.html
spring.swagger.config.api-version=1.0.0
spring.swagger.config.packages=
spring.swagger.config.url-pattern=/**
````
> 提示: `spring.swagger.config.packages` 与 `spring.swagger.config.url-pattern` 可以只选择其中一个配置，如果两个同时配置则回取两个的并集。 

### 更多参考

- http://springfox.github.io/springfox/
- https://github.com/springfox/springfox

## springfox版本升级提醒
2.9.x只需要导入2个依赖，2.10.x需要三个依赖,依赖为：
```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-spring-webmvc</artifactId>
            <version>2.10.5</version>
        </dependency>
```
或者：
````xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-spring-webFlux</artifactId>
            <version>2.10.5</version>
        </dependency>
````

且启用注解由`@EnabledSwagger2` 修改为`@EnableSwagger2WebMvc` 或者`@EnableSwagger2WebFlux`

## 依赖相关
去除了三方依赖，如2.9.x的google guava包
### LICENSE
Apache Software License, Version 2.0