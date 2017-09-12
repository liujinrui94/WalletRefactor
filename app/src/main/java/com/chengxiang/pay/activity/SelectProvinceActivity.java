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
import com.chengxiang.pay.bean.ResponseParamsProvinceInfo;
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
 * @time: 2017/7/20 15:57
 * @description: 选择银行所在省
 */

public class SelectProvinceActivity extends BaseActivity implements BankView.ProvinceView, AdapterView.OnItemClickListener {
    private ArrayList<String> itemList;//省列表
    private ArrayList<ResponseParamsProvinceInfo.ProvinceInfo> provList;
    private ListView provinceLv;
    private ArrayAdapter<String> mAdapter;

    private String bankName, bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_province);

        bankId = getIntent().getStringExtra("bankId");
        bankName = getIntent().getStringExtra("bankName");

        initView();
        progressShow();
        new BankPresenter.Province(this).PostProvince();
    }

    private void initView() {
        setTitle("选择银行所在省");
        provinceLv = (ListView) findViewById(R.id.province_lv);
        itemList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.item_select_text, itemList);
        provinceLv.setAdapter(mAdapter);
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getProvinceJsonString() {
        BaseRequestParams request = new BaseRequestParams();
        RequestUtils.initRequestBean(request, encryptManager, "1005");
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void provinceResponse(ArrayList<ResponseParamsProvinceInfo.ProvinceInfo> provList) {
        this.provList = provList;
        progressCancel();
        for (ResponseParamsProvinceInfo.ProvinceInfo provinceInfo : provList) {
            itemList.add(provinceInfo.getProvName());
        }
        mAdapter.notifyDataSetChanged();
        provinceLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(SelectProvinceActivity.this,
                SelectCityActivity.class);
        intent.putExtra("bankName", bankName);
        intent.putExtra("bankId", bankId);
        intent.putExtra("provId", provList.get(position).getProvId());
        startActivityForResult(intent, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
