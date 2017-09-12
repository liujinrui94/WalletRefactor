package com.chengxiang.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 15:03
 * @description: 银行卡列表
 */


public class UnionBankCardListAdapter extends BaseAdapter {
    private List<BankInfoBean> bankList;
    private Context context;

    public UnionBankCardListAdapter(Context context, ArrayList<BankInfoBean> list0) {
        this.context = context;
        bankList = list0;
    }

    @Override
    public int getCount() {
        return bankList.size();
    }

    @Override
    public Object getItem(int position) {
        return bankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_union_bank_card, null);
            viewHolder = new ViewHolder();
            viewHolder.bankName = (TextView) convertView.findViewById(R.id.union_bank_name_tv);
            viewHolder.bankNum = (TextView) convertView.findViewById(R.id.union_bank_number_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bankName.setText(bankList.get(position).getBankName());
        viewHolder.bankNum.setText(StringUtil.encryptCardNum(bankList.get(position).getCarNo()));
        return convertView;
    }

    private class ViewHolder {
        TextView bankName;//卡名称
        TextView bankNum;//卡号
    }
}
