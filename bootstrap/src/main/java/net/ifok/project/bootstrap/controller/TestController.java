package net.ifok.project.bootstrap.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.ifok.project.bootstrap.controller.request.UserDTO;
import net.ifok.project.bootstrap.controller.response.UserVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:  接口测试类
 * 
 * @author lee
 * @date 2020/7/17 10:48
 */
@Api(tags = "测试API controller说明")
@RestController
@RequestMapping("/api")
public class TestController {

    @ApiOperation(value = "新增用户",notes = "新增用户，HTTP POST BODY参数模式",produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO dto){
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "删除用户",notes = "根据用户ID删除用户（query参数模式）",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "accessToken",value = "访问HTTP头部令牌",paramType = "header", dataType = "string", required = true)
    @DeleteMapping("/user/delete")
    public ResponseEntity<Boolean> delete(Long userId){
        return ResponseEntity.ok(true);
    }

    @ApiOperation(value = "更新用户信息",notes = "根据用户ID修改用户信息，当前参数传递方式为form data",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "accessToken",value = "访问HTTP头部令牌",paramType = "header", dataType = "string", required = true)
    @PutMapping("/user/update")
    public ResponseEntity<UserDTO> update(UserDTO dto){
        return ResponseEntity.ok(dto);
    }


    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/user/list")
    public ResponseEntity<List<UserVO>> getList(){
        return ResponseEntity.ok(Arrays.asList(
           new UserVO("张三",20),
           new UserVO("李四",30)
        ));
    }

}
