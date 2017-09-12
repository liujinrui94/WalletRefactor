package com.chengxiang.pay.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.SelectHeadOfficeActivity;
import com.chengxiang.pay.baiduocr.FileUtil;
import com.chengxiang.pay.baiduocr.RecognizeService;
import com.chengxiang.pay.baiduocr.ui.camera.CameraActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPerfectBank;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.custom.CustomEditText;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.PermissionsUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.view.MineView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.app.Activity.RESULT_OK;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/13 18:08
 * @description: 用户信息银行卡信息
 */

public class UserInfoBankDataFragment extends BaseFragment implements View.OnClickListener, MineView.PerfectBankDataView {
    private Context context;
    private View inflate;

    private EditText mEditName, mEditMerchant; //真实姓名、商户名称
    private CustomEditText mEditIdCard, mEditBankPhone,//身份证号、银行预留手机号
            mBankNumEdit, mCreditCardNumEdit;//银行卡号、信用卡号

    private TextView mTextBankName, mTextPrompt;//支行名称,提示
    private LinearLayout mLLBankName, mLLBankPhone, mLLCreditCard;
    private Button mBtnSubmit;

    private ImageView mIvIdCard, mIvBankCard, mIvCreditCard;//身份证拍照、银行卡拍照、信用卡拍照

    private String realName;//真名
    private String merName;//商户名
    private String idCardNum;//身份证号
    private String bankCardNum;//银行卡号
    private String bankMobile;//银行预留手机号
    private String bankId;//银行ID
    private String bankCityCode;//银行城市Code
    private String bankProvCode; //银行所在省Code
    private String cardBranchBank;//支行Code
    private String cardBranchName;//支行名称
    private String creditCard;//信用卡

    private static final int REQUEST_CODE_ID_CARD = 110;
    private static final int REQUEST_CODE_BANKCARD = 111;
    private static final int REQUEST_CODE_CREDIT_CARD = 112;
    private static final int REQUEST_CODE_BRANCH_BANK = 113;

    private String bdContentType;//百度OCR请求类型
    private int bdRequestCode;//百度OCR请求标记code

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int RC_SETTING_CAMERA = 1;

    private final String[] needPermissions = new String[]{Manifest.permission.CAMERA};

