package com.chengxiang.pay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.BillListActivity;
import com.chengxiang.pay.activity.MessageActivity;
import com.chengxiang.pay.activity.ProfitTabActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.custom.MessageBarView;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/4 16:07
 * @description: 收益
 */

public class ProfitFragment extends BaseFragment implements View.OnClickListener, ProfitView.ProfitInfoView {
    private Context context;
    private View inflate;
    private ImageView messageNoReadShowIv;


    @BindView(R.id.profit_balance_tv)
    TextView balanceTv;
    @BindView(R.id.profit_withdrawal_cash_tv)
    TextView withdrawalCashTv;

    @BindView(R.id.profit_pending_income_tv)
    TextView pendingIncomeTv;

    @BindView(R.id.profit_expand_user_tv)
    TextView expandUserTv;

    @BindView(R.id.profit_mouth_profit_tv)
    TextView mouthProfitTv;

    @BindView(R.id.profit_upgrade_profit_tv)
    TextView upgradeProfitTv;

    private Unbinder unbinder;

    private EncryptManager encryptManager;

    private MessageBarView rightMbv;

    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        if (inflate == null) {
            View view = inflate = inflater.inflate(R.layout.fragment_profit, container, false);
            unbinder = ButterKnife.bind(this, view);
            initView();
            initData();
        }
        return inflate;
    }

    private void initData() {
        progressShow();
        new ProfitPresenter.ProfitInfo(this).PostProfitInfo();
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void initView() {
        TextView title = (TextView) inflate.findViewById(R.id.action_bar_title_tv);
        title.setText(getResources().getString(R.string.profit));
        rightMbv = (MessageBarView) inflate.findViewById(R.id.action_bar_right_mbv);
        rightMbv.setVisibility(View.VISIBLE);
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
        rightMbv.setOnClickListener(this);
        ImageView leftIv = (ImageView) inflate.findViewById(R.id.action_bar_left_iv);
        leftIv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_bar_order));
        leftIv.setOnClickListener(this);

    }

    public String getProfitInfo() {
        RequestParamsPhoneNumber requestParamsPhoneNumber = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(requestParamsPhoneNumber, encryptManager, "9001");
        requestParamsPhoneNumber.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] sign = {"phoneNum"};
        requestParamsPhoneNumber.setSign(SignUtil.signNature(encryptManager, requestParamsPhoneNumber, sign));
        String requestString = new Gson().toJson(requestParamsPhoneNumber);
        return StringUtil.getStringToUtf(requestString);

    }

    @Override
    public void profitInfoResponse(ResponseParamsProfitInfo responseParamsProfitInfo) {
        progressCancel();
        balanceTv.setText(encryptManager.getDecryptDES(responseParamsProfitInfo.getBalance()));
        withdrawalCashTv.setText(encryptManager.getDecryptDES(responseParamsProfitInfo.getAmt()));
        pendingIncomeTv.setText(encryptManager.getDecryptDES(responseParamsProfitInfo.getZaituAmt()));

        BaseBean.saveBalance(encryptManager.getDecryptDES(responseParamsProfitInfo.getBalance()));
        BaseBean.saveOkAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getAmt()));
        BaseBean.saveNoAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getZaituAmt()));
        BaseBean.saveTotalProfitAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getAllShareAmt()));
        BaseBean.saveDirectNum(responseParamsProfitInfo.getDirectNum());
        BaseBean.savePerNum1(responseParamsProfitInfo.getpNum1());
        BaseBean.savePerNum2(responseParamsProfitInfo.getpNum2());
        BaseBean.savePerNum3(responseParamsProfitInfo.getpNum3());
        BaseBean.savePerNum4(responseParamsProfitInfo.getpNum4());


        expandUserTv.setText("我的客户" + responseParamsProfitInfo.getDirectNum() + "人");
        mouthProfitTv.setText("本月收益" + responseParamsProfitInfo.getTotleAmt() + "元");
        String upAmount;
        if (encryptManager.getDecryptDES(responseParamsProfitInfo.getUpAmt()) == null) {
            upAmount = " ";
        } else {
            upAmount = encryptManager.getDecryptDES(responseParamsProfitInfo.getUpAmt());
        }
        upgradeProfitTv.setText("升级为" + responseParamsProfitInfo.getNextLevelName() + "，可增加收益" + upAmount);
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, context);
    }

    @Override
    public void onResume() {
        super.onResume();
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
    }

    @OnClick({R.id.profit_withdraw_deposit_cv, R.id.profit_develop_people_cv, R.id.profit_month_income_cv, R.id.profit_improve_earnings_cv})
    public void doClick(View view) {
        Intent mIntent = null;
        switch (view.getId()) {
            case R.id.profit_withdraw_deposit_cv:
                mIntent = new Intent(context, ProfitTabActivity.class);
                mIntent.putExtra("typeClass", 0);
                break;
            case R.id.profit_develop_people_cv:
                mIntent = new Intent(context, ProfitTabActivity.class);
                mIntent.putExtra("typeClass", 1);
                break;
            case R.id.profit_month_income_cv:
                mIntent = new Intent(context, ProfitTabActivity.class);
                mIntent.putExtra("typeClass", 2);
                break;
            case R.id.profit_improve_earnings_cv:
                mIntent = new Intent(context, ProfitTabActivity.class);
                mIntent.putExtra("typeClass", 3);
                break;

        }
        if (TextUtils.equals("3", BaseBean.getProcessAudit())) {
            startActivity(mIntent);
        } else {
            checkLevel(context);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_right_mbv:
                Intent mIntent = new Intent(context, MessageActivity.class);
                startActivity(mIntent);
                break;
            case R.id.action_bar_left_iv:
                Intent intentOrderList = new Intent(context, BillListActivity.class);
                startActivity(intentOrderList);
                break;
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }
    }


}
