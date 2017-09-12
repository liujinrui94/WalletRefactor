package com.chengxiang.pay.framework.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/21 16:39
 * @description: SnackBar工具类
 */


public class SnackBarUtils {
    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static Snackbar indefiniteSnackBar(View view, String msg) {
        return Snackbar.make(view,msg, Snackbar.LENGTH_INDEFINITE);
    }
}
