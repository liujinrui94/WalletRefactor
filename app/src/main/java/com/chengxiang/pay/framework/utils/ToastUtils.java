package com.chengxiang.pay.framework.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import static com.chengxiang.pay.framework.base.BaseApplication.applicationContext;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/21 16:40
 * @description: toast工具类
 */


public class ToastUtils {
    private static Toast toast;
    private static Context context = applicationContext;

    public static void showShortToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showCenterToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void toastCancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
