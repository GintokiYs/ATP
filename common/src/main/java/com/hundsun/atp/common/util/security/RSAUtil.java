package com.hundsun.atp.common.util.security;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;


public class RSAUtil {
    public static final String CHARSET = "UTF-8";

    public static final String RSA_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyAC4+7bctfgYbwZq80BDXvme6EH3i/0RQKUG7eymNtq/quNdP+qQxzhStw9k9SEdL5SvouZh+2SMNknV+IxxQ+2AsoGuCVjSuFZ9hc0WD5G0pxsK2E+8Kbb7LPpr3sYRy7p/j+GtKEOA662O556QnmBDpHPT+9x8+8T4bolLpqQIDAQAB";

    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIALj7tty1+BhvBmrzQENe+Z7oQfeL/RFApQbt7KY22r+q410/6pDHOFK3D2T1IR0vlK+i5mH7ZIw2SdX4jHFD7YCyga4JWNK4Vn2FzRYPkbSnGwrYT7wptvss+mvexhHLun+P4a0oQ4DrrY7nnpCeYEOkc9P73Hz7xPhuiUumpAgMBAAECgYAFtiOFvaUwaqApzRYo25DEyW1k3yFY8qmnnFFJgZ/RViSyJoTzIibZWcRYWIXMlXiDLwrwBF+IJ3G8XmASgHAou8jCheVwR6Wt43PE3BrxwJKtLG485FC0HDLQCHN8yTpB/6/XKzfh6dmmN3dTvVSwPi/tY7WS5Liqjzw7+MWpcQJBAOx8yxT42koT43AUqg1QoqdfYDbMzFFB4UumO12EieBLQMT7l9xaKSf6hJ/9HznOZJF1AXtPj51bZcaLJusuELkCQQDAsAAmhDX/r3L2yGozgTcu+KqNP1unuvD+UHj120+8rL5V6cQ2Wyjive0QCG4Sv9rumxqrMSHUEvlslZ9M1chxAkEAtDw4Gve+ho653IYjRJqcHKvYDGvxnEx1hM5gmFmXK1avOnH1v38Htux7f+POlKP0idd/FZgBgJBT9QnA06T8IQJAOlsTMJcJ4423O5Ym08JmfAtDW9O8ZmfvZJXqjEsHMxT97/cEPZIOJ91tVlW3QGZqX6kJwp72p8u3TnNh81srgQJBANiSQXHqh/BnsznItZScWvHrS+rvOZUz+CLI3unSqta4XSCxv/3ec7Zn4ip10anIIDYuqQ8grXCWE8KBm7Thqhc=";

    public static Map<String, String> createKeys(int keySize) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[RSA]");
        }
        kpg.initialize(keySize);
        KeyPair keyPair = kpg.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, publicKey);
            return Base64.encodeBase64String(rsaSplitCodec(cipher, 1, data.getBytes("UTF-8"), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到问题", e);
        }
    }

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, privateKey);
            return new String(rsaSplitCodec(cipher, 2, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到问题", e);
        }
    }

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, privateKey);
            return Base64.encodeBase64String(rsaSplitCodec(cipher, 1, data.getBytes("UTF-8"), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到问题", e);
        }
    }

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, publicKey);
            return new String(rsaSplitCodec(cipher, 2, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到问题", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == 2) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        try {
            while (datas.length > offSet) {
                byte[] buff;
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阈值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = createKeys(512);

        String str = "root";

        String encodedData = publicEncrypt(str, getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ5+2VK/nzZo0fx4GoTd/fcfrWGcDtYUJdUj85QkrO8GkMSMNNn1Hpz62E+hjfYzVmVM8LQSb5nKzCsXarkbkiUCAwEAAQ=="));
        System.out.println("密文\r\n" + encodedData);
        String decodedData = privateDecrypt(encodedData, getPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnn7ZUr+fNmjR/HgahN399x+tYZwO1hQl1SPzlCSs7waQxIw02fUenPrYT6GN9jNWZUzwtBJvmcrMKxdquRuSJQIDAQABAkAFw/eD2xSflhCNbpEIZErowm48LsEbaIA30Kb7rJnd2ACD7AX90YQIdn5ne9EMaQRjF9/5YqdyZcTlNQYdDSXBAiEA5cYN4r4rhw+gG7G82vK/dp3/CELd58Ywq/CNnoUTyeMCIQCwlg9L5bg2RBAexFAGUWfC/PesLDK5HA/kokdLHxASVwIgXN/TGQV5HpyIQOl0xqM8FLwEK9irTVXksnsHIz3zI7UCIQCBVhd0DphC3s0zy7OVPRCTp3a8G083d49IdXdkVScPVwIgCt+SAb8yyH4EPj2Tzf16MaqZj4VV6uYDE0lvm0i+j64="));
        System.out.println("解密后文字：\r\n" + decodedData);
    }
}
