package com.chengxiang.pay.framework.encrypt;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具类
 *
 * @author duronggang email: duronggang@buybal.com 2016年6月17日
 */
public class SignUtil {

    /**
     * 参数签名
     *
     * @param encryptManager
     * @param obj
     * @param params
     * @return
     */
    public static String signNature(EncryptManager encryptManager, Object obj,
                                    String[] params) {
        String paramNames[];
        if (params != null) {
            paramNames = new String[6 + params.length];
        } else {
            paramNames = new String[6];
        }

        paramNames[0] = "IMEI";
        paramNames[1] = "IP";
        paramNames[2] = "MAC";
        paramNames[3] = "funCode";
        paramNames[4] = "mobKey";
        paramNames[5] = "seq";

        if (params != null) {
            System.arraycopy(params, 0, paramNames, 6, params.length);
        }
        Map<String, String> map = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                Object object = null;
                for (String paramName : paramNames) {
                    String field = methodName.substring(3);
                    if (TextUtils.equals(field, paramName)
                            || TextUtils.equals(field.substring(0, 1)
                                    .toLowerCase() + field.substring(1),
                            paramName)) {
                        try {
                            object = method.invoke(obj);
                        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        if (object != null
                                && !TextUtils.isEmpty(object.toString())) {
                            map.put(paramName, object.toString());
                        }
                    }
                }
            }
        }
        try {
            return encryptManager.getMobResSign(paramNames, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 校验签名
     *
     * @param encryptManager
     * @param obj
     * @param params
     * @return
     */
    public static boolean verifyParams(EncryptManager encryptManager, Object obj,
                                       String[] params) {

        String paramNames[];
        if (params != null) {
            paramNames = new String[3 + params.length];
        } else {
            paramNames = new String[3];
        }
        paramNames[0] = "funCode";
        paramNames[1] = "retCode";
        paramNames[2] = "seq";

        if (params != null) {
            System.arraycopy(params, 0, paramNames, 3, params.length);
        }
        Map<String, String> map = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                Object object = null;
                for (String paramName : paramNames) {
                    String field = methodName.substring(3);
                    if (TextUtils.equals(field, paramName)
                            || TextUtils.equals(field.substring(0, 1)
                                    .toLowerCase() + field.substring(1),
                            paramName)) {
                        try {
                            object = method.invoke(obj);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (object != null
                                && !TextUtils.isEmpty(object.toString())) {
                            map.put(paramName, object.toString());
                        }
                    }
                }
            }
            if (TextUtils.equals(methodName.substring(3).substring(0, 1)
                    .toLowerCase()
                    + methodName.substring(3).substring(1), "sign")) {
                try {
                    map.put("sign", method.invoke(obj, new Object[]{}).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return encryptManager.verifyMobRequestSign(paramNames, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
