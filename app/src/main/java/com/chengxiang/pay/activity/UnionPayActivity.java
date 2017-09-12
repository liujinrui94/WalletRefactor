package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.UnionBankCardListAdapter;
import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.DisplayUtil;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.BankPresenter;
import com.chengxiang.pay.view.BankView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/9 20:17
 * @description: 银联支付
 */

public class UnionPayActivity extends BaseActivity implements View.OnClickListener, BankView.BankCardView {
    private String amount;//收款金额
    private ArrayList<BankInfoBean> bankList;
    private PopupWindow popupWindow;
    private Context context;
    private ListView bankCardLv;//银行卡列表
    private LinearLayout unionPayLL;
    private String unionUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union_pay);
        context = this;
        amount = getIntent().getStringExtra("amt");
        unionUrl = Constant.MOBILE_HOST + "/fvp-qp-business/quickPay.jsp?";
        initView();
    }

    private void initView() {
        setTitle("银联支付");
        unionPayLL = (LinearLayout) findViewById(R.id.union_pay_ll);
        unionPayLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.union_pay_ll:
                getMyBankCardInfo();
                break;
            case R.id.popup_union_add_bank_tv:
                toAddBankCard();
                break;
            case R.id.popup_union_cancel_tv:
                dismissPopupWindow();
                break;
        }
    }

    private void getMyBankCardInfo() {
        progressShow();
        new BankPresenter.BankCard(this).PostBankCard();
    }

    private void toAddBankCard() {
        Intent intent = new Intent(this, AddBankCardActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showCordError(String msg) {
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
        this.bankList = bankList;
        if (bankList == null || bankList.size() == 0) {
            toAddBankCard();
        } else {
            showPopupWindow();
            UnionBankCardListAdapter adapter = new UnionBankCardListAdapter(context, bankList);
            bankCardLv.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    getMyBankCardInfo();
                }
                break;
        }
    }

    /**
     * popupWindow
     */
    private void showPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_union_bank, null);
        initPopupWindowView(view);
        // 设置宽高
        popupWindow = new PopupWindow(view,
                (int) (DisplayUtil.getWindowWidth(context) * 0.8),
                (int) (DisplayUtil.getWindowHeight(context) * 0.6));
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        //设置弹出背景颜色变化
        InterfaceUtil.setBackgroundColor(UnionPayActivity.this, 0.6f);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.unionPopupWindow);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.showAtLocation(unionPayLL, Gravity.CENTER, 0, 0);
    }

    /**
     * 隐藏popupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            InterfaceUtil.setBackgroundColor(UnionPayActivity.this, 1.0f);
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    public void initPopupWindowView(View popupView) {
        TextView addBankTv = (TextView) popupView.findViewById(R.id.popup_union_add_bank_tv);
        bankCardLv = (ListView) popupView.findViewById(R.id.popup_union_bank_card_lv);
        TextView popupWindowCancelTv = (TextView) popupView.findViewById(R.id.popup_union_cancel_tv);
        addBankTv.setOnClickListener(this);
        popupWindowCancelTv.setOnClickListener(this);
        bankCardLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newUrl = "phoneNum=" + BaseBean.getPhoneNum()
                        + "&carNo=" + bankList.get(position).getCarNo()
                        + "&amt=" + amount
                        + "&orgId=" + Constant.orgId;
                Intent intent = new Intent(UnionPayActivity.this, UnionWebActivity.class);
                intent.putExtra("unionUrl", unionUrl + newUrl);
                startActivity(intent);
                dismissPopupWindow();
            }
        });
    }
}
