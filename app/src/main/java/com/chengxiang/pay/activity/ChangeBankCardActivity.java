package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.BankCardAdapter;
import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsDeleteBankCard;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.chengxiang.pay.presenter.BankPresenter;
import com.chengxiang.pay.view.BankView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 13:36
 * @description: 更换银行卡
 */

public class ChangeBankCardActivity extends BaseActivity implements View.OnClickListener, BankView.BankCardView, BankView.DeleteBankCardView {
    private ArrayList<BankInfoBean> bankList;
    private BankInfoBean bankInfo;

    private BankCardAdapter adapter;
    private SwipeRefreshLayout mSwipe;
    private TextView mEmpty;
    public static final int ADD_BANK_CARD = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bank_card);
        initView();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.mine_change_bank_card));
        ImageView actionBarRightIv = (ImageView) findViewById(R.id.action_bar_right_iv);
        actionBarRightIv.setVisibility(View.VISIBLE);
        actionBarRightIv.setImageResource(R.mipmap.ic_action_bar_add);
        actionBarRightIv.setOnClickListener(this);

        adapter = new BankCardAdapter(this);
        bankList = new ArrayList<>();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.change_bank_rlv);
        //创建默认的线性LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BankCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.item_bank_card_delete_ll:
                        bankInfo = bankList.get(position);
                        showDeleteDialog();
                        break;
                }

            }
        });
        mEmpty = (TextView) findViewById(R.id.change_bank_empty_tv);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.change_bank_swipe);
        mSwipe.setColorSchemeColors(getResources().getColor(R.color.themeColorDark));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyBankCardInfo();
            }
        });
        getMyBankCardInfo();
    }

    private void getMyBankCardInfo() {
        progressShow();
        new BankPresenter.BankCard(this).PostBankCard();
    }

    /**
     * 删除银行卡dialog
     */
    private void showDeleteDialog() {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setDetail("确定要删除此银行卡么？");
        commonDialog.setDetailCenter(true);
        commonDialog.setOnNegateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BankPresenter.DeleteBankCard(ChangeBankCardActivity.this).PostDeleteBankCard();
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_right_iv:
                Intent intent = new Intent(ChangeBankCardActivity.this, AddBankCardActivity.class);
                startActivityForResult(intent, ADD_BANK_CARD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BANK_CARD && resultCode == RESULT_OK) {
            getMyBankCardInfo();
        }
    }

    @Override
    public void showCordError(String msg) {
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        progressCancel();
        checkCordError(msg);
    }


    @Override
    public String getBankCardJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "9100");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void bankCardResponse(ArrayList<BankInfoBean> bankList) {
        progressCancel();
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        this.bankList = bankList;
        mEmpty.setVisibility(View.GONE);
        if (bankList == null || bankList.size() == 0) {
            adapter.notifyBankDataChanged(bankList);
            mEmpty.setVisibility(View.VISIBLE);
            mEmpty.setText("尚未绑定支付信用卡！");
        } else {
            adapter.notifyBankDataChanged(bankList);
        }
    }

    @Override
    public String getDeleteBankCardJsonString() {
        RequestParamsDeleteBankCard request = new RequestParamsDeleteBankCard();
        RequestUtils.initRequestBean(request, encryptManager, "9103");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setCarNo(encryptManager.getEncryptDES(bankInfo.getCarNo()));
        String[] signs = {"phoneNum", "carNo"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void deleteBankCardResponse() {
        progressCancel();
        SnackBarUtils.showLong(mEmpty, "银行卡删除成功");
        getMyBankCardInfo();
    }
}
