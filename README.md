# Swagger Spring Boot Stater 
## 简介
spring boot 整合 springfox 实现swagger api文档生成。本项目主要简化整合流程，达到开箱即用，无需更多java代码配置。
## 使用说明
### Spring Boot项目（兼容 2.2.0-2.4.0）
引入依赖
```xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.3.7</version>
</dependency>
```
> 提示：
> 1.3.0 有两个ui界面，`http://localhost:8080/doc.html` 和`http://localhost:8080/swagger-ui.html`

### Spring Boot 介于2.0.0-2.20之间，引入方式
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
  <version>1.3.0</version>
</dependency>
```

#### 方法二：
````xml
<dependency>
  <groupId>net.ifok.swagger</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.3.0</version>
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

## Spring Boot 配置说明
### 单组配置
properties
````properties
spring.swagger.api-path=/v2/api-docs
spring.swagger.url-pattern=/**
spring.swagger.packages=
spring.swagger.api-title=API开放文档
spring.swagger.api-description=Api 接口说明
spring.swagger.terms-of-service-url=
spring.swagger.contact.name=联系人名
spring.swagger.contact.email=abc@qq.com
spring.swagger.contact.url=www.baidu.com
spring.swagger.license=Apache 2
spring.swagger.license-url=
````
yml
````yaml
spring: 
  swagger:
    api-path: /v2/api-docs
    url-pattern: /**
#    packages:
    api-title: API开放文档
    api-description: Api接口说明
    terms-of-service-url: www.baidu.com
    contact: 
      name: 联系人
      email: abc@qq.com
      url: www.baidu.com
    license: 许可名称
    license-url: 许可详细内容地址
    global-params:
      - paramName: accessToken
        paramDesc: token令牌
        paramType: header
        paramExample: 123456
        required: true
    global-response-messages:
      - method: POST
        codeMessages:
          - code: 200
            message: okkk
          - code: 401
            message: nologin
          - code: 403
            message: 权限不足
      - method: GET
        codeMessages:
          - code: 200
            message: okkk
          - code: 401
            message: nologin
          - code: 403
            message: 权限不足
````

> 提示: `spring.swagger.config.packages` 与 `spring.swagger.config.url-pattern` 可以只选择其中一个配置，如果两个同时配置则回取两个的并集。 

### 多组别配置
```properties
#A组
spring.swagger.group.aaa.group-name=A组名称
spring.swagger.group.aaa.url-pattern=/aaa/**
spring.swagger.group.aaa.packages=
spring.swagger.group.aaa.api-title=A分组API
spring.swagger.group.aaa.api-description=A分组API主要用于干xxx
spring.swagger.group.aaa.terms-of-service-url=
spring.swagger.group.aaa.contact.name=联系人名
spring.swagger.group.aaa.contact.email=abc@qq.com
spring.swagger.group.aaa.contact.url=wwww.baidu.com
spring.swagger.group.aaa.license=Apache 2
spring.swagger.group.aaa.license-url=
#B组
spring.swagger.group.bbb.group-name=B组名称
spring.swagger.group.bbb.url-pattern=/bbb/**
spring.swagger.group.bbb.packages=
spring.swagger.group.bbb.api-title=B分组API
spring.swagger.group.bbb.api-description=B分组API主要用于干xxx
spring.swagger.group.bbb.terms-of-service-url=
spring.swagger.group.bbb.contact.name=联系人名
spring.swagger.group.bbb.contact.email=abc@qq.com
spring.swagger.group.bbb.contact.url=wwww.baidu.com
spring.swagger.group.bbb.license=Apache 2
spring.swagger.group.bbb.license-url=

```
> 注意：当同时配置了多分组和单分组，单分组将会无效！

其他配置
- 全局参数
- 全局HTTP状态码响应说明（注意200的响应说明内容改不了）
````yaml
spring: 
  swagger:
    global-params:
      - paramName: accessToken
        paramDesc: token令牌
        paramType: header
        paramExample: 123456
        required: true
    global-response-messages:
      - method: POST
        codeMessages:
          - code: 200
            message: okkk
          - code: 401
            message: nologin
          - code: 403
            message: 权限不足
      - method: GET
        codeMessages:
          - code: 200
            message: okkk
          - code: 401
            message: nologin
          - code: 403
            message: 权限不足
````

## Springfox相关注解使用说明
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

## 更新日志
#### 1.3.7
- 支持配置和修改默认的api 文档json地址。`spring.swagger.api-path`

#### 1.3.6
- 1.新增支持配置全局响应消息。`spring.swagger.global-response-messages`

#### 1.3.5
- 1.修复number空指针问题；

#### 1.3.4
- 1.更新升级ui版本为2.1.2

#### 1.3.3
- 修复bug

#### 1.3.2
- 1.新增全局参数配置`spring.swagger.global-params`
#### 1.3.1
- 略
#### 1.3.0
- 添加新ui


### LICENSE
Apache Software License, Version 2.0