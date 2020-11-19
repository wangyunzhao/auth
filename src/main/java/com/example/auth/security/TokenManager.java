package com.example.auth.security;


import com.example.auth.provider.model.User;
import com.example.auth.provider.service.UserService;
import com.example.auth.vo.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangnianzhong on 2019/8/27.
 */
@Component("tokenManager")
public class TokenManager {
    private static Logger logger = LoggerFactory.getLogger(TokenManager.class);

    @Autowired
    private UserService userService;


    public String createToken(String userId) {
        long expire_time = 1000 * 60 * 60 * 12;
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + expire_time))
                .signWith(SignatureAlgorithm.HS512, "JwtSecret")
                .compact();
        return token;
    }

    /**
     * B端用户权限
     *
     * @param userName
     * @return
     */
    public String createTokenBusiness(String userName) {
        long expireTime = 1000 * 60 * 60 * 12;

        User user = userService.getUserById(1);
        List<String> list= new ArrayList<>();
        if (user.isDev()) {
            list.add("ROLE_01");
        }
        if (user.isChannel()) {
            list.add("ROLE_02");
        }
        if (!user.isChannel() && !user.isDev()) {
            list.add("ROLE_00");
        }

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "JwtSecret")
                .claim("roles", String.join(",", list))
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();

        return token;
    }

    /**
     * C端用户权限
     *
     * @param userName
     * @return
     */
    public String createTokenCustomer(String userName) {
        long expireTime = 1000 * 60 * 60 * 12;

        User account = userService.getUserById(1);
        List<String> list= new ArrayList<>();
        if ("0".equalsIgnoreCase(account.getRole())) {
            list.add("ROLE_10");
        } else {
            list.add("ROLE_11");
        }

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,"JwtSecret")
                .claim("roles", String.join(",", list))
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();

        return token;
    }

    /**
     * 模拟所有用户权限
     *
     * @param userName
     * @return
     */
    public String createTokenAll(String userName) {
        long expireTime = 1000 * 60 * 60 * 12;

        List<String> list= new ArrayList<>();
        list.add(Constants.ROLE_ADMIN);
        list.add(Constants.ROLE_DEVELOPER);
        list.add(Constants.ROLE_CHANNEL);
        list.add(Constants.ROLE_STREAMER);
        list.add(Constants.ROLE_AUDIENCE);

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, Constants.SIGN_KEY)
                .claim(Constants.CLAIM_ROLES, String.join(",", list))
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();

        return token;
    }

    public String getRoles(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.SIGN_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String roles = claims.get(Constants.CLAIM_ROLES).toString();

            return roles;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public String getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal().toString();
    }

    public String getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().toString();
    }

    public List<String> getRoleList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();
        List<String> roleList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++) {
            GrantedAuthority grantedAuthority = list.get(i);
            roleList.add(grantedAuthority.getAuthority());
        }

        return roleList;
    }
}
