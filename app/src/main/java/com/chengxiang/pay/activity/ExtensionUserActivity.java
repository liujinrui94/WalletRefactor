package com.chengxiang.pay.activity;

import android.os.Bundle;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 14:29
 * @description: 推广的用户
 */

public class ExtensionUserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_user);
        initView();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.mine_client));
    }
}
