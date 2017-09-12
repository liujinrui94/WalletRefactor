package com.chengxiang.pay.apshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.BaseReq;
import com.alipay.share.sdk.openapi.BaseResp;
import com.alipay.share.sdk.openapi.IAPAPIEventHandler;
import com.alipay.share.sdk.openapi.IAPApi;
import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/26 12:21
 * @description: 支付宝返回
 */

public class ShareEntryActivity extends BaseActivity implements IAPAPIEventHandler {
    private IAPApi api;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = APAPIFactory.createZFBApi(getApplicationContext(), Constant.ALI_PAY_APP_ID, false);
        Intent intent = getIntent();
        api.handleIntent(intent, this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.send_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.send_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.validation_failure;
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = R.string.send_failure;
                break;
            default:
                result = R.string.unknown_error;
                break;
        }
        String aliResult = getResources().getString(result);
        Toast.makeText(this, aliResult, Toast.LENGTH_LONG).show();
        ShareEntryActivity.this.finish();
    }
}
