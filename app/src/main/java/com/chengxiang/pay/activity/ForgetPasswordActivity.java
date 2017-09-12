package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.RequestParamsForgetPassword;
import com.chengxiang.pay.bean.RequestParamsGetVerifiedCode;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.custom.CustomEditText;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.LoginPresenter;
import com.chengxiang.pay.view.LoginView;
import com.google.gson.Gson;

import static com.chengxiang.pay.framework.base.BaseApplication.safeExit;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/3 17:58
 * @description: 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity implements
        LoginView.UserGetVerifiedCodeView, LoginView.UserResetPasswordView, View.OnClickListener {
    private CustomEditText phoneEt;//手机号
    private EditText messageCodeEt,//短信验证码
            passwordEt, passwordAgainEt;//新密码
    private TextView getVerifiedTv;//获取验证码

    private String phoneNumber  //注册人手机号码
            , messageCode  //验证码
            , password;  //密码

    private boolean isGetVerifiedCode = false;// 是否获取验证码

    /**
     * 倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getVerifiedTv.setText(millisUntilFinished / 1000 + "秒");
            getVerifiedTv.setClickable(false);
        }

        @Override
        public void onFinish() {
            getVerifiedTv.setText(getResources().getString(R.string.get_identifying_code));
            getVerifiedTv.setClickable(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        setTitle("忘记密码");
        phoneEt = (CustomEditText) findViewById(R.id.forget_phone_cet);// 手机号
        messageCodeEt = (EditText) findViewById(R.id.forget_message_code_et);// 验证码
        passwordEt = (EditText) findViewById(R.id.forget_password_et);// 密码
        passwordAgainEt = (EditText) findViewById(R.id.forget_password_again_et);// 密码
        getVerifiedTv = (TextView) findViewById(R.id.forget_get_verified_tv);// 获取验证码
        Button confirmBtn = (Button) findViewById(R.id.forget_make_sure_btn);

        getVerifiedTv.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.forget_get_verified_tv:
                getVerifiedCode();
                break;
            case R.id.forget_make_sure_btn:
                modifyPassword();
                break;
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }

    }

    //修改密码
    private void modifyPassword() {
        messageCode = messageCodeEt.getText().toString().trim();
        phoneNumber = phoneEt.getCustomEditText().trim();
        password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber) || !StringUtil.isMobileNumber(phoneNumber)) {
            ToastUtils.showShortToast("请输入有效手机号");
            return;
        }
        if (!isGetVerifiedCode) {
            ToastUtils.showShortToast("请获取验证码");
            return;
        }
        if (TextUtils.isEmpty(messageCode) || messageCode.length() != 6) {
            ToastUtils.showShortToast("请输入有效的验证码");
            return;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastUtils.showShortToast("请输入长度6-20位的密码");
            return;
        }
        if (!TextUtils.equals(password, passwordAgainEt.getText().toString().trim())) {
            ToastUtils.showShortToast("两次密码输入不一致，请重新输入");
            passwordAgainEt.setText("");
            return;
        }
        progressShow();
        new LoginPresenter.UserResetPwd(this).PostUserResetPwd();
    }

    //获取验证码
    private void getVerifiedCode() {
        phoneNumber = phoneEt.getCustomEditText().trim();
        if (TextUtils.isEmpty(phoneNumber) || !StringUtil.isMobileNumber(phoneNumber)) {
            ToastUtils.showShortToast("请输入有效的手机号");
        } else {
            new LoginPresenter.UserGetVerifiedCode(this).PostUserGetVerifiedCode();
            progressShow();
            countDownTimer.start();
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getVerifiedCodeJsonString() {
        RequestParamsGetVerifiedCode request = new RequestParamsGetVerifiedCode();
        RequestUtils.initRequestBean(request, encryptManager, "1001");
        request.setPhoneNum(encryptManager.getEncryptDES(phoneNumber));
        request.setType("1");
        String[] signs = {"phoneNum", "type"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void callVerifiedCode(String isUser) {
        progressCancel();
        SnackBarUtils.showShort(phoneEt, "验证码发送成功");
        isGetVerifiedCode = true;
    }

    @Override
    public String getUserResetPasswordJsonString() {
        RequestParamsForgetPassword request = new RequestParamsForgetPassword();
        RequestUtils.initRequestBean(request, encryptManager, "3006");
        request.setCode(messageCode);
        request.setNewPwd(encryptManager.getEncryptDES(encryptManager
                .encryptByMd5AndBASE64(password)));
        request.setPhoneNum(encryptManager.getEncryptDES(phoneNumber));
        String[] signs = {"code", "newPwd", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void callResetPasswordResponse() {
        progressCancel();
        safeExit();
        finish();
    }
}
