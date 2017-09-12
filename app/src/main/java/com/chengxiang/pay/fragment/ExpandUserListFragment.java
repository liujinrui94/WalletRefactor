package com.chengxiang.pay.fragment;

import android.content.Context;
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
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.ExpandUserBean;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.RequestParamsTotalExpand;
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
import com.chengxiang.pay.presenter.UserGradePresenter;
import com.chengxiang.pay.view.UserGradeView;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 15:04
 * @description: 我的客户下的单独fragment
 */
public class ExpandUserListFragment extends BaseFragment implements UserGradeView.TotalExpandView {
    private Context mContext;
    private String type;//请求类型：全部、直接、间接、隔代、四级以后
    private View inflate;
    private int currentPage = 1;//当前页
    private int pageSize = 10;//每次加载数量
    private ArrayList<ExpandUserBean> mExpandUserBeans = new ArrayList<>();

    private BaseRecyclerAdapter mBaseRecyclerAdapter;

    @BindView(R.id.fragment_expand_userList_rlv)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_expand_userList_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.no_data_tv)
    TextView no_data_tv;

    public static ExpandUserListFragment newInstance(String type) {
        ExpandUserListFragment newFragment = new ExpandUserListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mContext = getActivity();
        type = getArguments().getString("type");
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

        new UserGradePresenter.TotalExpand(this).PostTotalExpand();
    }

    @Override
    public void showCordError(String msg) {
        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.autoRefresh();
        }
        progressCancel();
        checkCordError(msg, mContext);

    }

    @Override
    public String getTotalExpandJsonString() {
        RequestParamsTotalExpand request = new RequestParamsTotalExpand();
        RequestUtils.initRequestBean(request, BaseApplication.encryptManager, "9005");
        request.setPhoneNum(BaseApplication.encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setPageSize(pageSize + "");
        request.setCurrentPage(currentPage + "");
        request.setType(type);
        String[] sign = {"phoneNum", "type"};
        request.setSign(SignUtil.signNature(BaseApplication.encryptManager, request, sign));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void totalExpandResponse(String totalPage, ArrayList<ExpandUserBean> expandUserBeanArrayList) {
        progressCancel();
        if (mSmartRefreshLayout.isRefreshing()) {
            mBaseRecyclerAdapter.refresh(expandUserBeanArrayList);
            mSmartRefreshLayout.finishRefresh();

        } else if (mSmartRefreshLayout.isLoading()) {
            mBaseRecyclerAdapter.loadmore(expandUserBeanArrayList);
            mSmartRefreshLayout.finishLoadmore();
        } else {
            mExpandUserBeans.addAll(expandUserBeanArrayList);
            initNetView(mExpandUserBeans);
        }
        if (expandUserBeanArrayList.size() < pageSize) {
            mSmartRefreshLayout.setLoadmoreFinished(true);
        }
        if (mExpandUserBeans.size() == 0) {
            no_data_tv.setVisibility(View.VISIBLE);
        }
    }

    private void initNetView(ArrayList<ExpandUserBean> expandUserBeanArrayList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<ExpandUserBean>(expandUserBeanArrayList, R.layout.item_expand_user) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, ExpandUserBean model, final int position) {
                GlideUtils.getInstance().loadNetImage(model.getLevelImgUrl(), (ImageView) holder.itemView.findViewById(R.id.expand_user_level_iv));
                GlideUtils.getInstance().loadNetCircleDefaultImage(model.getImgUrl(), (ImageView) holder.itemView.findViewById(R.id.expand_user_head_iv));
                holder.text(R.id.expand_user_register_time_tv, model.getRegTime());
                holder.text(R.id.expand_user_level_tv, "直接拓展用户" + model.getDirectNum() + "人");
                if (TextUtils.equals("0", model.getRegState())) {
                    holder.image(R.id.expand_user_state_iv, R.mipmap.ic_level_in_review);
                } else if (TextUtils.equals("3", model.getRegState())) {
                    holder.itemView.findViewById(R.id.expand_user_state_iv).setVisibility(View.INVISIBLE);
                } else if (TextUtils.equals("5", model.getRegState())) {
                    holder.image(R.id.expand_user_state_iv, R.mipmap.ic_level_supplement);
                } else {
                    holder.image(R.id.expand_user_state_iv, R.mipmap.ic_level_in_review);
                }
                if (TextUtils.equals("0", model.getIsZhijie())) { //不是直接
                    holder.text(R.id.expand_user_phone_tv, StringUtil.encryptPhoneNum(model.getPhoneNum()));
                } else {
                    holder.text(R.id.expand_user_phone_tv, StringUtil.encryptPhoneNum(model.getPhoneNum()));
                }
            }
        };
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);
    }


}
