package com.hundsun.atp.common.enums;

/**
 * 文件夹枚举类
 */
public enum FolderTypeEnum {
    PROJECT(Integer.valueOf(1), "项目文件夹"),
    CATEGORY(Integer.valueOf(2), "用例类别文件夹"),
    USECASE(Integer.valueOf(3), "用例集");

    private Integer code;

    private String name;

    FolderTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FolderTypeEnum getByCode(Integer code) {
        for (FolderTypeEnum folderTypeEnum : FolderTypeEnum.values()) {
            if (folderTypeEnum.code.equals(code)) {
                return folderTypeEnum;
            }
        }
        return null;
    }
}