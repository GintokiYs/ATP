package com.hundsun.atp.common.domain.entity;

import java.util.Map;

public interface IRpcResultDTO<T> {
    Integer getReturnCode();

    void setReturnCode(Integer paramInteger);

    String getErrorCode();

    void setErrorCode(String paramString);

    String getErrorMessage();

    void setErrorMessage(String paramString);

    T getResult();

    void setResult(T paramT);

    Map<String, Object> getErrorProperties();

    void setErrorProperties(Map<String, Object> paramMap);
}