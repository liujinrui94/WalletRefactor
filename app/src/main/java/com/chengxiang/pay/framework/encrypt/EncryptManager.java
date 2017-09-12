package com.chengxiang.pay.framework.encrypt;

import android.text.TextUtils;

import com.chengxiang.pay.framework.utils.StringUtil;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 15:36
 * @description: 加密工具类
 */

public class EncryptManager {
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx8X1ZwAyJfC2T5WTP/RHE6YHB+1gY+EFKfxJcwygBBvGM6TEHVmERRzfjPN6TZY+m4X5f7T/OvntYmhuh4cW/KyyupepBsX2TZlipqSFiGFOIhOH/iErlnKIL03aq9rt35MpixG5MiU0LB0grGxD/wNbi1EKJj3IBjMgnHOg3cwIDAQAB";
    private String pingKey = null;
    private String workKey = null;
    private String mobKey = null;

    private static EncryptManager encryptManager;

    public static synchronized EncryptManager getEncryptManager(){    //对获取实例的方法进行同步
        if (encryptManager == null)
            encryptManager = new EncryptManager();
        return encryptManager;
    }

    /**
     * 初始化加密管理者， 1.生成加密秘钥 2.生成验签秘钥
     *
     * @throws Exception
     */
    public void initEncrypt() throws Exception {
        pingKey = StringUtil.getRandomString(24);
        workKey = StringUtil.getRandomString(24);
        setMobKey(pingKey, workKey);
    }

    /**
     * 返回加密秘钥
     *
     * @return
     */
    public String getPingKey() {
        return pingKey;
    }

    /**
     * 返回验签秘钥
     *
     * @return
     */
    public String getWorkKey() {
        return workKey;
    }

    /**
     * 默默的设置RAS加密
     *
     * @param pingKey
     * @param workKey
     * @throws Exception
     */
    private void setMobKey(String pingKey, String workKey) throws Exception {
        mobKey = encryptKey(pingKey + workKey);
    }

    /**
     * 返回加密的秘钥保护字段
     *
     * @return
     */
    public String getMobKey() {
        return mobKey;
    }

    /**
     * 先Md5再Base64加密 用于密码加密
     */
    public String encryptByMd5AndBASE64(String pwd) {
        try {
            byte messageDigest[] = MD5Util.MD5Bytes(pwd);

            return BASE64Util.encryptBASE64(messageDigest).trim();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param desKey
     * @return
     * @throws Exception
     */
    public String encryptKey(String desKey) throws Exception {
        // 构造X509EncodedKeySpec对象
        byte[] keyBytes = BASE64Util.decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactor = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactor
                .generatePublic(keySpec);
        byte[] buybalSignBin = desKey.getBytes();
        byte[] plainSignBin;
        try {
            plainSignBin = RSAUtil.encrypt(rsaPublicKey, buybalSignBin);
        } catch (Exception e) {
            return "";
        }

        return BASE64Util.encryptBASE64(plainSignBin);
    }

    /**
     * 移动解密验签
     *
     * @param paramNames
     * @param map
     * @return
     * @throws Exception
     */
    public boolean verifyMobRequestSign(String[] paramNames,
                                        Map<String, String> map) throws Exception {
        String signHex = map.get("sign");
        if (TextUtils.isEmpty(signHex)) {
            return false;
        }
        PayEncrypt pe = new PayEncrypt();
        // 拼接签名原串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramNames.length; i++) {
            String v = map.get(paramNames[i]);
            if (null != v) {
                sb.append(v);
            }
        }
        String p = sb.toString().replaceAll(" ", "+");
        // 计算摘要
        String calSignHex = PaySign.md5(p);
        // 解密sign
        String decSign = pe.decryptMode(getWorkKey(), signHex);
        // 比较
        if (!calSignHex.equalsIgnoreCase(decSign)) {
            return false;
        }
        return true;
    }

    /**
     * 移动加密签名
     *
     * @param paramNames
     * @param map
     * @return
     * @throws Exception
     */
    public String getMobResSign(String[] paramNames, Map<String, String> map)
            throws Exception {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < paramNames.length; i++) {
            String v = map.get(paramNames[i].trim());
            if (null != v) {
                sb.append(v);
            }
        }
        String p = sb.toString().replaceAll(" ", "+");
        p = PaySign.md5(p);
        PayEncrypt pe = new PayEncrypt();
        String sign = pe.encryptMode(getWorkKey(), p);
        return sign;
    }

    /**
     * 字段加密
     *
     * @param str
     * @return
     */
    public String getEncryptDES(String str) {
        PayEncrypt payEncrypt = new PayEncrypt();
        try {
            return payEncrypt.encryptMode(getPingKey(), str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字段解密
     *
     * @param str
     * @return
     */
    public String getDecryptDES(String str) {
        PayEncrypt payEncrypt = new PayEncrypt();
        try {
            return payEncrypt.decryptMode(getPingKey(), str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字段解密
     *
     * @param str
     * @return
     */
    public String getDecryptDES(String str, String pinkey) {
        PayEncrypt payEncrypt = new PayEncrypt();
        try {
            return payEncrypt.decryptMode(pinkey, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
