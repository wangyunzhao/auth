package com.example.auth.exception;

/**
 * Created by zhicheng.zhao on 2020/9/10.
 */
public class ServiceException extends Exception {
    private String msg;

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
