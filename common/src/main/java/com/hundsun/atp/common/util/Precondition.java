package com.hundsun.atp.common.util;

import java.util.Collection;
import java.util.Objects;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.exception.PlatformException;


public class Precondition {
    public static void checkIndexGreaterZero(int index, String errorCode, String... errorMessage) {
        if (index > 0) {
            throw exceptionHandle(errorCode, errorMessage);
        }
    }

    public static void checkIndexGreaterOne(int index, String errorCode, String... errorMessage) {
        if (index > 1) {
            throw exceptionHandle(errorCode, errorMessage);
        }
    }

    public static void checkState(boolean expression, String errorCode, String... errorMessage) {
        if (expression) {
            throw exceptionHandle(errorCode, errorMessage);
        }
    }

    public static void checkArgument(boolean expression, String errorCode, String... errorMessage) {
        if (expression) {
            throw exceptionHandle(errorCode, errorMessage);
        }
    }

    public static <T> T checkNotNull(T reference, String errorCode, String... errorMessage) {
        if (reference instanceof String && StrUtil.isBlank(Objects.toString(reference))) {
            throw exceptionHandle(errorCode, errorMessage);
        }
        if (reference instanceof Object && Objects.isNull(reference)) {
            throw exceptionHandle(errorCode, errorMessage);
        }
        if (reference instanceof Collection && CollectionUtil.isEmpty((Collection) reference)) {
            throw exceptionHandle(errorCode, errorMessage);
        }
        if (reference == null) {
            throw exceptionHandle(errorCode, errorMessage);
        }
        return reference;
    }

    public static PlatformException exceptionHandle(String errorCode, String... errorMessage) {
        if (StrUtil.isEmpty(errorCode)) {
            errorCode = "500";
        }
        if (errorMessage == null) {
            return new PlatformException(errorCode);
        }
        return new PlatformException(errorCode, errorMessage);
    }
}
