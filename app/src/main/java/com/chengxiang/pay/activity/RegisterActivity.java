package com.chengxiang.pay.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsGetVerifiedCode;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestParamsRegister;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.custom.CustomEditText;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.ImageUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.LoginPresenter;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.view.LoginView;
import com.chengxiang.pay.view.MineView;
import com.google.gson.Gson;

import java.io.File;

import static com.chengxiang.pay.R.id.register_btn;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/2 14:56
 * @description: 注册页面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, LoginView.UserGetVerifiedCodeView, LoginView.UserRegisterView, MineView.UploadFileView {

    private CustomEditText registerPhoneEt, //注册手机号
            registerInvitePhoneEt;//邀请人手机号
    private EditText registerMessageCodeEt, //短信验证码
            registerPasswordEt, registerPasswordAgainEt; //注册密码

    private TextView getVerifiedTv;//获取验证码
    private String phoneNumber  //注册人手机号码
            , messageCode  //验证码
            , password    //密码
            , tjUserPhone;//邀请人手机号
    private boolean isGetVerifiedCode = false;// 是否获取验证码

    private File signFile;//上传的签名文件
    private String signImgPath;//签名图片地址

    private String isUser;

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
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        registerPhoneEt = (CustomEditText) findViewById(R.id.register_phone_cet);
        getVerifiedTv = (TextView) findViewById(R.id.register_get_verified_tv);
        registerMessageCodeEt = (EditText) findViewById(R.id.register_message_code_et);
        registerPasswordEt = (EditText) findViewById(R.id.register_password_et);
        registerPasswordAgainEt = (EditText) findViewById(R.id.register_password_again_et);
        registerInvitePhoneEt = (CustomEditText) findViewById(R.id.register_invite_phone_cet);

        TextView userAgreementTv = (TextView) findViewById(R.id.register_user_agreement_tv);
        TextView signNameTv = (TextView) findViewById(R.id.register_sign_name_tv);

        Button registerBtn = (Button) findViewById(register_btn);//注册

        if (TextUtils.isEmpty(BaseBean.getOpenId())) {
            setTitle(getResources().getString(R.string.register));
            registerBtn.setText(getResources().getString(R.string.register));

        } else {
            setTitle("微信用户补充信息");
            registerBtn.setText("绑定手机号");
        }

        signNameTv.setOnClickListener(this);
        getVerifiedTv.setOnClickListener(this);
        userAgreementTv.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_get_verified_tv:
                getVerifiedCode();
                break;
            case R.id.register_user_agreement_tv:
                Intent intent = new Intent(RegisterActivity.this, UserAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.register_sign_name_tv:
                Intent intentSign = new Intent(RegisterActivity.this, UserSignActivity.class);
                startActivityForResult(intentSign, 1);
                break;
            case register_btn:
                checkRegisterUser();
                break;
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }
    }

    /**
     * 校验并注册
     */
    private void checkRegisterUser() {
        messageCode = registerMessageCodeEt.getText().toString();
        phoneNumber = registerPhoneEt.getCustomEditText().trim();
        password = registerPasswordEt.getText().toString();
        tjUserPhone = registerInvitePhoneEt.getCustomEditText().trim();

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
        if (TextUtils.equals("0", isUser)) {
            if (TextUtils.isEmpty(password)) {
                ToastUtils.showShortToast("请输入密码");
                return;
            }
            if (password.length() < 6 || password.length() > 20) {
                ToastUtils.showShortToast("请输入长度6-20位的密码");
                return;
            }
            if (!TextUtils.equals(password, registerPasswordAgainEt.getText().toString())) {
                ToastUtils.showShortToast("两次密码输入不一致，请重新输入");
                registerPasswordAgainEt.setText("");
                return;
            }

            if (!TextUtils.isEmpty(tjUserPhone)) {
                if (!StringUtil.isMobileNumber(tjUserPhone)
                        || TextUtils.equals(tjUserPhone, phoneNumber)) {
                    ToastUtils.showShortToast("请输入有效推荐人手机号,如果没有可不填");
                }
            } else {
                tjUserPhone = "13888888888";
            }
        }
        if (signFile == null) {
            ToastUtils.showShortToast("请签字同意《诚享钱包用户使用协议》");
            return;
        }
        progressShow();
        new MinePresenter.UploadFile(this).PostUploadFile();

    }

    /**
     * 获取手机验证码请求
     */
    private void getVerifiedCode() {
        phoneNumber = registerPhoneEt.getCustomEditText().trim();
        if (TextUtils.isEmpty(phoneNumber) || !StringUtil.isMobileNumber(phoneNumber)) {
            ToastUtils.showShortToast("请输入有效的手机号");
        } else {
            progressShow();
            new LoginPresenter.UserGetVerifiedCode(this).PostUserGetVerifiedCode();
            countDownTimer.start();
        }
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        if (msg.equals("用户已存在，请勿重复注册！")) {
            countDownTimer.cancel();
            getVerifiedTv.setText(getResources().getString(R.string.get_identifying_code));
            getVerifiedTv.setClickable(true);
        }
        checkCordError(msg);
    }

    @Override
    public String getVerifiedCodeJsonString() {
        RequestParamsGetVerifiedCode request = new RequestParamsGetVerifiedCode();
        RequestUtils.initRequestBean(request, encryptManager, "1001");
        request.setPhoneNum(encryptManager.getEncryptDES(phoneNumber));
        if (TextUtils.isEmpty(BaseBean.getOpenId())) {
            request.setType("0");
        } else {
            request.setType("2");
        }
        String[] signs = {"phoneNum", "type"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void callVerifiedCode(String isUser) {
        this.isUser = isUser;
        progressCancel();
        SnackBarUtils.showShort(registerPhoneEt, "验证码发送成功");
        isGetVerifiedCode = true;
        if (TextUtils.equals("1", isUser)) {
            registerPasswordEt.setText("已设置");
            registerPasswordAgainEt.setText("已设置");
            registerInvitePhoneEt.setText("已设置");
            registerPasswordEt.setEnabled(false);
            registerPasswordAgainEt.setEnabled(false);
            registerInvitePhoneEt.setEnabled(false);
        } else {
            registerPasswordEt.setText("");
            registerPasswordAgainEt.setText("");
            registerInvitePhoneEt.setText("");
            registerPasswordEt.setEnabled(true);
            registerPasswordAgainEt.setEnabled(true);
            registerInvitePhoneEt.setEnabled(true);
        }
        BaseBean.savePhoneNum(phoneNumber);
    }

    @Override
    public String getUserRegisterJsonString() {
        RequestParamsRegister request = new RequestParamsRegister();
        RequestUtils.initRequestBean(request, encryptManager, "0001");
        request.setCode(messageCode);
        request.setPhoneNum(encryptManager.getEncryptDES(phoneNumber));
        if (!TextUtils.isEmpty(tjUserPhone)) {
            request.setTjUserPhone(encryptManager.getEncryptDES(tjUserPhone));
        }
        request.setSignImg(signImgPath);
        request.setPwd(encryptManager.getEncryptDES(encryptManager.encryptByMd5AndBASE64(password)));
        request.setOpenId(BaseBean.getOpenId());
        String[] signs = {"phoneNum", "pwd", "tjUserPhone"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void callRegisterResponse(String intentType) {
        progressCancel();
        BaseBean.savePhoneNum(phoneNumber);
        if (TextUtils.equals(intentType, "wechat")) {
            ToastUtils.showShortToast("微信绑定成功");
        } else if (TextUtils.equals(intentType, "login")) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("className", "RegisterActivity");
            intent.putExtra("password", password);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri fileUri = Uri.parse(data.getStringExtra("fileName"));
            signFile = ImageUtil.getFileFromUri(RegisterActivity.this, fileUri);
        }
    }

    @Override
    public String getUploadFileJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "3002");
        request.setPhoneNum(BaseBean.getPhoneNum());
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public File getUploadFileImage() {
        return signFile;
    }

    @Override
    public void uploadFileResponse(String fileName) {
        progressCancel();
        signImgPath = fileName;
        progressShow();
        new LoginPresenter.UserRegister(this).PostUserRegister();
    }
}
