package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 18:17
 * @description: 正在开发中界面
 */

public class DevelopingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developing);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        TextView backTv = (TextView) findViewById(R.id.develop_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
