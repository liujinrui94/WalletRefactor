package com.chengxiang.pay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/9 9:00
 * @description:
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardHolderView> implements View.OnClickListener {

    private List<BankInfoBean> bankList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public BankCardAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<BankInfoBean> list) {
        this.bankList = list;
        notifyDataSetChanged();
    }

    public void addList(List<BankInfoBean> list) {
        bankList.addAll(list);
        notifyDataSetChanged();
    }

    public void notifyBankDataChanged(ArrayList<BankInfoBean> list0) {
        bankList.clear();
        bankList.addAll(list0);
        notifyDataSetChanged();
    }

    public List<BankInfoBean> getList() {
        return bankList;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public BankCardHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bank_card_detail, parent, false);
        BankCardHolderView holder = new BankCardHolderView(view);
        holder.deleteLL.setOnClickListener(this);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BankCardHolderView holder, final int position) {
        holder.bankName.setText(bankList.get(position).getBankName());
        holder.bankNum.setText(StringUtil.encryptCardNum(bankList.get(position).getCarNo()));
        holder.itemView.setTag(position);
        holder.deleteLL.setTag(position);
        switch (bankList.get(position).getBankName()) {
            case "中国银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_boc);
                break;
            case "中国农业银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_abc);
                break;
            case "交通银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_bcm);
                break;
            case "中国建设银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_ccb);
                break;
            case "中国光大银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_ceb);
                break;
            case "兴业银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_cib);
                break;
            case "招商银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_cmb);
                break;
            case "中国民生银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_cmbc);
                break;
            case "中国工商银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_icbc);
                break;
            case "平安银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_pab);
                break;
            case "中国邮政储蓄银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_psbc);
                break;
            case "上海浦东发展银行":
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_spdb);
                break;
            default:
                holder.detailLL.setBackgroundResource(R.mipmap.ic_bank_boc);
        }


    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
}


class BankCardHolderView extends RecyclerView.ViewHolder {
    TextView bankName;//卡名称
    TextView bankNum;//卡号
    LinearLayout deleteLL;//删除
    LinearLayout detailLL;//银行卡信息

    BankCardHolderView(View itemView) {
        super(itemView);
        bankName = (TextView) itemView.findViewById(R.id.grid_product_name_tv);
        bankNum = (TextView) itemView.findViewById(R.id.bank_num_tv);
        deleteLL = (LinearLayout) itemView.findViewById(R.id.item_bank_card_delete_ll);
        detailLL = (LinearLayout) itemView.findViewById(R.id.item_bank_card_detail_ll);
    }
}