    private EncryptManager encryptManager;

    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_user_info_bank_data, container, false);
            initView();
            initAccessTokenWithAkSk();//初始化COR
        }
        return inflate;

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
                showDialog("show", "识别功能暂时无法使用，请手动输入", context);
            }
        }, getActivity().getApplicationContext(), Constant.BAIDU_OCR_AK, Constant.BAIDU_OCR_SK);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mEditName = (EditText) inflate.findViewById(R.id.my_info_true_name_et);
        mEditMerchant = (EditText) inflate.findViewById(R.id.my_info_merchant_name_et);
        mEditIdCard = (CustomEditText) inflate.findViewById(R.id.my_info_id_card_cet);
        mBankNumEdit = (CustomEditText) inflate.findViewById(R.id.my_info_bank_card_cet);
        mEditBankPhone = (CustomEditText) inflate.findViewById(R.id.my_info_bank_phone_cet);
        mCreditCardNumEdit = (CustomEditText) inflate.findViewById(R.id.my_info_credit_card_cet);

        mIvIdCard = (ImageView) inflate.findViewById(R.id.my_info_id_card_iv);
        mIvBankCard = (ImageView) inflate.findViewById(R.id.my_info_bank_card_iv);
        mIvCreditCard = (ImageView) inflate.findViewById(R.id.my_info_credit_card_iv);

        mTextBankName = (TextView) inflate.findViewById(R.id.my_info_bank_name_tv);

        mLLBankName = (LinearLayout) inflate.findViewById(R.id.my_info_bank_name_ll);
        mLLBankPhone = (LinearLayout) inflate.findViewById(R.id.my_info_bank_phone_ll);
        mLLCreditCard = (LinearLayout) inflate.findViewById(R.id.my_info_credit_card_ll);

        mTextPrompt = (TextView) inflate.findViewById(R.id.mine_data_prompt_tv);
        mBtnSubmit = (Button) inflate.findViewById(R.id.mine_data_btn_submit);

        mIvIdCard.setOnClickListener(this);
        mIvBankCard.setOnClickListener(this);
        mIvCreditCard.setOnClickListener(this);

        mLLBankName.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    private void initData() {
        if (TextUtils.equals("3", BaseBean.getProcessAudit())
                || TextUtils.equals("6", BaseBean.getProcessAudit())) {
            realName = BaseBean.getRealName();
            merName = BaseBean.getMerchantName();
            idCardNum = encryptManager.getDecryptDES(BaseBean.getCertCode());
            bankCardNum = encryptManager.getDecryptDES(BaseBean.getCardNo());
            cardBranchName = BaseBean.getOpenBank();
            mEditName.setText(realName);
            mEditMerchant.setText(merName);
            mEditIdCard.setText(idCardNum);
            mIvIdCard.setVisibility(View.GONE);
            mBankNumEdit.setText(bankCardNum);
            mIvBankCard.setVisibility(View.GONE);
            mTextBankName.setText(cardBranchName);
            clickState();
        }

    }


    private void clickState() {
        mEditName.setFocusable(false);
        mEditMerchant.setFocusable(false);
        mEditIdCard.setFocusable(false);
        mBankNumEdit.setFocusable(false);
        mLLBankName.setOnClickListener(null);
        mBtnSubmit.setOnClickListener(null);
        mLLBankPhone.setVisibility(View.GONE);
        mLLCreditCard.setVisibility(View.GONE);
        mIvIdCard.setOnClickListener(null);
        mIvBankCard.setOnClickListener(null);
        mIvCreditCard.setOnClickListener(null);
        mBtnSubmit.setVisibility(View.GONE);
        mTextPrompt.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_id_card_iv:
                bdContentType = CameraActivity.CONTENT_TYPE_ID_CARD_FRONT;
                bdRequestCode = REQUEST_CODE_ID_CARD;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    intoBdDistinguish();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.my_info_bank_card_iv:
                bdContentType = CameraActivity.CONTENT_TYPE_BANK_CARD;
                bdRequestCode = REQUEST_CODE_BANKCARD;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    intoBdDistinguish();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.my_info_credit_card_iv:
                bdContentType = CameraActivity.CONTENT_TYPE_BANK_CARD;
                bdRequestCode = REQUEST_CODE_CREDIT_CARD;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    intoBdDistinguish();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.my_info_bank_name_ll:
                //选择分行
                Intent intentBranch = new Intent(context, SelectHeadOfficeActivity.class);
                intentBranch.putExtra("type", 1);
                startActivityForResult(intentBranch, REQUEST_CODE_BRANCH_BANK);
                break;
            case R.id.mine_data_btn_submit:
                //提交数据
                getPerfectNet();
                break;
        }
    }

    //进入百度识别
    private void intoBdDistinguish() {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, bdContentType);
        startActivityForResult(intent, bdRequestCode);
    }

    private void getPerfectNet() {
        realName = mEditName.getText().toString();
        merName = mEditMerchant.getText().toString();
        idCardNum = mEditIdCard.getCustomEditText().trim();
        bankCardNum = mBankNumEdit.getCustomEditText().trim();
        bankMobile = mEditBankPhone.getCustomEditText().trim();
        creditCard = mCreditCardNumEdit.getCustomEditText().trim();//信用卡
        cardBranchName = mTextBankName.getText().toString().trim();

        if (TextUtils.isEmpty(realName)) {
            Toast.makeText(context, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(merName)) {
            Toast.makeText(context, "请输入商户名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idCardNum)) {
            Toast.makeText(context, "请输入有效身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(bankCardNum)) {
            Toast.makeText(context, "请输入有效银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(creditCard)) {
            Toast.makeText(context, "请输入有效信用卡卡号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(bankMobile)) {
            bankMobile = "";
        }

        if (!TextUtils.isEmpty(bankMobile)) {
            if (!StringUtil.isMobileNumber(bankMobile)) {
                Toast.makeText(context, "请输入有效银行卡预留手机号，或者不填", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(mTextBankName.getText().toString().trim())) {
            Toast.makeText(context, "请选择开户行", Toast.LENGTH_SHORT).show();
            return;
        }
        progressShow();
        new MinePresenter.PerfectBankData(this).PostPerfectBankData();
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        param.setIdCardSide(idCardSide);
        param.setDetectDirection(true);
        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    idCardNum = result.getIdNumber().toString();
                    realName = result.getName().toString();
                    mEditName.setText(null);
                    mEditName.setText(realName);
                    mEditIdCard.setText(idCardNum);
                }
            }

            @Override
            public void onError(OCRError error) {
                showDialog("show", "身份证信息识别失败,请手动输入", context);
            }
        });
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
            // other 'case' lines to check for other permissions this app might request
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_BRANCH_BANK:
                if (resultCode == RESULT_OK) {
                    bankId = data.getStringExtra("bankId");
                    bankCityCode = data.getStringExtra("cityId");
                    bankProvCode = data.getStringExtra("provId");
                    cardBranchBank = data.getStringExtra("InterBankNo");
                    cardBranchName = data.getStringExtra("PmsBankNm");
                    mTextBankName.setText(cardBranchName);
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case REQUEST_CODE_ID_CARD:
                if (resultCode == RESULT_OK && data != null) {
                    String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                    String filePath = FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath();
                    if (!TextUtils.isEmpty(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    }
                }
                break;
            case REQUEST_CODE_BANKCARD:
                if (resultCode == RESULT_OK) {
                    RecognizeService.recBankCard(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                            new RecognizeService.ServiceListener() {
                                @Override
                                public void onResult(String result) {
                                    if (TextUtils.equals("识别错误", result)) {
                                        showDialog("show", "银行卡信息识别失败,请手动输入", context);
                                    } else {
                                        String[] strings = result.split(",");
                                        mBankNumEdit.setText(strings[0]);
                                    }
                                }
                            });
                }
                break;
            case REQUEST_CODE_CREDIT_CARD:
                if (resultCode == RESULT_OK) {
                    RecognizeService.recBankCard(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                            new RecognizeService.ServiceListener() {
                                @Override
                                public void onResult(String result) {
                                    if (TextUtils.equals("识别错误", result)) {
                                        showDialog("show", "信用卡信息识别失败,请手动输入", context);
                                    } else {
                                        String[] strings = result.split(",");
                                        mCreditCardNumEdit.setText(strings[0]);
                                    }
                                }
                            });
                }
                break;
            case RC_SETTING_CAMERA:
                // Do something after user returned from app settings screen, like showing a Toast.
                ToastUtils.showShortToast("从设置界面返回");
                break;
            default:
                break;
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, context);
    }

    @Override
    public String getPerfectBankDataJsonString() {
        RequestParamsPerfectBank request = new RequestParamsPerfectBank();
        RequestUtils.initRequestBean(request, encryptManager, "3101");
        request.setBankCard(encryptManager.getEncryptDES(bankCardNum));
        request.setBankId(bankId);
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setCard_branch_bank(cardBranchBank);
        request.setBank_city_code(bankCityCode);
        request.setBank_prov_code(bankProvCode);
        request.setIdCard(encryptManager.getEncryptDES(idCardNum));
        try {
            request.setUserName(URLEncoder.encode(realName, "utf-8"));
            request.setCard_branch_name(URLEncoder.encode(cardBranchName, "utf-8"));
            request.setMerName(URLEncoder.encode(merName, "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setBankMobile(bankMobile);
        request.setCreditCard(encryptManager.getEncryptDES(creditCard));

        String[] signs = {"bankCard", "bankId", "bank_city_code", "bank_prov_code",
                "card_branch_bank", "creditCard", "idCard", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void perfectBankDataResponse() {
        progressCancel();
        clickState();
        if (BaseBean.getProcessAudit().equals("3")) {
            showDialog("perfectSuccess", null, context);
        }
    }
}
