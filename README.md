# Swagger Boot Stater 
## 简介
spring boot 整合 springfox 实现swagger api文档生成。本项目主要简化整合流程，达到开箱即用，无需更多java代码配置。
## 使用说明
### spring boot项目（兼容 2.2.0-2.4.0）
引入依赖
```xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.2.1</version>
</dependency>
```
### spring boot 介于2.0.0-2.20之间，引入方式
#### 方法一：
````xml
    <properties>
        ...其他
        <spring-plugin.version>2.0.0.RELEASE</spring-plugin.version>
    </properties>
````
> 在pom.xml的properties节点添加`<spring-plugin.version>2.0.0.RELEASE</spring-plugin.version>` 
然后引入
```xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>
```

#### 方法二：
````xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.2.0</version>
  <exclusions>
      <exclusion>
          <groupId>org.springframework.plugin</groupId>
          <artifactId>spring-plugin-core</artifactId>
      </exclusion>
      <exclusion>
          <groupId>org.springframework.plugin</groupId>
          <artifactId>spring-plugin-metadata</artifactId>
      </exclusion>
  </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.plugin</groupId>
    <artifactId>spring-plugin-core</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.plugin</groupId>
    <artifactId>spring-plugin-metadata</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
````

## spring boot 配置说明
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

## springfox相关注解使用说明
- Spring 项目的controller类，添加@Api(tags="该controller的处理说明")
    ```java
    @Api(tags = "用户相关操作")
    public class UserController {
    
        @ApiOperation("添加用户")
        public Object addUser(){
            return "success";
        }
    }
    ```
- Spring 项目的controller类，方法添加 @ApiOpertion("该接口说明")
- 未完待续

### 更多参考

- http://springfox.github.io/springfox/
- https://github.com/springfox/springfox

### LICENSE
Apache Software License, Version 2.0