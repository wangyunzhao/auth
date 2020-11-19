package com.example.auth.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by nianzhong on 2019/10/29.
 */
public class MyAuthException extends Exception {

    private JSONObject result;

    public MyAuthException(JSONObject json) {
        this.result = json;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }
}
