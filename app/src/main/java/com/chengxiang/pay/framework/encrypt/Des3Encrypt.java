package com.chengxiang.pay.framework.encrypt;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 22:08
 * @description: 3DES加密
 */

public class Des3Encrypt {

    private String privateKey = "qdcftx20170701fengwenyao";

    /**
     * 字段加密
     *
     * @param str
     * @return
     */
    public String getEncryptDES(String str) {
        try {
            return PayEncrypt.encryptMode(privateKey, str);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 字段解密
     *
     * @param str
     * @return
     */
    public String getDecryptDES(String str) {
        try {
            return PayEncrypt.decryptMode(privateKey, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
