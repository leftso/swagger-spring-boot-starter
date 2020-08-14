package net.ifok.project.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:  启动类
 * 
 * @author lee
 * @date 2020/7/17 10:51
 */
@SpringBootApplication
@Controller
public class SpringBootSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSwaggerApplication.class, args);
    }

    /**
     * 主页跳转到 文档页码
     * @return
     */
    @GetMapping("")
    public String idnex(){
        return "redirect:/swagger-ui.html";
    }
}
