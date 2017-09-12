package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsLogin;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsLogin;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.custom.CustomEditText;
import com.chengxiang.pay.framework.encrypt.Des3Encrypt;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.LoginPresenter;
import com.chengxiang.pay.view.LoginView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView.UserLoginView {
    private CustomEditText phoneNumberEt;//账号
    private EditText passwordEt;//密码
    private CheckBox rememberPasswordCb;//记住密码

    private String phoneNumber, password;//账号,密码

    private SharedPreferences sharedPreferences;
    private Des3Encrypt des3Encrypt;//3DES加密

    private String loginType;//0 账号密码登录 1 微信登录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressCancel();//防止微信启动错误返回后还有登录提示
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressCancel();
        if ("WXEntryActivity".equals(getIntent().getStringExtra("className"))
                && !TextUtils.isEmpty(BaseBean.getWxCode())) {
            progressShow("微信用户登录中...");
            loginType = "1";
            new LoginPresenter.UserLogin(this).PostUserLogin();
        } else if ("RegisterActivity".equals(getIntent().getStringExtra("className"))) {
            progressShow("用户登录中...");
            loginType = "0";
            phoneNumber = BaseBean.getPhoneNum();
            password = getIntent().getStringExtra("password");
            new LoginPresenter.UserLogin(this).PostUserLogin();
        }
        BaseBean.saveOpenId("");//清空微信绑定标识
        initAccountPassword();
    }

    private void initView() {
        phoneNumberEt = (CustomEditText) findViewById(R.id.login_phone_number_cet);
        passwordEt = (EditText) findViewById(R.id.login_password_et);
        rememberPasswordCb = (CheckBox) findViewById(R.id.login_remember_password_checkbox);
        TextView forgetPasswordTv = (TextView) findViewById(R.id.login_forget_password_tv);//忘记密码
        Button loginBtn = (Button) findViewById(R.id.login_btn);//登录按钮
        Button wechatLoginBtn = (Button) findViewById(R.id.login_wechat_btn);//微信登录

        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    new LoginPresenter.UserLogin(LoginActivity.this).PostUserLogin();
                }
                return false;
            }
        });

        findViewById(R.id.login_register_tv).setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        wechatLoginBtn.setOnClickListener(this);
    }

    /**
     * 初始化账号密码
     */
    private void initAccountPassword() {

        sharedPreferences = getSharedPreferences("AccountPassword", MODE_PRIVATE);
        boolean rememberPasswordIsChecked = sharedPreferences
                .getBoolean("rememberPasswordIsChecked", false);
        if (rememberPasswordIsChecked) {
            rememberPasswordCb.setChecked(true);
            phoneNumber = sharedPreferences.getString("username", "");
            String pwd = sharedPreferences.getString("password", "");

            if (!TextUtils.isEmpty(phoneNumber)) {
                phoneNumberEt.setText(phoneNumber);
            }
            if (!TextUtils.isEmpty(pwd)) {
                if (des3Encrypt == null) {
                    des3Encrypt = new Des3Encrypt();
                }
                try {
                    password = des3Encrypt.getDecryptDES(pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                    password = pwd;
                }
                passwordEt.setText(password);
            }
        } else {
            rememberPasswordCb.setChecked(false);
        }
    }

    /**
     * 保存账号密码
     */
    private void saveAccountPassword() {

        // 获取SharedPreferences对象
        sharedPreferences = this.getSharedPreferences("AccountPassword", Context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (rememberPasswordCb.isChecked()) {
            if (des3Encrypt == null) {
                des3Encrypt = new Des3Encrypt();
            }
            // 设置参数
            editor.putString("username", phoneNumber);
            editor.putString("password", des3Encrypt.getEncryptDES(password));
            editor.putBoolean("rememberPasswordIsChecked", true);
        } else {
            editor.putBoolean("rememberPasswordIsChecked", false);
        }
        // 提交
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register_tv:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.login_forget_password_tv:
                Intent intentForget = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intentForget);
                break;
            case R.id.login_btn:
                loginType = "0";
                checkNumberLogin();
                break;
            case R.id.login_wechat_btn:
                loginType = "1";
                wechatLogin(v);
                break;
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }
    }

    /**
     * 微信登录
     *
     * @param v
     */
    private void wechatLogin(View v) {
        String APP_ID = Constant.WECHAT_APP_ID;
        //通过WXAPIFactory工厂，获取IWXAPI实例
        IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID);
        //将应用appId注册到微信
        api.registerApp(APP_ID);
        if (!api.isWXAppInstalled()) {
            SnackBarUtils.showLong(v, "未检测到微信客户端，请先下载安装！");
            return;
        }
        progressShow();
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "login";
        api.sendReq(req);
    }

    /**
     * 校验账号密码后登录
     */
    private void checkNumberLogin() {
        phoneNumber = phoneNumberEt.getCustomEditText().trim();
        password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber) || !StringUtil.isMobileNumber(phoneNumber)) {
            ToastUtils.showShortToast("请输入有效手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast("请输入密码");
            return;
        }
        BaseBean.saveWxCode(null);//清除微信登录记录
        progressShow("登录中...");
        new LoginPresenter.UserLogin(this).PostUserLogin();
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getUserLoginJsonString() {
        RequestParamsLogin request = new RequestParamsLogin();
        RequestUtils.initRequestBean(request, encryptManager, "0002");
        if ("1".equals(loginType)) {
            //微信登录
            request.setLoginType(loginType);
            request.setCode(BaseBean.getWxCode());
        } else {
            request.setPhoneNum(encryptManager.getEncryptDES(phoneNumber));
            request.setPassword(encryptManager.getEncryptDES(encryptManager
                    .encryptByMd5AndBASE64(password)));
            request.setLoginType(loginType);
            request.setCode(null);
        }
        String[] signs = {"password", "phoneNum", "loginType"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void startActivity(ResponseParamsLogin info) {
        if (TextUtils.isEmpty(BaseBean.getWxCode())) {
            saveAccountPassword();
        } else {
            BaseBean.saveWxCode(null);//登录一次，清空一次
        }
        BaseBean.saveProcessAudit(info.getProcessAduit());
        BaseBean.saveLevel(info.getLevel());
        BaseBean.saveLevelStr(info.getLevelStr());
        BaseBean.saveOpenId(encryptManager.getDecryptDES(info.getOpenId()));
        BaseBean.saveHeadImgUrl(info.getHeadImg());
        BaseBean.saveNickName(info.getNickName());
        BaseBean.savePhoneNum(info.getId());
        BaseBean.saveIsBinder(info.getIsBind());
        BaseBean.saveVersion(info.getVersion());
        BaseBean.saveIsLogin(true);
        setJGPushAlias();//设置极光推送别名
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        progressCancel();
    }

    private void setJGPushAlias() {
        JPushMessage jPushMessage = new JPushMessage();
        int sequence = jPushMessage.getSequence();
        JPushInterface.setAlias(getApplicationContext(), sequence, BaseBean.getPhoneNum());
    }
}
