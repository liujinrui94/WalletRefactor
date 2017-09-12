package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.RequestParamsCity;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsCityInfo;
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
 * @time: 2017/7/20 15:58
 * @description: 选择银行所在市
 */

public class SelectCityActivity extends BaseActivity implements BankView.CityView, AdapterView.OnItemClickListener {
    private ArrayList<String> itemList;//市列表
    private ArrayList<ResponseParamsCityInfo.CityInfo> cityList;
    private ListView cityLv;
    private ArrayAdapter<String> mAdapter;

    private String bankName, bankId, provId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        bankId = getIntent().getStringExtra("bankId");
        bankName = getIntent().getStringExtra("bankName");
        provId = getIntent().getStringExtra("provId");

        initView();
        progressShow();
        new BankPresenter.City(this).PostCity();
    }

    private void initView() {
        setTitle("选择银行所在市");
        cityLv = (ListView) findViewById(R.id.city_lv);
        itemList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.item_select_text, itemList);
        cityLv.setAdapter(mAdapter);
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getCityJsonString() {
        RequestParamsCity request = new RequestParamsCity();
        RequestUtils.initRequestBean(request, encryptManager, "1006");
        request.setProvId(provId);
        String[] signs = {"provId"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void cityResponse(ArrayList<ResponseParamsCityInfo.CityInfo> cityList) {
        this.cityList = cityList;
        progressCancel();
        for (ResponseParamsCityInfo.CityInfo cityInfo : cityList) {
            itemList.add(cityInfo.getCityName());
        }
        mAdapter.notifyDataSetChanged();
        cityLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(SelectCityActivity.this,
                SelectBranchActivity.class);
        intent.putExtra("bankName", bankName);
        intent.putExtra("bankId", bankId);
        intent.putExtra("provId", provId);
        intent.putExtra("cityId", cityList.get(position).getCityId());
        startActivityForResult(intent, 13);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}

