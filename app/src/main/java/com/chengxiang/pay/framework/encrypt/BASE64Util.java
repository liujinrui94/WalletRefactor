package com.chengxiang.pay.framework.encrypt;

import android.util.Base64;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 16:40
 * @description: Base64加密
 */

class BASE64Util {
    private BASE64Util() {
    }

    /**
     * 解密BASE64
     *
     * @param key 密码
     * @return 解密结果
     * @throws Exception 异常
     */
    static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * 加密BASE64
     *
     * @param key 密码
     * @return 加密结果
     * @throws Exception 异常
     */
    static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

}
