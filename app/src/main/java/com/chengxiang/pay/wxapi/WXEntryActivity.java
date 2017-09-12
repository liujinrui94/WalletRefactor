package com.chengxiang.pay.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengxiang.pay.activity.LoginActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 23:29
 * @description: 微信平台返回后使用，修改参考微信开发者文档
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID, true);
        api.registerApp(Constant.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        progressCancel();
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK: // 用户同意
                try {
                    SendAuth.Resp response = (SendAuth.Resp) baseResp;
                    if (TextUtils.equals("login", ((SendAuth.Resp) baseResp).state)) {
                        Logger.d("微信返回_用户同意__" + "onResp: " + response.code);
                        BaseBean.saveWxCode(response.code);
                        startActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
                ToastUtils.showShortToast("用户取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:// 用户拒绝授权
                ToastUtils.showShortToast("用户拒绝授权");
                finish();
                break;
            default:
                Logger.d("微信返回_位置问题__" + "onResp: 登录失败");
                ToastUtils.showShortToast("登录失败");
                finish();
                break;
        }
    }

    private void startActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("className", "WXEntryActivity");
        startActivity(intent);
        finish();
    }
}
