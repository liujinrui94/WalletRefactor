package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.fragment.UserInfoBankDataFragment;
import com.chengxiang.pay.fragment.UserInfoPhotoDataFragment;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.view.MineView;
import com.google.gson.Gson;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 14:16
 * @description: 用户信息展示
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener, MineView.UserInfoView {
    private TextView mBankDataTv;
    private TextView mPhotoDataTv;
    private UserInfoBankDataFragment bankDataFragment;
    private UserInfoPhotoDataFragment photoDataFragment;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        progressShow();
        new MinePresenter.UserInfo(this).PostUserInfo();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.user_info));

        mBankDataTv = (TextView) findViewById(R.id.user_info_bank_data_tv);
        mPhotoDataTv = (TextView) findViewById(R.id.user_info_photo_data_tv);
        mBankDataTv.setOnClickListener(this);
        mPhotoDataTv.setOnClickListener(this);

        fManager = getSupportFragmentManager();
        if (TextUtils.equals("6", BaseBean.getProcessAudit())) {
            mPhotoDataTv.performClick();
        } else {
            mBankDataTv.performClick(); //模拟一次点击，既进去后选择第一项
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()) {
            case R.id.user_info_bank_data_tv:
                clearSelected();
                mBankDataTv.setSelected(true);
                if (bankDataFragment == null) {
                    bankDataFragment = new UserInfoBankDataFragment();
                    fTransaction.add(R.id.mine_data_fl_container, bankDataFragment);
                } else {
                    fTransaction.show(bankDataFragment);
                }
                bankDataFragment.initEncryptManager(encryptManager);
                break;
            case R.id.user_info_photo_data_tv:
                clearSelected();
                mPhotoDataTv.setSelected(true);
                if (photoDataFragment == null) {
                    photoDataFragment = new UserInfoPhotoDataFragment();
                    fTransaction.add(R.id.mine_data_fl_container, photoDataFragment);
                } else {
                    fTransaction.show(photoDataFragment);
                }
                photoDataFragment.initEncryptManager(encryptManager);
                break;
        }
        fTransaction.commit();
    }

    //重置所有文本的选中状态
    private void clearSelected() {
        mBankDataTv.setSelected(false);
        mPhotoDataTv.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (bankDataFragment != null)
            fragmentTransaction.hide(bankDataFragment);
        if (photoDataFragment != null)
            fragmentTransaction.hide(photoDataFragment);
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
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }
}
