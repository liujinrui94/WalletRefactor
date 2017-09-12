package com.chengxiang.pay.framework.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 16:39
 * @description: MD5加密
 */

class MD5Util {
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * MD5
     *
     * @param b byte数组
     * @return String byte数组处理后字符串
     */
    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * MD5运算
     *
     * @param s 传入明文
     * @return String 返回密文
     */
    public static String MD5String(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * MD5运算
     *
     * @param s
     * @return
     */
    public static byte[] MD5Bytes(String s) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
