package com.chengxiang.pay.framework.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.utils.InterfaceUtil;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 21:51
 * @description: 自定义全局记载ProgressDialog
 */


public class BaseProgressDialog extends ProgressDialog {
    private String dialogDetail;
    private Activity context;

    public BaseProgressDialog(Activity context) {
        super(context, R.style.BaseProgressDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_base_progress);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        // 设置背景
        InterfaceUtil.setBackgroundColor(context, 0.6f);

        TextView dialogTv = (TextView) findViewById(R.id.base_progress_dialog_tv);
        if (!TextUtils.isEmpty(dialogDetail)) {
            dialogTv.setText(dialogDetail);
        }

    }

    public void setDialogDetail(String dialogDetail) {
        this.dialogDetail = dialogDetail;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        InterfaceUtil.setBackgroundColor(context, 1.0f);
    }
}
