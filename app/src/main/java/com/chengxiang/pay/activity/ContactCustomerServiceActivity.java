package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.utils.ImageUtil;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.ContactCustomerDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 14:37
 * @description: 联系客服
 */

public class ContactCustomerServiceActivity extends BaseActivity implements View.OnClickListener {

    private Context context;

    @BindView(R.id.contact_customer_phone_iv)//客服热线
            ImageView phoneIv;
    @BindView(R.id.contact_customer_wechat_iv)//微信客服
            ImageView wechatIv;
    @BindView(R.id.contact_customer_qq_iv)//QQ客服
            ImageView qqIv;
    @BindView(R.id.contact_customer_business_iv)//商务合作
            ImageView businessIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_customer_service);
        context = this;
        setTitle(getResources().getString(R.string.contact_customer_service));
    }

    @OnClick({R.id.contact_customer_phone_iv, R.id.contact_customer_wechat_iv
            , R.id.contact_customer_qq_iv, R.id.contact_customer_business_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_customer_phone_iv:
                showContactCustomerDialog(1, R.mipmap.ic_contact_customer_phone, "拨打客服电话"
                        , getResources().getString(R.string.hot_line), "拨打", false);
                break;
            case R.id.contact_customer_wechat_iv:
                showContactCustomerDialog(2, R.mipmap.ic_contact_customer_phone, "拨打客服电话"
                        , getResources().getString(R.string.hot_line), "保存", true);
                break;
            case R.id.contact_customer_qq_iv:
                String qqNum = getResources().getString(R.string.qq_number).trim();
                if (InterfaceUtil.isAvailable(this, "com.tencent.mobileqq")//腾讯QQ
                        || InterfaceUtil.isAvailable(this, "com.tencent.qqlite")//QQ轻聊版
                        || InterfaceUtil.isAvailable(this, "com.tencent.tim")) {//TIM
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum + "&version=1")));
                } else {
                    ToastUtils.showLongToast("本机未安装QQ应用");
                }
                break;
            case R.id.contact_customer_business_iv:
                showContactCustomerDialog(4, R.mipmap.ic_contact_customer_email, "发送至邮箱"
                        , getResources().getString(R.string.email), "确定", false);
                break;
        }
    }

    private void showContactCustomerDialog(final int typeId, int resId, final String title, String detail
            , String positive, boolean isImageLL) {
        final ContactCustomerDialog contactCustomerDialog = new ContactCustomerDialog(context);
        if (isImageLL) {
            contactCustomerDialog.setIsImageLL(isImageLL);
        } else {
            contactCustomerDialog.setIsImageLL(isImageLL);
            contactCustomerDialog.setResId(resId);
            contactCustomerDialog.setTitle(title);
            contactCustomerDialog.setDetail(detail);
        }
        contactCustomerDialog.setOnNegateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCustomerDialog.dismiss();
            }
        });
        contactCustomerDialog.setPositive(positive);
        contactCustomerDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (typeId) {
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + getResources().getString(R.string.hot_line).trim());
                        intent.setData(data);
                        startActivity(intent);
                        break;
                    case 2:
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_contact_customer_service_scan);
                        ImageUtil.saveFile(context, bitmap);
                        break;
                    case 4:
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822"); // 真机上使用这行
                        i.putExtra(Intent.EXTRA_EMAIL,
                                new String[] { getResources().getString(R.string.email).trim() });
                        startActivity(Intent.createChooser(i,
                                "Select email application"));
                        break;
                }
                contactCustomerDialog.dismiss();
            }
        });
        contactCustomerDialog.show();
    }


}
