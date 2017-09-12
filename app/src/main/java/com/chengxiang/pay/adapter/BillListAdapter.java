package com.chengxiang.pay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BillBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 9:58
 * @description: 订单列表
 */


public class BillListAdapter extends BaseAdapter {
    private List<BillBean> billBeanList;
    private Context context;

    public BillListAdapter(Context context) {
        this.context = context;
        billBeanList = new ArrayList<>();
    }

    public void notifyBankDataChanged(ArrayList<BillBean> list0) {
        billBeanList.clear();
        billBeanList.addAll(list0);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return billBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return billBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_bean, null);
            viewHolder = new ViewHolder();
            viewHolder.billTypeIv = (ImageView) convertView.findViewById(R.id.bill_bean_type_iv);
            viewHolder.billTypeWayTv = (TextView) convertView.findViewById(R.id.bill_bean_type_way_tv);
            viewHolder.billTradeTimeTv = (TextView) convertView.findViewById(R.id.bill_bean_trade_time_tv);
            viewHolder.billAccountTimeTv = (TextView) convertView.findViewById(R.id.bill_bean_account_time_tv);
            viewHolder.billTradeAmountTv = (TextView) convertView.findViewById(R.id.bill_bean_trade_amt_tv);
            viewHolder.billTradeStateTv = (TextView) convertView.findViewById(R.id.bill_bean_trade_state_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String typeReceive;
        if (TextUtils.equals("1", billBeanList.get(position).getPayType())) {
            typeReceive = "升级收款";
        } else {
            typeReceive = "收款";
        }
        if (TextUtils.equals("0", billBeanList.get(position).getType())) {
            viewHolder.billTypeWayTv.setText("微信" + typeReceive);
            viewHolder.billTypeIv.setImageResource(R.mipmap.ic_user_upgrade_wechat);
        } else if (TextUtils.equals("2", billBeanList.get(position).getType())) {
            viewHolder.billTypeWayTv.setText("固码"+ typeReceive);
            viewHolder.billTypeIv.setImageResource(R.mipmap.ic_bill_gu_ma);
        } else if (TextUtils.equals("1", billBeanList.get(position).getType())) {
            viewHolder.billTypeWayTv.setText("支付宝" + typeReceive);
            viewHolder.billTypeIv.setImageResource(R.mipmap.ic_user_upgrade_ali_pay);
        } else if (TextUtils.equals("3", billBeanList.get(position).getType())) {
            viewHolder.billTypeWayTv.setText("快捷支付" + typeReceive);
            viewHolder.billTypeIv.setImageResource(R.mipmap.ic_bill_union);
        }
        viewHolder.billAccountTimeTv.setText("预计到账时间:" + billBeanList.get(position).getAccountTime());
        viewHolder.billTradeTimeTv.setText("交易时间:" + billBeanList.get(position).getPayTime());
        viewHolder.billTradeAmountTv.setText(billBeanList.get(position).getAmt());
        //0 未完成  1 成功  2  失败   99 失效',
        if (TextUtils.equals("0", billBeanList.get(position).getState())) {
            viewHolder.billTradeStateTv.setText("待支付");
        } else if (TextUtils.equals("1", billBeanList.get(position).getState())) {
            viewHolder.billTradeStateTv.setText("交易成功");
        } else if (TextUtils.equals("2", billBeanList.get(position).getState())) {
            viewHolder.billTradeStateTv.setText("交易失败");
        } else if (TextUtils.equals("99", billBeanList.get(position).getState())) {
            viewHolder.billTradeStateTv.setText("交易失效");
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView billTypeIv;//订单类型（微信、支付宝、固码、快捷）
        TextView billTypeWayTv;//订单支付类型（收款、升级）
        TextView billTradeTimeTv;//订单交易时间
        TextView billAccountTimeTv;//预计到账时间
        TextView billTradeAmountTv;//订单交易金额
        TextView billTradeStateTv;//订单交易状态
    }
}
