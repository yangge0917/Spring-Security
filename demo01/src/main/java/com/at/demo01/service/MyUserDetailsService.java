package com.at.demo01.service;

import com.at.demo01.entity.Users;
import com.at.demo01.mapper.UsersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用UsersMapper方法,根据用户名查询数据库

        System.out.println("第一次提交");
        System.out.println("我提交了新的分支");
        System.out.println("我拉取新的代码");

        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("name",username);
        Users users = usersMapper.selectOne(usersQueryWrapper);
        if (users==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_role1");
        //从数据库中查询，得到用户名和密码，返回
        return new User(users.getName(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);

    }
}
