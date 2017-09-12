package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.ProfitDetailBean;
import com.chengxiang.pay.bean.RequestParamsUserDetail;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.base.BaseRecyclerAdapter;
import com.chengxiang.pay.framework.base.SmartViewHolder;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/14 11:14
 * @description: 收益详情
 */
public class ProfitDetailActivity extends BaseActivity implements ProfitView.ProfitListDetailView {

    @BindView(R.id.activity_profit_detail_rlv)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_detail);
        initView();
        new ProfitPresenter.ProfitListDetail(this).PostProfitListDetail();
    }

    private void initView() {
    }


    @Override
    public void showCordError(String msg) {
        checkCordError(msg);
    }

    @Override
    public String getProfitListDetailJsonString() {
        RequestParamsUserDetail request = new RequestParamsUserDetail();
        RequestUtils.initRequestBean(request, encryptManager, "9008");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setLowPhoneNum(encryptManager.getEncryptDES(getIntent().getStringExtra("lowPhoneNum")));
        String[] sign = {"phoneNum", "lowPhoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, sign));
        return new Gson().toJson(request);
    }

    @Override
    public void profitListDetailResponse(ArrayList<ProfitDetailBean> amtList) {
        initNetView(amtList);
    }

    private void initNetView(ArrayList<ProfitDetailBean> amtList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new BaseRecyclerAdapter<ProfitDetailBean>(amtList, R.layout.item_profit_detail_rlv) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, final ProfitDetailBean model, int position) {
                if (position % 3 == 0) {
                    holder.image(R.id.profit_detail_circle_iv, R.mipmap.ic_red_circle);
                } else if (position % 3 == 1) {
                    holder.image(R.id.profit_detail_circle_iv, R.mipmap.ic_yellow_circle);
                } else if (position % 3 == 2) {
                    holder.image(R.id.profit_detail_circle_iv, R.mipmap.ic_green_circle);
                }
                GlideUtils.getInstance().loadNetCircleDefaultImage(model.getImg(), (ImageView) holder.itemView.findViewById(R.id.head_iv));
                if (TextUtils.equals(BaseBean.getPhoneNum(), model.getPhoneNum())) {
                    holder.text(R.id.phone_num_tv, "我");
                } else if (position == 1) {
                    holder.text(R.id.phone_num_tv, model.getPhoneNum());
                } else {
                    holder.text(R.id.phone_num_tv, StringUtil.encryptPhoneNum(model.getPhoneNum()));
                }
                holder.text(R.id.trade_amt_tv, "交易金额:" + model.getAmt());
                holder.text(R.id.leve_str_tv, model.getLevel());
            }
        });


    }

}
