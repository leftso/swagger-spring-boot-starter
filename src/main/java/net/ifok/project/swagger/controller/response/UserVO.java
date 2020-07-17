package net.ifok.project.swagger.controller.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import net.ifok.project.swagger.controller.request.UserDTO;

@ApiModel(value = "用户返回")
@Data
public class UserVO extends UserDTO {
    public UserVO() {
    }

    public UserVO(String name, Integer age) {
        super(name, age);
    }
}
