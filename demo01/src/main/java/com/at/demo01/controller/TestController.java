package com.at.demo01.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello security";
    }
    @GetMapping("/index")
    public String index(){
        return "登录成功";
    }

//    @Secured({"ROLE_role1"}) //需要加上前缀  需要有该角色才能访问此控制器
//    @PreAuthorize("hasAnyAuthority('admins')")
    @PostAuthorize("hasAnyAuthority('admins')")
    @GetMapping("/update")
    public String update(){
        System.out.println("update====");
        return "update";
    }
}
