package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseRequestParams;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsHeadOfficeInfo;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.BankPresenter;
import com.chengxiang.pay.view.BankView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/19 11:17
 * @description: 选择开户行总行
 */

public class SelectHeadOfficeActivity extends BaseActivity implements BankView.HeadOfficeView, AdapterView.OnItemClickListener {
    private ArrayList<String> itemList;//总行列表
    private ArrayList<ResponseParamsHeadOfficeInfo.BankInfo> bankList;
    private ListView headOfficeLv;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_head_office);
        initView();
        progressShow();
        new BankPresenter.HeadOffice(this).PostHeadOffice();
    }

    private void initView() {
        setTitle("选择银行");
        headOfficeLv = (ListView) findViewById(R.id.head_office_lv);
        itemList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.item_select_text, itemList);
        headOfficeLv.setAdapter(mAdapter);
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getHeadOfficeJsonString() {
        BaseRequestParams request = new BaseRequestParams();
        RequestUtils.initRequestBean(request, encryptManager, "1007");
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void headOfficeResponse(ArrayList<ResponseParamsHeadOfficeInfo.BankInfo> bankList) {
        this.bankList = bankList;
        progressCancel();
        for (ResponseParamsHeadOfficeInfo.BankInfo bankInfo : bankList) {
            itemList.add(bankInfo.getBankName());
        }
        mAdapter.notifyDataSetChanged();
        headOfficeLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(SelectHeadOfficeActivity.this,
                SelectProvinceActivity.class);
        intent.putExtra("bankId", bankList.get(position).getBankId());
        intent.putExtra("bankName", bankList.get(position).getBankName());
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
