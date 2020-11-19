package com.example.auth.Jwt;

import com.alibaba.fastjson.JSONObject;
import com.example.auth.exception.ExceptionEnum;
import com.example.auth.provider.model.Result;
import com.example.auth.security.TokenManager;
import com.example.auth.util.SpringContextUtil;
import com.example.auth.vo.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhicheng.zhao on 2020/9/4.
 */
public class JwtAuthFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;

    public JwtAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
    }

    /**
     * 过滤器
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置请求权限
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,OPTIONS,DELETE,PATCH");
        response.setHeader("Access-Control-Allow-Headers", "tokenId,userType,token,Origin,X-Requested-With,Content-Type,Accept,appKey,hszCookie");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json,charset=utf-8");

        String url = request.getRequestURI();

        // 鉴权判断：本地环境模拟管理员权限，其他环境需要根据url鉴权 zzc
        System.out.println(SpringContextUtil.getProp("authVerify"));
        if (SpringContextUtil.getProp("authVerify")==null?false:SpringContextUtil.getProp("authVerify").equalsIgnoreCase("false1")) {
            // 本地环境，存储用户认证信息
            String tokenTmp = tokenManager.createTokenAll("test");
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(tokenTmp);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);

            return;
        } else if (urlFilter(url)) {
            chain.doFilter(request, response);

            return;
        }

        // 过滤token为空
        String token = request.getHeader(Constants.TOKEN);
        if (token == null || "".equals(token)) {
            Result result = new Result(ExceptionEnum.TOKEN_INVALID.getErrorCode(), ExceptionEnum.TOKEN_INVALID.getErrorMsg());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JSONObject.toJSONString(result));

            return;
        }

        // 从token获取用户认证信息
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        if (authenticationToken == null) {
            Result result = new Result(ExceptionEnum.TOKEN_INVALID.getErrorCode(), ExceptionEnum.TOKEN_INVALID.getErrorMsg());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JSONObject.toJSONString(result));
            return;
        }

        // 存储用户认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    /**
     * 不需要token的地址:
     * 用户名登录/手机登录/第三方登录/微信登录
     * 注册
     * 登录前(authFree)
     *
     * @param url
     * @return
     */
    private boolean urlFilter(String url) {
        return url.contains("/login")
                || url.contains("Login")
                || url.contains("/regist")
                || url.contains("Regist")
                || url.contains("/authFree");
    }

    /**
     * 从token获取用户认证信息
     *
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.SIGN_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String user = claims.getSubject();
            String role = claims.get(Constants.CLAIM_ROLES).toString();

            List<SimpleGrantedAuthority> userRolesByToken = Arrays.stream(role.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (!StringUtils.isEmpty(user)) {
                return new UsernamePasswordAuthenticationToken(user, null, userRolesByToken);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
