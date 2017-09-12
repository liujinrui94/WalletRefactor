package com.chengxiang.pay.framework.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PaySign {
    public static String md5(String str) {
        //确定计算方法
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] data;
        try {
            data = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        byte[] md5Date = md5.digest(data);
        return ByteArrayUtil.byteArray2HexString(md5Date);
    }
}
