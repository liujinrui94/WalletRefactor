package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPaymentUpgrade;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.UserGradePresenter;
import com.chengxiang.pay.view.UserGradeView;
import com.google.gson.Gson;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/22 22:23
 * @description: 用户提升等级方式
 */

public class UserUpgradeModeActivity extends BaseActivity implements View.OnClickListener, UserGradeView.PaymentUpgradeView {
    private String levelId, orderAmt, levelName;
    private String type;//0 微信 1 支付宝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upgrade_mode);
        getData();
        initView();
    }

    private void getData() {
        levelId = getIntent().getStringExtra("levelId");
        orderAmt = getIntent().getStringExtra("orderAmt");
        levelName = getIntent().getStringExtra("levelName");
    }

    private void initView() {
        setTitle("升级到" + levelName);
        LinearLayout upgradeShareLL = (LinearLayout) findViewById(R.id.user_upgrade_share_ll);
        LinearLayout upgradeWechatLL = (LinearLayout) findViewById(R.id.user_upgrade_wechat_ll);
        LinearLayout upgradeAliPayLL = (LinearLayout) findViewById(R.id.user_upgrade_ali_pay_ll);
        TextView upgradeWechatDetailTv = (TextView) findViewById(R.id.user_upgrade_wechat_detail_tv);
        TextView upgradeWechatAmountTv = (TextView) findViewById(R.id.user_upgrade_wechat_amount_tv);
        TextView upgradeAliPayDetailTv = (TextView) findViewById(R.id.user_upgrade_ali_pay_detail_tv);
        TextView upgradeAliPayAmountTv = (TextView) findViewById(R.id.user_upgrade_ali_pay_amount_tv);

        upgradeWechatDetailTv.setText("微信扫码支付");
        upgradeWechatAmountTv.setText(orderAmt + "元");
        upgradeAliPayDetailTv.setText("支付宝扫码支付");
        upgradeAliPayAmountTv.setText(orderAmt + "元");

        upgradeShareLL.setOnClickListener(this);
        upgradeWechatLL.setOnClickListener(this);
        upgradeAliPayLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.user_upgrade_share_ll:
                intent.setClass(UserUpgradeModeActivity.this, ExtensionActivity.class);
                startActivity(intent);
                break;
            case R.id.user_upgrade_wechat_ll:
                progressShow();
                type = "0";
                new UserGradePresenter.PaymentUpgrade(this).PostPaymentUpgrade();
                break;
            case R.id.user_upgrade_ali_pay_ll:
                progressShow();
                type = "1";
                new UserGradePresenter.PaymentUpgrade(this).PostPaymentUpgrade();
                break;
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getPaymentUpgradeJsonString() {
        RequestParamsPaymentUpgrade request = new RequestParamsPaymentUpgrade();
        RequestUtils.initRequestBean(request, encryptManager, "6002");
        request.setLevelId(levelId);
        request.setOrderAmt(encryptManager.getEncryptDES(orderAmt));
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setType(type);
        String[] signs = {"levelId", "orderAmt", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void paymentUpgradeResponse(String url) {
        progressCancel();
        Intent intent = new Intent(UserUpgradeModeActivity.this, PaymentCodeActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        intent.putExtra("paymentType", "0");
        startActivity(intent);
    }
}
