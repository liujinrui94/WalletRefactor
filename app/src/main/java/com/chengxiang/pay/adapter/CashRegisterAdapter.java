package com.chengxiang.pay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.CashRegisterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 9:58
 * @description: 提现记录adapter
 */


public class CashRegisterAdapter extends BaseAdapter {
    private List<CashRegisterBean> cashRegisterBeanList;
    private Context context;

    public CashRegisterAdapter(Context context) {
        this.context = context;
        cashRegisterBeanList = new ArrayList<>();
    }

    public void notifyBankDataChanged(ArrayList<CashRegisterBean> list0) {
        cashRegisterBeanList.clear();
        cashRegisterBeanList.addAll(list0);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cashRegisterBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return cashRegisterBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_withdraw_cash_bean, null);
            viewHolder = new ViewHolder();
            viewHolder.applyTimeTv = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_apply_time_tv);
            viewHolder.transferTimeTV = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_transfer_time_tv);
            viewHolder.transferAmtTV = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_transfer_amt_tv);
            viewHolder.withdrawAmtTv = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_amt_tv);
            viewHolder.withdrawFeeAmtTv = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_fee_amt_tv);
            viewHolder.withdrawStateTv = (TextView) convertView.findViewById(R.id.withdraw_cash_bean_state_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.withdrawFeeAmtTv.setText("手续费 " + cashRegisterBeanList.get(position).getFee() + "元");
        viewHolder.applyTimeTv.setText("申请时间：" + cashRegisterBeanList.get(position).getAppTime());
        viewHolder.transferTimeTV.setText("划款时间：" + cashRegisterBeanList.get(position).getLiqTime());
        viewHolder.transferAmtTV.setText("划款金额：" + cashRegisterBeanList.get(position).getLiqAmt());
        viewHolder.withdrawAmtTv.setText(cashRegisterBeanList.get(position).getAmt());
        if (TextUtils.equals("2", cashRegisterBeanList.get(position).getLiqState())) {
            viewHolder.withdrawStateTv.setText("提现成功");
        } else if (TextUtils.equals("3", cashRegisterBeanList.get(position).getLiqState())) {
            viewHolder.withdrawStateTv.setText("提现失败");
        } else {
            viewHolder.withdrawStateTv.setText("提现处理中");
        }
        return convertView;
    }

    private class ViewHolder {
        TextView applyTimeTv;//申请时间
        TextView transferTimeTV;//划款时间
        TextView transferAmtTV;//划款金额
        TextView withdrawAmtTv;//提现金额
        TextView withdrawFeeAmtTv;//提现手续费
        TextView withdrawStateTv;//提现状态
    }
}
