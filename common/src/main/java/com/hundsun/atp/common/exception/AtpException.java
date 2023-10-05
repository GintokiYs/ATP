package com.hundsun.atp.common.exception;

import com.hundsun.atp.common.enums.ResponseEnum;

public class AtpException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object object;

    /**
     * 响应状态码枚举
     */
    private ResponseEnum responseEnum;

    public AtpException(String msg) {
        super(msg);
    }

    public AtpException(String msg, Object object) {
        super(msg);
        this.object = object;
    }

    public AtpException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public AtpException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
    }

    public AtpException(ResponseEnum responseEnum, Object object) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
        this.object = object;
    }


    public Object getObject() {
        return object;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}