package com.chengxiang.pay.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chengxiang.pay.R;


/**
 * @author: FengWenyao
 * @email: 86785221@qq.com
 * @time: 2017/5/7 22:27
 * @description: 自定义Dialog
 */

public class CommonDialog extends Dialog {
    private String title;
    private String detail;
    private boolean isCenter;
    private View.OnClickListener OnNegateClickListener;
    private View.OnClickListener OnPositiveClickListener;
    private String positive;
    private String negate;

    public CommonDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_common);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);

        TextView textTitle = (TextView) findViewById(R.id.common_dialog_title_tv);
        if (!TextUtils.isEmpty(title)) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(title);
        } else {
            textTitle.setVisibility(View.GONE);
        }

        TextView textDetail = (TextView) findViewById(R.id.common_dialog_detail_tv);
        if (!TextUtils.isEmpty(detail)) {
            textDetail.setVisibility(View.VISIBLE);
            textDetail.setText(detail);
        }
        if (isCenter) {
            textDetail.setGravity(Gravity.CENTER);
        }
        TextView divider = (TextView) findViewById(R.id.common_dialog_dividers_tv);
        TextView negateTv = (TextView) findViewById(R.id.common_dialog_negate_tv);
        if (OnNegateClickListener != null) {
            negateTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(negate))
                negateTv.setText(negate);
            negateTv.setOnClickListener(OnNegateClickListener);
        } else {
            divider.setVisibility(View.GONE);
            negateTv.setVisibility(View.GONE);
        }
        TextView positiveTv = (TextView) findViewById(R.id.common_dialog_positive_tv);
        if (OnPositiveClickListener != null) {
            positiveTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(positive))
                positiveTv.setText(positive);
            positiveTv.setOnClickListener(OnPositiveClickListener);
        } else {
            divider.setVisibility(View.GONE);
            positiveTv.setVisibility(View.GONE);
        }

        setCanceledOnTouchOutside(false);
        setCancelable(false);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDetailCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }


    /**
     * 确定按钮
     */
    public void setOnPositiveListener(View.OnClickListener onPositiveClickListener) {
        this.OnPositiveClickListener = onPositiveClickListener;
    }

    /**
     * 取消按钮
     */
    public void setOnNegateListener(View.OnClickListener onNegateClickListener) {
        this.OnNegateClickListener = onNegateClickListener;
    }

    public void setNegate(String negate) {
        this.negate = negate;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }
}
