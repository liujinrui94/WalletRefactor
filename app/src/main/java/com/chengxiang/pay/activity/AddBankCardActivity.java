package com.chengxiang.pay.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.chengxiang.pay.R;
import com.chengxiang.pay.baiduocr.FileUtil;
import com.chengxiang.pay.baiduocr.RecognizeService;
import com.chengxiang.pay.baiduocr.ui.camera.CameraActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsAddBankCard;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.custom.CustomEditText;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.PermissionsUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.chengxiang.pay.presenter.BankPresenter;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.view.BankView;
import com.chengxiang.pay.view.MineView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/27 16:44
 * @description: 添加信用卡
 */

public class AddBankCardActivity extends BaseActivity implements View.OnClickListener, BankView.AddBankCardView, MineView.UserInfoView {

    private EditText bankNameEt;//银行名称
    private CustomEditText creditCardEt;//信用卡号
    private CustomEditText bankCardPhoneEt;//预留手机号

    private String bankName;//银行名称
    private String bankNum;//银行卡号
    private String bankPhone;//预留手机号

    private static final int REQUEST_CODE_CREDIT_CARD = 111;

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int RC_SETTING_CAMERA = 1;

    private final String[] needPermissions = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        progressShow();
        new MinePresenter.UserInfo(this).PostUserInfo();
    }

    private void initView() {
        setTitle("添加信用卡");
        TextView trueNameTv = (TextView) findViewById(R.id.add_bank_card_true_name_tv);
        bankNameEt = (EditText) findViewById(R.id.add_bank_card_bank_name_et);
        creditCardEt = (CustomEditText) findViewById(R.id.add_bank_card_credit_card_cet);
        bankCardPhoneEt = (CustomEditText) findViewById(R.id.add_bank_card_phone_cet);

        ImageView creditCardIv = (ImageView) findViewById(R.id.add_bank_card_credit_card_iv);
        Button submitBtn = (Button) findViewById(R.id.add_bank_card_submit_bt);

        trueNameTv.setText(BaseBean.getRealName());

        creditCardIv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        initAccessTokenWithAkSk();//初始化COR
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Logger.v(token);
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                showDialog("识别功能暂时无法使用，请手动输入");
            }
        }, getApplicationContext(), Constant.BAIDU_OCR_AK, Constant.BAIDU_OCR_SK);
    }

    /**
     * 消息展示
     */
    private void showDialog(String string) {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setDetail(string);
        commonDialog.setDetailCenter(true);
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_bank_card_credit_card_iv:
                if (PermissionsUtil.hasPermissions(this, needPermissions)) {
                    intoBdDistinguish();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.add_bank_card_submit_bt:
                getRequestParams();
                break;
        }
    }

    //进入到百度OCR识别
    private void intoBdDistinguish() {
        Intent intentCredit = new Intent(AddBankCardActivity.this, CameraActivity.class);
        intentCredit.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intentCredit.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intentCredit, REQUEST_CODE_CREDIT_CARD);
    }

    private void getRequestParams() {
        bankName = bankNameEt.getText().toString().trim();
        bankNum = creditCardEt.getCustomEditText().trim();
        bankPhone = bankCardPhoneEt.getCustomEditText().trim();
        if (TextUtils.isEmpty(bankName)) {
            ToastUtils.showLongToast("请输入所属银行");
            return;
        }
        if (TextUtils.isEmpty(bankNum)) {
            ToastUtils.showLongToast("请输入卡号");
            return;
        }
        if (TextUtils.isEmpty(bankPhone) || !StringUtil.isMobileNumber(bankPhone)) {
            ToastUtils.showLongToast("请输入预留手机号");
            return;
        }
        progressShow();
        new BankPresenter.AddBankCard(this).PostAddBankCard();
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getAddBankCardJsonString() {
        RequestParamsAddBankCard request = new RequestParamsAddBankCard();
        RequestUtils.initRequestBean(request, encryptManager, "9102");

        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        try {
            request.setName(URLEncoder.encode(BaseBean.getRealName(), "utf-8"));
            request.setBankName(URLEncoder.encode(bankName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setCertNo(encryptManager.getEncryptDES(BaseBean.getCertCode()));
        request.setCarNo(encryptManager.getEncryptDES(bankNum));
        request.setBankPhone(bankPhone);

        String[] signs = {"phoneNum", "certNo", "carNo"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void addBankCardResponse() {
        progressCancel();
        ToastUtils.showLongToast("添加成功");
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public String getUserInfoJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "3009");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void userInfoResponse() {
        progressCancel();
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    intoBdDistinguish();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
                    // This will display a dialog directing them to enable the permission in app settings.
                    if (PermissionsUtil.somePermissionPermanentlyDenied(this, needPermissions)) {
                        PermissionsUtil.goSettings2Permissions(this, getString(R.string.request_permission_camera_again)
                                , "去设置", RC_SETTING_CAMERA);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREDIT_CARD && resultCode == RESULT_OK) {
            RecognizeService.recBankCard(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            if (TextUtils.equals("识别错误", result)) {
                                showDialog("信用卡信息识别失败,请手动输入");
                            } else {
                                String[] strings = result.split(",");
                                creditCardEt.setText(strings[0]);
                                bankNameEt.setText(strings[1]);
                            }
                        }
                    });
        } else if (requestCode == RC_SETTING_CAMERA) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtils.showShortToast("从设置界面返回");
        }
    }
}
