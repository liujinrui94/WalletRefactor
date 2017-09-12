package com.chengxiang.pay.framework.encrypt;

import com.orhanobut.logger.Logger;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

class PayEncrypt {
    private static final String Algorithm = "desede/CBC/PKCS5Padding"; //定义 加密算法,可用 DES,DESede,Blowfish
    // 向量
    private final static String iv = "01234567";
    private static String encoding = "utf-8";

    PayEncrypt() {
    }

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    static String encryptMode(String secretKey, String plainText)
            throws Exception {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(Algorithm);
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            return BASE64Util.encryptBASE64(encryptData);
        } catch (Exception e) {
            Logger.e("PayEncrypt + encryptMode + 加密错误" + e);
            throw new Exception("PayEncrypt + encryptMode + 加密错误" + e);
        }
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    static String decryptMode(String secretKey, String encryptText)
            throws Exception {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(Algorithm);
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
            byte[] decryptData = cipher.doFinal(BASE64Util.decryptBASE64(encryptText));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            Logger.e("PayEncrypt + encryptMode + 解密错误" + e);
            throw new Exception("PayEncrypt + encryptMode + 解密错误" + e);
        }

    }
}
