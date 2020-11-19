package com.example.auth.config;


import com.example.auth.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianzhong on 2019/8/27.
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserService baseAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        //账号信息
        com.example.auth.provider.model.User accountInfo = baseAccountRepository.getUserById(1);
        if (accountInfo == null) {
            throw new UsernameNotFoundException("Account[" + account + "]not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //对应的权限添加
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(accountInfo.getName(), accountInfo.getPassword(), authorities);

    }
}
