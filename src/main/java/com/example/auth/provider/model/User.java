package com.example.auth.provider.model;

import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @program: auth
 * @description: 用户
 * @author: YunZhao.Wang
 * @create: 2020-10-16 11:26
 **/
@Data
public class User {

    private Integer id;

    private String name;

    private String password;

    private boolean dev;

    private boolean isChannel;

    private String role;

    public User(String name, String password, List<SimpleGrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    private List<SimpleGrantedAuthority> authorities;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
