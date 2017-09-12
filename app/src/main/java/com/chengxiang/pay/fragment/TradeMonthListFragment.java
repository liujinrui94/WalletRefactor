package com.chengxiang.pay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.ProfitDetailActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.MonthProfitUserBean;
import com.chengxiang.pay.bean.RequestParamsMonthProfit;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseApplication;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.base.BaseRecyclerAdapter;
import com.chengxiang.pay.framework.base.SmartViewHolder;
import com.chengxiang.pay.framework.custom.refreshlayout.SmartRefreshLayout;
import com.chengxiang.pay.framework.custom.refreshlayout.api.RefreshLayout;
import com.chengxiang.pay.framework.custom.refreshlayout.listener.OnLoadmoreListener;
import com.chengxiang.pay.framework.custom.refreshlayout.listener.OnRefreshListener;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/12 8:42
 * @description: 本月收益下的单独fragment
 */
public class TradeMonthListFragment extends BaseFragment implements ProfitView.MonthProfitView {
    private Context mContext;
    private String shareType;//0:全部，1：交易分润，2升级分润
    private String directType;//0:全部，1:直接，2:间接,3:隔代，4:四级以后
    private View inflate;
    private int currentPage = 1;//当前页
    private int pageSize = 10;//每次加载数量
    private List<MonthProfitUserBean> monthProfitList = new ArrayList<>();
    private BaseRecyclerAdapter mBaseRecyclerAdapter = null;

    @BindView(R.id.fragment_expand_userList_rlv)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_expand_userList_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.no_data_tv)
    TextView no_data_tv;

    public static TradeMonthListFragment newInstance(String shareType, String directType) {
        TradeMonthListFragment newFragment = new TradeMonthListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shareType", shareType);
        bundle.putString("directType", directType);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mContext = getActivity();
        shareType = getArguments().getString("shareType");
        directType = getArguments().getString("directType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_expand_user_list, container, false);
            Unbinder unbinder = ButterKnife.bind(this, inflate);
            progressShow();
            initNetData();
        }
        //注意 一定要放在外面
        initEvent();
        return inflate;
    }

    private void initEvent() {
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                no_data_tv.setVisibility(View.GONE);
                currentPage = 1;
                mSmartRefreshLayout.setLoadmoreFinished(false);
                initNetData();
            }
        });
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPage++;
                initNetData();
            }
        });

    }

    private void initNetData() {
        new ProfitPresenter.MonthProfit(this).PostMonthProfit();
    }

    @Override
    public void showCordError(String msg) {
        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.autoRefresh();
        }
        progressCancel();
        checkCordError(msg, mContext);
    }


    private void initNetView(List<MonthProfitUserBean> amtList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<MonthProfitUserBean>(amtList, R.layout.item_trade_month) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, final MonthProfitUserBean model, int position) {
                GlideUtils.getInstance().loadNetImage(model.getLevelImg(), (ImageView) holder.itemView.findViewById(R.id.item_trade_head_iv));
                GlideUtils.getInstance().loadNetCircleDefaultImage(model.getImg(), (ImageView) holder.itemView.findViewById(R.id.item_trade_head_iv));
                if (TextUtils.equals("0", model.getIsZhijie())) {
                    holder.text(R.id.item_trade_phone_tv, StringUtil.encryptPhoneNum(model.getPhoneNum()));
                } else {
                    holder.text(R.id.item_trade_phone_tv, model.getPhoneNum());
                }
                holder.text(R.id.item_trade_type_tv, model.getShareMeno() + ":" + model.getShareAmt());
                holder.text(R.id.item_trade_time_tv, model.getDateTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mIntent = new Intent(getActivity(), ProfitDetailActivity.class);
                        mIntent.putExtra("lowPhoneNum", model.getPhoneNum());
                        startActivity(mIntent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);
    }


    @Override
    public String getMonthProfitJsonString() {
        RequestParamsMonthProfit request = new RequestParamsMonthProfit();
        RequestUtils.initRequestBean(request, BaseApplication.encryptManager, "9007");
        request.setPhoneNum(BaseApplication.encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setShareType(shareType);
        request.setDirectType(directType);
        request.setCurrentPage(currentPage + "");
        request.setPageSize(pageSize + "");
        String[] sign = {"phoneNum", "directType"};
        request.setSign(SignUtil.signNature(BaseApplication.encryptManager, request, sign));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void monthProfitResponse(String pageTotal, ArrayList<MonthProfitUserBean> amtList) {
        progressCancel();
        if (amtList.size() < pageSize) {
            mSmartRefreshLayout.setLoadmoreFinished(true);
        }
        if (mSmartRefreshLayout.isRefreshing()) {
            mBaseRecyclerAdapter.refresh(amtList);
            mSmartRefreshLayout.finishRefresh();
        } else if (mSmartRefreshLayout.isLoading()) {
            mBaseRecyclerAdapter.loadmore(amtList);
            mSmartRefreshLayout.finishLoadmore();
        } else {
            monthProfitList.addAll(amtList);
            initNetView(monthProfitList);
        }
        if (monthProfitList.size() == 0) {
            no_data_tv.setVisibility(View.VISIBLE);
        }
    }
}