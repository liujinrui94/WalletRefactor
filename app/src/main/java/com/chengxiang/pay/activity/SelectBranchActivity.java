package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.RequestParamsBranch;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsBranchInfo;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.BankPresenter;
import com.chengxiang.pay.view.BankView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 15:59
 * @description: 选择支行
 */

public class SelectBranchActivity extends BaseActivity implements BankView.BranchView, AdapterView.OnItemClickListener {
    private ArrayList<String> itemList;//支行列表
    private ArrayList<ResponseParamsBranchInfo.PmsBankInfo> pmsBankList;
    private ArrayList<ResponseParamsBranchInfo.PmsBankInfo> newPmsBankList;
    private ListView branchLv;
    private ArrayAdapter<String> mAdapter;
    private TextView emptyTv;
    private EditText searchEt;

    private String bankName, bankId, provId, cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_branch);
        bankId = getIntent().getStringExtra("bankId");
        bankName = getIntent().getStringExtra("bankName");
        provId = getIntent().getStringExtra("provId");
        cityId = getIntent().getStringExtra("cityId");
        initView();
        progressShow();
        new BankPresenter.Branch(this).PostBranch();
    }

    private void initView() {
        setTitle("选择支行");
        branchLv = (ListView) findViewById(R.id.branch_lv);
        emptyTv = (TextView) findViewById(R.id.branch_empty_tv);
        itemList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.item_select_text, itemList);
        branchLv.setAdapter(mAdapter);
        branchLv.setOnItemClickListener(this);
        searchEt = (EditText) findViewById(R.id.branch_search_et);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        String data = searchEt.getText().toString();
                        itemList.clear();
                        getDataSub(pmsBankList, data);// 匹配查询
                        mAdapter.notifyDataSetChanged();// 数据更新
                    }
                });
            }
        });
    }

    private void getDataSub(
            List<ResponseParamsBranchInfo.PmsBankInfo> mDataSubs, String data) {
        int length = mDataSubs.size();
        newPmsBankList = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            if (mDataSubs.get(i).getPmsBankNm().contains(data)) {
                itemList.add(mDataSubs.get(i).getPmsBankNm());
                newPmsBankList.add(mDataSubs.get(i));
            }
        }
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getBranchJsonString() {
        RequestParamsBranch request = new RequestParamsBranch();
        RequestUtils.initRequestBean(request, encryptManager, "1008");
        request.setBankId(bankId);
        request.setCityId(cityId);
        String[] signs = {"bankId", "cityId"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void branchResponse(ArrayList<ResponseParamsBranchInfo.PmsBankInfo> pmsBankList) {
        this.pmsBankList = pmsBankList;
        progressCancel();
        if (pmsBankList.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
            branchLv.setVisibility(View.GONE);
        }
        for (ResponseParamsBranchInfo.PmsBankInfo pmsBankInfo : pmsBankList) {
            itemList.add(pmsBankInfo.getPmsBankNm());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if (newPmsBankList == null) {
            intent.putExtra("PmsBankNm", pmsBankList.get(position).getPmsBankNm());
            intent.putExtra("InterBankNo", pmsBankList.get(position).getInterBankNo());
        } else {
            intent.putExtra("PmsBankNm", newPmsBankList.get(position).getPmsBankNm());
            intent.putExtra("InterBankNo", newPmsBankList.get(position).getInterBankNo());
        }
        intent.putExtra("bankId", bankId);
        intent.putExtra("provId", provId);
        intent.putExtra("cityId", cityId);
        intent.putExtra("bankName", bankName);
        setResult(RESULT_OK, intent);
        finish();
    }

}