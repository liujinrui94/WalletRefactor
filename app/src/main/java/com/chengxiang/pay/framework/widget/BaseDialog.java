package com.chengxiang.pay.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.chengxiang.pay.R;


/**
 * @author: FengWenyao
 * @email: 86785221@qq.com
 * @time: 2017/5/7 22:27
 * @description: 可以替换背景的dialog
 */

public class BaseDialog extends Dialog {


    public BaseDialog(Context context, View view) {
        super(context, R.style.CommonDialog);
        setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

    }
}
