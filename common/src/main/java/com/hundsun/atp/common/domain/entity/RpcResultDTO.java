package com.hundsun.atp.common.domain.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RpcResultDTO<T> implements IRpcResultDTO<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private Integer returnCode = Integer.valueOf(0);

    private String errorCode = "";

    private String errorMessage = "";

    private Map<String, Object> errorProperties = new HashMap<>();

    private T result;

    public RpcResultDTO() {
    }

    public RpcResultDTO(T result) {
        this.result = result;
    }

    @Override
    public Integer getReturnCode() {
        return this.returnCode;
    }

    @Override
    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public T getResult() {
        return this.result;
    }

    @Override
    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public Map<String, Object> getErrorProperties() {
        return this.errorProperties;
    }

    @Override
    public void setErrorProperties(Map<String, Object> errorProperties) {
        this.errorProperties = errorProperties;
    }

    @Override
    public String toString() {
        return "RpcResultDTO{returnCode=" + this.returnCode + ", errorCode='" + this.errorCode + '\'' + ", errorMessage='" + this.errorMessage + '\'' + ", errorProperties=" + this.errorProperties + ", result=" + this.result + '}';
    }
}