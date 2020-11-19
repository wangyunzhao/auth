package com.example.auth.advice;


import com.alibaba.fastjson.JSON;
import com.example.auth.exception.ExceptionEnum;
import com.example.auth.exception.MyAuthException;
import com.example.auth.exception.ServiceException;
import com.example.auth.provider.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 方法成功返回调用该类
 */
@ControllerAdvice({"com.egret.module.controller", "com.egret.module.api"})
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static Logger logger = LoggerFactory.getLogger(JsonResponseBodyAdvice.class);

    public Object beforeBodyWrite(Object object,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class clazz,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        Result data = new Result();
        data.setResult(object);
        return JSON.toJSONString(data) ;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class clazz) {
        return clazz.isAssignableFrom(MappingJackson2HttpMessageConverter.class);
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Result> handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        Result data = new Result();

        response.setHeader("Content-Type", "application/json");
        if (exception instanceof AccessDeniedException) {
            logger.info("==========鉴权失败========== {}", request.getRequestURI());

            data.setErrorMsg(ExceptionEnum.TOKEN_DENIED.getErrorMsg());
            data.setErrorCode(ExceptionEnum.TOKEN_DENIED.getErrorCode());
            return new ResponseEntity<Result>(data, HttpStatus.OK);
        }

        if (exception instanceof MyAuthException) {
            logger.info("==========数据异常========== {}", request.getRequestURI());
            data.setErrorMsg(exception.getMessage());
            data.setErrorCode(ExceptionEnum.DATA_ERROR.getErrorCode());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        if (exception instanceof ServiceException) {
            logger.info("==========数据异常========== {}", request.getRequestURI());
            data.setErrorMsg(((ServiceException) exception).getMsg());
            data.setErrorCode(ExceptionEnum.DATA_ERROR.getErrorCode());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        exception.printStackTrace();
        data.setErrorCode(ExceptionEnum.DATA_ERROR.getErrorCode());
        data.setErrorMsg(ExceptionEnum.DATA_ERROR.getErrorMsg());
        return new ResponseEntity<Result>(data, HttpStatus.OK);
    }
}
