package com.chengxiang.pay.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/22 15:06
 * @description: 联系客服
 */


public class ContactCustomerDialog extends Dialog {
    private String title;
    private String detail;
    private int resId;
    private boolean isImageLL;
    private View.OnClickListener OnNegateClickListener;
    private View.OnClickListener OnPositiveClickListener;
    private String positive;
    private String negate;

    public ContactCustomerDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_contact_customer);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);

        LinearLayout textLL = (LinearLayout) findViewById(R.id.dialog_contact_customer_text_ll);
        ImageView imageLL = (ImageView) findViewById(R.id.dialog_contact_customer_ll_iv);
        if (isImageLL) {
            textLL.setVisibility(View.GONE);
            imageLL.setVisibility(View.VISIBLE);
        } else {
            textLL.setVisibility(View.VISIBLE);
            imageLL.setVisibility(View.GONE);
        }
        ImageView imageView = (ImageView) findViewById(R.id.dialog_contact_customer_iv);
        imageView.setImageResource(resId);

        TextView titleTv = (TextView) findViewById(R.id.dialog_contact_customer_title_tv);
        titleTv.setText(title);

        TextView detailTv = (TextView) findViewById(R.id.dialog_contact_customer_detail_tv);
        detailTv.setText(detail);


        TextView negateTv = (TextView) findViewById(R.id.dialog_contact_customer_negate_tv);
        if (OnNegateClickListener != null) {
            negateTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(negate))
                negateTv.setText(negate);
            negateTv.setOnClickListener(OnNegateClickListener);
        }
        TextView positiveTv = (TextView) findViewById(R.id.dialog_contact_customer_positive_tv);
        if (OnPositiveClickListener != null) {
            positiveTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(positive))
                positiveTv.setText(positive);
            positiveTv.setOnClickListener(OnPositiveClickListener);
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

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setIsImageLL(boolean isImageLL) {
        this.isImageLL = isImageLL;
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
