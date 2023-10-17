package com.hundsun.atp.common.util.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.common.util
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-07 14:41
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-07 14:41
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
public class EncryptUtil {
    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * RSA私钥
     */
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIALj7tty1+BhvBmrzQENe+Z7oQfeL/RFApQbt7KY22r+q410/6pDHOFK3D2T1IR0vlK+i5mH7ZIw2SdX4jHFD7YCyga4JWNK4Vn2FzRYPkbSnGwrYT7wptvss+mvexhHLun+P4a0oQ4DrrY7nnpCeYEOkc9P73Hz7xPhuiUumpAgMBAAECgYAFtiOFvaUwaqApzRYo25DEyW1k3yFY8qmnnFFJgZ/RViSyJoTzIibZWcRYWIXMlXiDLwrwBF+IJ3G8XmASgHAou8jCheVwR6Wt43PE3BrxwJKtLG485FC0HDLQCHN8yTpB/6/XKzfh6dmmN3dTvVSwPi/tY7WS5Liqjzw7+MWpcQJBAOx8yxT42koT43AUqg1QoqdfYDbMzFFB4UumO12EieBLQMT7l9xaKSf6hJ/9HznOZJF1AXtPj51bZcaLJusuELkCQQDAsAAmhDX/r3L2yGozgTcu+KqNP1unuvD+UHj120+8rL5V6cQ2Wyjive0QCG4Sv9rumxqrMSHUEvlslZ9M1chxAkEAtDw4Gve+ho653IYjRJqcHKvYDGvxnEx1hM5gmFmXK1avOnH1v38Htux7f+POlKP0idd/FZgBgJBT9QnA06T8IQJAOlsTMJcJ4423O5Ym08JmfAtDW9O8ZmfvZJXqjEsHMxT97/cEPZIOJ91tVlW3QGZqX6kJwp72p8u3TnNh81srgQJBANiSQXHqh/BnsznItZScWvHrS+rvOZUz+CLI3unSqta4XSCxv/3ec7Zn4ip10anIIDYuqQ8grXCWE8KBm7Thqhc=";

    /**
     * RSA公钥
     */
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyAC4+7bctfgYbwZq80BDXvme6EH3i/0RQKUG7eymNtq/quNdP+qQxzhStw9k9SEdL5SvouZh+2SMNknV+IxxQ+2AsoGuCVjSuFZ9hc0WD5G0pxsK2E+8Kbb7LPpr3sYRy7p/j+GtKEOA662O556QnmBDpHPT+9x8+8T4bolLpqQIDAQAB";

    private static RSA rsa;

    static {
        rsa = new RSA(privateKey, publicKey);
    }
//    public static void main(String[] args) {
//
//        String encode = rsaEncode("admin");
//        System.out.println(encode);
//        String decode = rsaDecode(encode);
//        System.out.println(decode);
//    }


    public static String rsaDecode(String s) {
        try {
            byte[] decode = Base64.decode(s);
            byte[] decrypt = rsa.decrypt(decode, KeyType.PrivateKey);
            return new String(decrypt);
        } catch (Exception e) {
            log.warn("decodePassword failed. 可能是密码非rsa加密方式或者密码非密文，即将使用该值", e);
            return s;
        }
    }

    public static String rsaEncode(String s) {
        try {
            if (StrUtil.isNotBlank(s)) {
                RSA rsa = new RSA(privateKey, publicKey);
                return rsa.encryptBase64(s, CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
            } else {
                log.info("密码为空");
                return s;
            }

        } catch (Exception e) {
            log.warn("encodePassword failed");
            return s;
        }
    }
}
