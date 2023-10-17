package com.hundsun.atp.common.enums;

/**
 * 实例执行结果枚举类
 */
public enum ExecuteStatusEnum {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    RUNNING("RUNNING", "运行中"),
    UNKNOW("UNKNOW", "未执行");

    private String code;

    private String name;

    ExecuteStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ExecuteStatusEnum getByCode(String code) {
        for (ExecuteStatusEnum folderTypeEnum : ExecuteStatusEnum.values()) {
            if (folderTypeEnum.code.equals(code)) {
                return folderTypeEnum;
            }
        }
        return null;
    }
}