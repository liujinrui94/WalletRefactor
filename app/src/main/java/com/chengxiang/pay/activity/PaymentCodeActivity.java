package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.utils.ImageUtil;
import com.chengxiang.pay.framework.utils.QRCodeUtil;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/23 16:32
 * @description: 付款码页面（升级付款、收款）
 */

public class PaymentCodeActivity extends BaseActivity implements View.OnClickListener {

    private TextView receiveUserTv;//收款人描述
    private TextView receiveAmountTv;//收款金额描述
    private TextView receiveTypeTv;//收款类型描述
    private TextView hintTv;//温馨提示
    private ImageView paymentCodeIv;//二维码图片
    private ImageView typeIv;//二维码类型微信、支付宝
    private String type;
    private LinearLayout paymentCodeLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_code);
        Context context = this;
        initView();
        initDate();
    }

    private void initView() {
        paymentCodeLL = (LinearLayout) findViewById(R.id.payment_code_ll);
        receiveUserTv = (TextView) findViewById(R.id.payment_code_receive_user_tv);
        receiveAmountTv = (TextView) findViewById(R.id.payment_code_receive_amount_tv);
        receiveTypeTv = (TextView) findViewById(R.id.payment_code_receive_type_tv);
        hintTv = (TextView) findViewById(R.id.payment_code_hint_tv);
        paymentCodeIv = (ImageView) findViewById(R.id.payment_code_receive_iv);
        typeIv = (ImageView) findViewById(R.id.payment_code_type_iv);
        Button shareBtn = (Button) findViewById(R.id.payment_code_share_btn);
        shareBtn.setOnClickListener(this);
    }

    private void initDate() {
        String url = getIntent().getStringExtra("url");
        //0 微信 1 支付宝
        type = getIntent().getStringExtra("type");
        //0 升级 1 收款
        String paymentType = getIntent().getStringExtra("paymentType");
        if (paymentType.equals("0")) {
            setTitle("升级");
            receiveUserTv.setVisibility(View.GONE);
            receiveAmountTv.setVisibility(View.GONE);
        } else {
            setTitle("收款");
            receiveUserTv.setVisibility(View.VISIBLE);
            receiveAmountTv.setVisibility(View.VISIBLE);
            String amt = getIntent().getStringExtra("amt");
            if (type.equals("0")) {
                receiveUserTv.setText(Html.fromHtml("<font color='#fd6e4c'>" + BaseBean.getMerchantName() + "</font>" + "<font color='#969696'>正在向您发起微信收款交易"));
            } else {
                receiveUserTv.setText(Html.fromHtml("<font color='#fd6e4c'>" + BaseBean.getMerchantName() + "</font>" + "<font color='#969696'>正在向您发起支付宝收款交易"));
            }
            receiveAmountTv.setText(Html.fromHtml("<font color='#969696'>交易金额为" + "</font>" + "<font color='#fd6e4c'>" + amt + "</font" + "<font color='#969696'>元</font"));
        }
        if (type.equals("0")) {
            typeIv.setImageResource(R.mipmap.ic_user_upgrade_wechat);
            receiveTypeTv.setText("使用微信扫描下方二维码，即可支付升级");
            hintTv.setText("您也可以分享到微信，长按识别二维码支付哦");
        } else {
            typeIv.setImageResource(R.mipmap.ic_user_upgrade_ali_pay);
            receiveTypeTv.setText("使用支付宝扫描下方二维码，即可支付升级");
            hintTv.setText("您也可以分享到支付宝，长按识别二维码支付哦");
        }
        Bitmap qrBitmap = QRCodeUtil.generateBitmap(url, 400, 400);
        paymentCodeIv.setImageBitmap(qrBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_code_share_btn:
                //intent不支持超过40k的bitmap传递
                Bitmap bitmap = ImageUtil.getBitmapFromView(paymentCodeLL);
                Uri bitmapUri = ImageUtil.getUriFromBitmap(bitmap);
                Intent intent = new Intent(PaymentCodeActivity.this, ShareActivity.class);
                intent.putExtra("imageUri", bitmapUri.toString());
                intent.putExtra("shareType", "0");
                intent.putExtra("channelType", type);
                startActivity(intent);
                break;
        }
    }
}
