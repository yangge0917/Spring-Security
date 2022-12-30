package com.at.demo01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//继承WebSecurityConfigurerAdapter接口
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //重写该方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //对密码进行加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123456");
        //设置用户名和密码
        auth.inMemoryAuthentication().withUser("yang").password(password).roles("admin");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
