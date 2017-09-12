package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.framework.base.BaseActivity;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 21:12
 * @description: 账单详情
 */

public class BillDetailActivity extends BaseActivity {
    private ImageView typeImg;
    private TextView payTypeTv;
    private TextView tradeStateTv;
    private TextView tradeAmtTv;
    private TextView tradeTimeTv;
    private TextView accountTimeTv;
    private TextView feeTv;
    private TextView cashFlowStateTv;
    private TextView settlementAmountTv;
    private TextView cardNo;
    private TextView bankInfoTv;
    private TextView settlementStateTv;
    private TextView orderIdTv;
    private LinearLayout settlementLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        initView();
        initData();
    }

    private void initView() {
        setTitle("账单详情");
        typeImg = (ImageView) findViewById(R.id.bill_detail_type_iv); //收款类型图标
        payTypeTv = (TextView) findViewById(R.id.bill_detail_pay_type_tv);//收款方式
        tradeStateTv = (TextView) findViewById(R.id.bill_detail_trade_state_tv);//交易状态
        tradeAmtTv = (TextView) findViewById(R.id.bill_detail_trade_amt_tv); //交易金额
        tradeTimeTv = (TextView) findViewById(R.id.bill_detail_trade_time_tv);//交易时间
        accountTimeTv = (TextView) findViewById(R.id.bill_detail_account_time_tv);//到帐时间
        feeTv = (TextView) findViewById(R.id.bill_detail_fee_tv);//手续费
        cashFlowStateTv = (TextView) findViewById(R.id.bill_detail_cash_flow_state_tv);//出款状态
        settlementAmountTv = (TextView) findViewById(R.id.bill_detail_settlement_amt_tv);//结算金额
        cardNo = (TextView) findViewById(R.id.bill_detail_card_no);//卡号
        bankInfoTv = (TextView) findViewById(R.id.bill_detail_bank_info_tv);//结算银行
        settlementStateTv = (TextView) findViewById(R.id.bill_detail_settlement_state_tv);//结算状态
        orderIdTv = (TextView) findViewById(R.id.bill_detail_order_id_tv);//结算状态
        settlementLL = (LinearLayout) findViewById(R.id.bill_detail_settlement_ll);
    }

    private void initData() {
        BillBean billBean = (BillBean) getIntent().getSerializableExtra("BillBean");
        String typeReceive;
        if (TextUtils.equals("1", billBean.getPayType())) {
            typeReceive = "升级收款";
            settlementLL.setVisibility(View.GONE);
        } else {
            typeReceive = "收款";
            settlementLL.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("0", billBean.getType())) {
            payTypeTv.setText("微信" + typeReceive);
            typeImg.setImageResource(R.mipmap.ic_user_upgrade_wechat);
        } else if (TextUtils.equals("2", billBean.getType())) {
            payTypeTv.setText("固码" + typeReceive);
            typeImg.setImageResource(R.mipmap.ic_bill_gu_ma);
        } else if (TextUtils.equals("1", billBean.getType())) {
            payTypeTv.setText("支付宝" + typeReceive);
            typeImg.setImageResource(R.mipmap.ic_user_upgrade_ali_pay);
        } else if (TextUtils.equals("3", billBean.getType())) {
            payTypeTv.setText("快捷支付" + typeReceive);
            typeImg.setImageResource(R.mipmap.ic_bill_union);
        }
        //0 待支付  1 支付成功  2 支付失败
        if (TextUtils.equals("0", billBean.getState())) {
            tradeStateTv.setText("待支付");
        } else if (TextUtils.equals("1", billBean.getState())) {
            tradeStateTv.setText("交易成功");
        } else if (TextUtils.equals("2", billBean.getState())) {
            tradeStateTv.setText("交易失败");
        }
        tradeAmtTv.setText(billBean.getAmt()); //交易金额
        tradeTimeTv.setText(billBean.getPayTime());//交易时间
        accountTimeTv.setText(billBean.getAccountTime());//到帐时间
        orderIdTv.setText(billBean.getOrderId());
        feeTv.setText(billBean.getFee() + "元");//手续费
        if (TextUtils.equals("0", billBean.getLiqState()))//0未出，1已出
        {
            cashFlowStateTv.setText("未出款");//出款状态
        } else {
            cashFlowStateTv.setText("已出款");//出款状态

        }
        settlementAmountTv.setText(billBean.getSettleAmt() + "元");//结算金额
        cardNo.setText(billBean.getSettleCard());//卡号
        bankInfoTv.setText(billBean.getSettleBank());//结算银行
        if (TextUtils.equals("0", billBean.getSettleState()))//0未结，1已结
        {
            settlementStateTv.setText("未结算");//结算状态
        } else if (TextUtils.equals("1", billBean.getSettleState())) {
            settlementStateTv.setText("已结算");//结算状态
        }

    }

}
