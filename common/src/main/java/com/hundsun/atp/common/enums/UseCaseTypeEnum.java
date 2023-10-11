package com.hundsun.atp.common.enums;

/**
 * 用例枚举类
 */
public enum UseCaseTypeEnum {
    INTERFACE(Integer.valueOf(1), "接口用例"),
    DI(Integer.valueOf(2), "实时同步用例");

    private Integer code;

    private String name;

    UseCaseTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static UseCaseTypeEnum getByCode(Integer code) {
        for (UseCaseTypeEnum folderTypeEnum : UseCaseTypeEnum.values()) {
            if (folderTypeEnum.code.equals(code)) {
                return folderTypeEnum;
            }
        }
        return null;
    }
}