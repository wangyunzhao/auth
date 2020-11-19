package com.example.auth.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.auth.Jwt.JwtAuthFilter;
import com.example.auth.exception.ExceptionEnum;
import com.example.auth.provider.model.Result;
import com.example.auth.provider.model.User;
import com.example.auth.provider.service.UserService;
import com.example.auth.security.TokenManager;
import com.example.auth.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by admin on 2016/12/12.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf保护
        http.csrf().disable();

        // 无状态
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加filter
        http.addFilterBefore(new JwtAuthFilter(authenticationManager(), tokenManager), UsernamePasswordAuthenticationFilter.class);

        // 无拦截
        http.authorizeRequests().anyRequest().permitAll();

        //登陆
        http.formLogin().successHandler(loginSuccessHandler()).failureHandler(loginFailureHandler())
                .permitAll();

        //退出
        http.logout().logoutSuccessUrl("/login")
                .permitAll();

    }

    /**
     * 静态资源配置
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());

    }

    /**
     * 加密
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        //密码加密
        return new BCryptPasswordEncoder(4);
    }

    /**
     * 登录成功
     *
     * @return
     */
    @Bean
    AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Autowired
            private UserService userService;

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {

                // 获取帐号
                String account = request.getParameter("username");
                User accountInfo = userService.getUserById(1);

                // 获取cookie
                Map<String, String> cookie = null;
                try {
                    // 本地环境使用test，生生产使用yiwan
//                    cookie = JsoupUtil.httpLoginCookie(localAdmin, localPassword);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Result result = new Result(ExceptionEnum.LOGIN_ERROR.getErrorCode(), ExceptionEnum.LOGIN_ERROR.getErrorMsg());
                    response.setHeader("Content-Type", "application/json");
                    response.getWriter().print(JSON.toJSON(result));
                    return;
                }

                // 返回
                successMethod(accountInfo, cookie, response);
            }
        };
    }

    /**
     * 登录失败
     *
     * @return
     */
    @Bean
    AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                //String managerName = (String) request.getParameter("username");

                Result result = new Result(ExceptionEnum.LOGIN_ERROR.getErrorCode(), ExceptionEnum.LOGIN_ERROR.getErrorMsg());
                response.setHeader("Content-Type", "application/json");
                response.getWriter().print(JSON.toJSON(result));
            }
        };
    }

    /**
     * 登录成功返回值
     *
     * @param
     * @param cookie
     * @param response
     * @throws IOException
     */
    private void successMethod(User accountInfo, Map<String, String> cookie, HttpServletResponse response) throws IOException {
        UserVO view = new UserVO();
        BeanUtils.copyProperties(accountInfo, view);
//        view.setHszCookie(JsoupUtil.cookieMap2Cookies(cookie));
        view.setSuccess(true);
        view.setToken(tokenManager.createTokenBusiness(accountInfo.getName()));
        view.setRoles(tokenManager.getRoles(view.getToken()));
        Result result = new Result(view);

//        initCookies(cookie, response);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json,charset=utf-8");
        response.getWriter().print(JSONObject.toJSONString(result));
    }

    /**
     * 初始化cookie
     *
     * @param cookie
     * @param response
     */
    private void initCookies(Map<String, String> cookie, HttpServletResponse response) {
        Iterator it = cookie.keySet().iterator();
        while (it.hasNext()) {
            String cookieKey = (String) it.next();
            String cookieValue = cookie.get(cookieKey);
            Cookie c = new Cookie(cookieKey, cookieValue);
            response.addCookie(c);
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(hashedPassword);
        //admin: $2a$04$VBbJKSVXLSqjXilq9h6z7.qZ.iPzsdpq5gezGp9FfKrRkHXZGWshG
        //123456: $2a$04$.IRowbH1p9HBAQATSpf9Ne5xEqj181rZ7M3FZ/SlOLpHyzN0g/39O
    }

}
