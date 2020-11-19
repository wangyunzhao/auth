package com.example.auth.exception;

import lombok.Data;

/**
 * @description:
 * @author: zhangnianzhong
 * @email: zhangnianzhong@egret.com
 * @datetime: 2019/8/27 10:11
 * @version: 1.0.0
 */

public enum RoleEnum {
    MANAGER("manager", "管理员"), GENERAL("general", "普通用户");
    private String roleName;
    private String desc;

    private RoleEnum(String roleName, String desc) {
        this.roleName = roleName;
        this.desc = desc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
