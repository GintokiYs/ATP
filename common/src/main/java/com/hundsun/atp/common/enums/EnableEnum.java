package com.hundsun.atp.common.enums;

/**
 * 有效值枚举类
 */
public enum EnableEnum {
    DELETE(Integer.valueOf(-1), "已删除"),
    INVALID(Integer.valueOf(0), "未生效"),
    VALID(Integer.valueOf(1), "已生效");

    private String name;

    private Integer code;

    EnableEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}