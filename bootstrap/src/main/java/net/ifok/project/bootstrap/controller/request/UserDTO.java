package net.ifok.project.bootstrap.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:  demo 用用户
 * 
 * @author lee
 * @date 2020/7/17 11:00
 */
@ApiModel(value = "用户信息")
@Data
public class UserDTO {
    public UserDTO() {
    }

    public UserDTO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @ApiModelProperty(value = "用户名",notes = "用户名，字符类型数据一般2-3位",name = "name",required = true)
    public String name;
    @ApiModelProperty(value = "年龄",notes = "年龄，数字类型",name = "age",required = true)
    public Integer age;

}
