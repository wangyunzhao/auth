package com.example.auth.exception;

/**
 * @description:
 * @author: zhangnianzhong
 * @email: zhangnianzhong@egret.com
 * @datetime: 2019/8/27 10:11
 * @version: 1.0.0
 */
public enum ExceptionEnum {
    LOGIN_ERROR("100", "用户名或密码不正确"),
    TOKEN_INVALID("101", "token无效"),
    TOKEN_DENIED("102", "权限不足"),
    DATA_ERROR("103", "数据异常");

    ExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private String errorCode;
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
