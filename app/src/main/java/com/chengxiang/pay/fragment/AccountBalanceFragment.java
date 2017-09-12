package com.chengxiang.pay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.WithdrawCashRecordActivity;
import com.chengxiang.pay.bean.BalanceModel;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsLogin;
import com.chengxiang.pay.bean.RequestParamsWithdrawCash;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.custom.AccountBalanceCakeView;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 8:37
 * @description: 账户余额
 */
public class AccountBalanceFragment extends BaseFragment implements ProfitView.WithdrawCashControlView, ProfitView.WithdrawCashView {

    private EncryptManager encryptManager;
    private View inflate;
    private Context mContext;

    private TextView withdrawCashControlTv;//提现风控
    private TextView arrivalDateTv;//到账时间

    private EditText withdrawCashAmountEt;
    private String minAmt;//最低限额
    private String maxAmt;//最高限额
    private String key;
    private Unbinder unbinder;

    public AccountBalanceFragment() {
    }


    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
        key = encryptManager.getPingKey();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_account_balance, container, false);
            unbinder = ButterKnife.bind(this, inflate);
            initView();
            progressShow();
            new ProfitPresenter.WithdrawCashControl(this).PostWithdrawCashControl();
        }
        return inflate;
    }

    private void initView() {
        AccountBalanceCakeView profitCakeView = (AccountBalanceCakeView) inflate.findViewById(R.id.account_balance_profit_cake_view);
        TextView totalProfitTv = (TextView) inflate.findViewById(R.id.account_balance_total_profit_tv);//累计收益
        withdrawCashControlTv = (TextView) inflate.findViewById(R.id.account_balance_withdraw_cash_control_tv);
        arrivalDateTv = (TextView) inflate.findViewById(R.id.account_balance_arrival_date_tv);
        withdrawCashAmountEt = (EditText) inflate.findViewById(R.id.account_balance_withdraw_cash_amount_et);
        totalProfitTv.setText("累计收益：" + BaseBean.getTotalProfitAmt());
        BalanceModel model = new BalanceModel();
        model.setOkAmt(BaseBean.getOkAmt());
        model.setNoAmt(BaseBean.getNoAmt());
        model.setBalance(BaseBean.getBalance());
        profitCakeView.setInitCakeData(176, 176, model);
    }

    @OnClick({R.id.account_balance_withdraw_cash_record_ll, R.id.account_balance_withdraw_cash_btn})
    public void doClick(View view) {

        switch (view.getId()) {
            case R.id.account_balance_withdraw_cash_record_ll:
                Intent mIntent = new Intent(mContext, WithdrawCashRecordActivity.class);
                startActivity(mIntent);
                break;
            case R.id.account_balance_withdraw_cash_btn:
                withdraw();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    private void withdraw() {
        String withdrawAmt = withdrawCashAmountEt.getText().toString();
        if (TextUtils.isEmpty(withdrawAmt)) {
            ToastUtils.showShortToast("请输入提现金额");
            return;
        }
        if (Double.parseDouble(withdrawAmt) == 0) {
            ToastUtils.showShortToast("输入的 提现金额不能为0");
            return;
        }
        if (Double.parseDouble(withdrawAmt) > Double.parseDouble(BaseBean.getOkAmt())) {
            ToastUtils.showShortToast("提现金额不能大于可提现金额");
            return;
        }
        double inputAmt = Double.parseDouble(withdrawCashAmountEt.getText()
                .toString().trim());
        if (inputAmt < Double.parseDouble(minAmt)) {
            ToastUtils.showShortToast("提现不能小于最低限额");
            return;
        }
        if (inputAmt > Double.parseDouble(maxAmt)) {
            ToastUtils.showShortToast("提现不能大于最高限额");
            return;
        }
        progressShow();
        new ProfitPresenter.WithdrawCash(this).PostWithdrawCash();
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, mContext);
    }

    @Override
    public String getWithdrawCashControlJsonString() {
        RequestParamsLogin request = new RequestParamsLogin();
        RequestUtils.initRequestBean(request, encryptManager, "9002");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void withdrawCashControlResponse(String fee, String minAmount, String maxAmount, String accountTime) {
        progressCancel();
        withdrawCashControlTv.setText("每笔结算金额最低"
                + encryptManager.getDecryptDES(minAmount, key) + "元，最高"
                + encryptManager.getDecryptDES(maxAmount, key) + "元，提现手续费"
                + encryptManager.getDecryptDES(fee, key) + "元。提现资金将汇入您的结算账户。");
        arrivalDateTv.setText("预计到账时间：" + accountTime);
        minAmt = encryptManager.getDecryptDES(minAmount, key);//最低限额
        maxAmt = encryptManager.getDecryptDES(maxAmount, key);//最高限额
    }

    @Override
    public String getWithdrawCashJsonString() {
        RequestParamsWithdrawCash request = new RequestParamsWithdrawCash();
        RequestUtils.initRequestBean(request, encryptManager, "9003");
        request.setCashAmt(encryptManager.getEncryptDES(withdrawCashAmountEt.getText().toString()));
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"cashAmt", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void withdrawCashResponse() {
        progressCancel();
        ToastUtils.showShortToast("提现申请成功");
    }
}
