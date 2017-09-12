package com.chengxiang.pay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.utils.QRCodeUtil;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 14:27
 * @description: 我的推广
 */

public class ExtensionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);
        initView();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.mine_extension_code));
        ImageView extensionCodeIv = (ImageView) findViewById(R.id.extension_code_iv);//推广二维码
        Button extensionBtn = (Button) findViewById(R.id.extension_btn);//分享
        extensionBtn.setOnClickListener(this);

        String shareUrl = Constant.EXTENSION_URL + "/fvp-qp-business/"
                + BaseBean.getOrgId() + "/toReg.html"
                + "?tjUserPhone=" + BaseBean.getPhoneNum();
        Bitmap shareBitmap = QRCodeUtil.generateBitmap(shareUrl, 400, 400);
        extensionCodeIv.setImageBitmap(shareBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.extension_btn:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_share_icon);
                Intent intent = new Intent(ExtensionActivity.this,ShareActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bitmap);
                intent.putExtra("bundle", bundle);
                intent.putExtra("shareType","1");
                intent.putExtra("channelType","2");
                startActivity(intent);
                break;
        }
    }
}
