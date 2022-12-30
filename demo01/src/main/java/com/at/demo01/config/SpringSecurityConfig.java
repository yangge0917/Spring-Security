package com.at.demo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    //配置操作数据库对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);//注入数据源
//        jdbcTokenRepository.setCreateTableOnStartup(true);//启动时将表创建
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用户退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
        http.exceptionHandling().accessDeniedPage("/unauth.html");//配置没有权限的错误页面
        http.formLogin()//自定义自己编写的登录页面
                .loginPage("/login.html")//登录页面设置
                .loginProcessingUrl("/user/login") //登录访问路径(控制器)
                .defaultSuccessUrl("/success.html").permitAll() //登录成功后的跳转路径
                .and().authorizeRequests().antMatchers("/","/test/hello","/user/login").permitAll()//设置忽略验证的路径，直接访问
//                .antMatchers("/test/index").hasAuthority("admins")//当前登录用户具有admins权限才能访问这个路径
//                .antMatchers("/test/index").hasAnyAuthority("admins","manager")//支持多个权限，当用户权限中有其中一个就可访问
//                .antMatchers("/test/index").hasRole("role") //当前用户具有该角色就可以访问
                .antMatchers("/test/index").hasAnyRole("admins","role1") //当前用户具有其中一个角色就可以访问
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(this.persistentTokenRepository())//设置记住我功能
                .tokenValiditySeconds(60) //设置有效时长单位是秒
                .userDetailsService(userDetailsService) //实现类对象
                .and().csrf().disable(); //关闭csrf防护
    }
}
