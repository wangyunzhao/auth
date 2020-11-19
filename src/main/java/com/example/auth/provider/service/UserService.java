package com.example.auth.provider.service;

import com.example.auth.provider.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @program: auth
 * @description: 获取user
 * @author: YunZhao.Wang
 * @create: 2020-10-16 17:21
 **/
@Service
public class UserService {

    public User getUserById(int id){
        User user = new User(1,"test");
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        user.setDev(true);
        return user;
    }
}
