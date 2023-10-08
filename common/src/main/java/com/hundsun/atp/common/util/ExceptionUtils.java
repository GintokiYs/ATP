package com.hundsun.atp.common.util;

import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.domain.entity.BaseException;

public class ExceptionUtils {
    public static String getErrorCode(Throwable e, int defaultCode) {
        if (e instanceof BaseException) {
            String errorCode = ((BaseException) e).getErrorCode();
            return (null == errorCode) ? (defaultCode + "") : errorCode;
        }
        return defaultCode + "";
    }

    public static BaseException getBaseException(Throwable e, int defaultCode) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        }
        BaseException be = new BaseException(defaultCode + "", e);
        if (!StrUtil.isEmpty(e.getMessage())) {
            be.setErrorMessage(new String[]{e.getMessage()});
        } else {
            be.setErrorMessage(new String[]{e.getClass().getName()});
        }
        return be;
    }

    public static String parseExceptionToString(Throwable e) {
        try {
            String s = e.toString() + "\n";
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                s = s + "\tat " + traceElement + "\n";
            }
            for (Throwable se : e.getSuppressed()) {
                s = s + parseExceptionToString(se);
            }
            Throwable ourCause = e.getCause();
            if (ourCause != null) {
                s = s + parseExceptionToString(ourCause);
            }
            s = s + "\n";
            return s;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "parseExceptionToString" + e.toString();
        }
    }

    public static boolean isSystemError(int code) {
        return ((code < 1999 && code != -1 && code != 0) || (code >= 2600 && code < 2700));
    }

    public static int getErrorCode(BaseException e) {
        int code = Integer.MAX_VALUE;
        try {
            if (e.getErrorCode() != null)
                code = Integer.parseInt(e.getErrorCode());
        } catch (Exception ex) {
            code = -1;
        }
        return code;
    }

    public static boolean isSystemError(BaseException e, String code) {
        if (null == e)
            return false;
        if (e.isLogAsBizError())
            return false;
        try {
            if (e.isLogAsSystemError() || isSystemError(Integer.parseInt(code)))
                return true;
        } catch (Exception ex) {
            return false;
        }
        return false;
    }
}