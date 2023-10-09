package com.hundsun.atp.common.domain.entity;


import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.constant.ExceptionLogTypeEnum;
import com.hundsun.atp.common.enums.Level;
import com.hundsun.atp.common.util.ErrorFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -6853310712844466349L;

    private String returnCode = "";

    private String errorCode = "-1";

    private String errorMessage = "";

    private ExceptionLogTypeEnum exceptionLogType = ExceptionLogTypeEnum.UNKNOWN;

    private Level level;

    private List<String> errorPropNames = new ArrayList<>();

    private Map<String, Object> errorProperties = new HashMap<>();

    public BaseException(String errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String... messages) {
        this.errorCode = errorCode;
        this.errorMessage = ErrorFormatter.getInstance().format(errorCode, (Object[]) messages);
    }

    public BaseException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, Throwable cause, String... messages) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = ErrorFormatter.getInstance().format(errorCode, (Object[]) messages);
    }

    public void setErrorMessage(String... messages) {
        this.errorMessage = ErrorFormatter.getInstance().format(this.errorCode, (Object[]) messages);
    }

    public void setErrorMessageNoFormat(String messages) {
        this.errorMessage = messages;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrorPropNames() {
        return this.errorPropNames;
    }

    public void logAsSystemError() {
        this.exceptionLogType = ExceptionLogTypeEnum.SYSTEM;
    }

    public void logAsBizError() {
        this.exceptionLogType = ExceptionLogTypeEnum.BUSINESS;
    }

    public boolean isLogAsSystemError() {
        return ExceptionLogTypeEnum.SYSTEM.equals(this.exceptionLogType);
    }

    public boolean isLogAsBizError() {
        return ExceptionLogTypeEnum.BUSINESS.equals(this.exceptionLogType);
    }

    public void logAsErrorLevel() {
        this.level = Level.ERROR;
    }

    public Level getLevel() {
        return this.level;
    }

    public void putErrorProperty(String name, Object prop) {
        if (StrUtil.isEmpty(name)) {
            return;
        }
        if (!this.errorProperties.containsKey(name)) {
            this.errorPropNames.add(name);
        }
        this.errorProperties.put(name, prop);
    }

    public void putErrorProperty(Map<String, Object> errorProperties) {
        if (errorProperties != null) {
            for (Map.Entry<String, Object> entry : errorProperties.entrySet()) {
                putErrorProperty(entry.getKey(), entry.getValue());
            }
        }

    }

    public Map<String, Object> getErrorProperties() {
        return this.errorProperties;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("[" + this.errorCode + "]");
        if (!StrUtil.isEmpty(this.errorMessage)) {
            sb.append("[").append(this.errorMessage).append("]");
        } else if (!StrUtil.isEmpty(super.getMessage())) {
            sb.append("[").append(super.getMessage()).append("]");
        }
        int errorPropSize = this.errorPropNames.size();
        if (errorPropSize > 0) {
            sb.append("[");
            for (int i = 0; i < errorPropSize; i++) {
                String propName = this.errorPropNames.get(i);
                Object propValue = this.errorProperties.get(propName);
                if (i == 0) {
                    sb.append(propName + "=" + propValue);
                } else {
                    sb.append(", " + propName + "=" + propValue);
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }
}