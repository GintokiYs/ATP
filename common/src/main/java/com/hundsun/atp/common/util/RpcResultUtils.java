package com.hundsun.atp.common.util;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.exception.WebException;

public class RpcResultUtils {
    public static <T> RpcResultDTO<T> suc(T obj) {
        RpcResultDTO<T> result = new RpcResultDTO(obj);
        result.setReturnCode(Integer.valueOf(200));
        return result;
    }

    public static <T> RpcResultDTO<T> error(T obj) {
        return new RpcResultDTO(obj);
    }

    public static <T> RpcResultDTO<T> error(int errorCode, String message) {
        return error(-1, String.valueOf(errorCode), message);
    }

    public static <T> RpcResultDTO<T> error(String errorCode, String message) {
        return error(-1, errorCode, message);
    }

    public static <T> RpcResultDTO<T> error(int returnCode, String errorCode, String message) {
        RpcResultDTO<T> result = new RpcResultDTO();
        result.setReturnCode(Integer.valueOf(returnCode));
        result.setErrorCode(errorCode);
        if (StrUtil.isNotEmpty(errorCode)) {
            result.setErrorMessage(ErrorFormatter.getInstance().format(errorCode, new Object[]{message}));
        } else {
            result.setErrorMessage(message);
        }
        return result;
    }

    public static <T> RpcResultDTO<T> error(WebException webException) {
        return error(Integer.parseInt(webException.getErrorCode()), webException.getErrorCode(), webException.getMessage());
    }
}
