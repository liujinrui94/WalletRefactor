package com.chengxiang.pay.framework.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 19:15
 * @description: 消息未读数显示
 */


public class MessageBarView extends RelativeLayout {

    private int msgCount;
    private TextView bar_num;

    public MessageBarView(Context context) {
        this(context, null);
    }

    public MessageBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_message_view, this, true);
        bar_num = (TextView) rl.findViewById(R.id.message_num);
    }

    public void setMessageCount(int count) {
        msgCount = count;
        if (count == 0) {
            bar_num.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 100) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("99+");
            }
        }
        invalidate();
    }

    public void addMsg() {
        setMessageCount(msgCount + 1);
    }
}
