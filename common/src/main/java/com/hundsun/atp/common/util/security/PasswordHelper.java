package com.hundsun.atp.common.util.security;

import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.util.security.EncryptUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PasswordHelper {

    public static final String decodePassword(String s) {
        try {
            return EncryptUtil.rsaDecode(s);
        } catch (Throwable throwable) {
            log.warn("decodePassword fai1ed,可能是密码非jres-sm4加密方式或者密码非密文，即将使用该值");
            return s;
        }
    }

    public static final String encodePassword(String s) {
        try {
            if (StrUtil.isNotBlank(s)) {
                s = StrUtil.trim(s);
                return EncryptUtil.rsaEncode(s);
            } else {
                log.info("密码为空");
                return s;
            }
        } catch (Throwable throwable) {
            log.warn("encodePassword failed", throwable);
            return s;
        }
    }
}