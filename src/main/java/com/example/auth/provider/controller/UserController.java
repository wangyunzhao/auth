package com.example.auth.provider.controller;


import com.example.auth.provider.model.User;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

/**
 * @program: auth
 * @description: 权限鉴权验证
 * @author: YunZhao.Wang
 * @create: 2020-10-16 11:23
 **/

@RestController
@Api(tags = {"接口鉴权"},value = "接口值",description = "描述")
@RequestMapping(value = "/authentication")
public class UserController {

    @PostMapping("/getUser")
    public User getUser(int id) {
        User user1 = new User(1, "王云召");
        User user2 = new User(2, "王云盼");
        User user3 = new User(3, "啥也不是");
        User user4= new User();
        if (id == user1.getId()) {
            return user1;
        } else if (id == user2.getId()) {
            return user2;
        }
        return user4;
    }


}
