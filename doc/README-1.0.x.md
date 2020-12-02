### 简介
spring boot 整合 springfox 2.9.2 实现swagger api文档生成。
### 使用说明
在spring boot项目（兼容 2.0.0-2.4.0）
引入依赖
```xml
<dependency>
  <groupId>net.ifok.project</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>1.0.0</version>
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

### 版本选择
##### 2.9.2

为何选择2.9.2版本

![版本选择](screenshot/version.png)

因为用的人多，且相对稳定。2.10.x/3.x目前存在一些bug，且与2.9.2版内部代码本差异较大
。

### 生成文档展示
[提示] 2.9.2 版本的UI访问地址为 /swagger-ui.html;3.x /swagger-ui/index.html
- 首页
![首页](screenshot/swagger-home.png)

- 新增POST
![POST](screenshot/swagger-post.png)

- 删除DELETE
![DELETE](screenshot/swagger-delete.png)

- 修改PUT
![PUT](screenshot/swagger-put.png)

- 查询GET
![GET](screenshot/swagger-get.png)

### 更多参考

- http://springfox.github.io/springfox/
- https://github.com/springfox/springfox

### LICENSE
Apache Software License, Version 2.0