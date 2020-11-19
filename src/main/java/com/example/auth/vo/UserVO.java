package com.example.auth.vo;

import com.example.auth.provider.model.User;
import lombok.Data;

@Data
public class UserVO{

    private User user;

    private String token;

    private boolean success;

    private String roles;

}