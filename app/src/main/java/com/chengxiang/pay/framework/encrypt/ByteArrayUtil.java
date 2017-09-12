package com.chengxiang.pay.framework.encrypt;

public class ByteArrayUtil {

    public static String hexChars = "0123456789ABCDEF";

    /**
     * byte数组转换成Hex字符串
     *
     * @param data
     * @return
     */
    public static String byteArray2HexString(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            byte lo = (byte) (0x0f & data[i]);
            byte hi = (byte) ((0xf0 & data[i]) >>> 4);
            sb.append(hexChars.charAt(hi)).append(hexChars.charAt(lo));
        }
        return sb.toString();
    }

    /**
     * Hex字符串转换成byte数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexString2ByteArray(String hexStr) {
        if (hexStr.length() % 2 != 0) {
            return null;
        }
        byte[] data = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            char hc = hexStr.charAt(2 * i);
            char lc = hexStr.charAt(2 * i + 1);
            byte hb = ByteUtil.hexChar2Byte(hc);
            byte lb = ByteUtil.hexChar2Byte(lc);
            if (hb < 0 || lb < 0) {
                return null;
            }
            int n = hb << 4;
            data[i] = (byte) (n + lb);
        }
        return data;
    }
}
