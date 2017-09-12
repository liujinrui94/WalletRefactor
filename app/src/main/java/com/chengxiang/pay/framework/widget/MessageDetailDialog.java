package com.chengxiang.pay.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/23 15:48
 * @description: 消息详细
 */


public class MessageDetailDialog extends Dialog {
    private String detail;
    private String date;
    private String time;
    private View.OnClickListener OnCloseClickListener;

    public MessageDetailDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_message_detail);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);


        TextView detailTv = (TextView) findViewById(R.id.dialog_message_detail_tv);
        detailTv.setText(detail);

        TextView dateTv = (TextView) findViewById(R.id.dialog_message_date_tv);
        dateTv.setText(date);
        TextView timeTv = (TextView) findViewById(R.id.dialog_message_time_tv);
        timeTv.setText(time);

        ImageView messageCloseIv = (ImageView) findViewById(R.id.dialog_message_close_iv);

        if (OnCloseClickListener != null) {
            messageCloseIv.setOnClickListener(OnCloseClickListener);
        }

        setCanceledOnTouchOutside(false);
        setCancelable(false);

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    /**
     * 关闭按钮
     */
    public void setOnCloseClickListener(View.OnClickListener onCloseClickListener) {
        this.OnCloseClickListener = onCloseClickListener;
    }
}
