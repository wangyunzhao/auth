package com.example.auth.provider.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zhangnianzhong
 * @email: zhangnianzhong@egret.com
 * @datetime: 2019/8/27 10:11
 * @version: 1.0.0
 */
@Data
public class Result<T> implements Serializable {
    private boolean success;
    private T result;
    private String errorCode;
    private String errorMsg;

    public Result() {
    }

    public Result(T result) {
        this.success = true;
        this.result = result;
    }

    public Result(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        return "Result{success=" + this.success + ", result=" + this.result + ", errorCode=\'" + this.errorCode + '\'' + ", errorMsg=\'" + this.errorMsg + '\'' + '}';
    }

    public String toJsonString() {
        return "{success:" + this.success + ", result:" + this.result + ", errorCode:\'" + this.errorCode + '\'' + ", errorMsg:\'" + this.errorMsg + '\'' + '}';
    }
}
