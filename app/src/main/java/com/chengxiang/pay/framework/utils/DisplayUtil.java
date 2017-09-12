package com.chengxiang.pay.framework.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/9 15:43
 * @description: 屏幕相关工具类
 */


public class DisplayUtil {

    /**
     * @return 获取屏幕宽度
     */
    public static int getWindowWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * @return 获取屏幕高度
     */
    public static int getWindowHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * @return dp转px
     */
    public static int dpToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @return px转dp
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
