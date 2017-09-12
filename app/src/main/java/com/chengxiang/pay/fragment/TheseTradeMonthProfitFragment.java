package com.chengxiang.pay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.MyFragmentPagerAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/12 7:55
 * @description: 全部本月收益
 */
public class TheseTradeMonthProfitFragment extends BaseFragment {
    private View inflate;
    private String shareType;

    private List<String> title_list = new ArrayList<>();
    private List<Fragment> fragment_list = new ArrayList<>();


    @BindView(R.id.fragment_month_profit_tl)
    TabLayout mTabLayout;
    @BindView(R.id.fragment_month_profit_vp)
    ViewPager mViewPager;

    public static Fragment newInstance(String shareType) {
        TheseTradeMonthProfitFragment newFragment = new TheseTradeMonthProfitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shareType", shareType);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareType = getArguments().getString("shareType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_these_trade_month_profit, container, false);
            Unbinder unbinder = ButterKnife.bind(this, inflate);
            initView();
        }
        return inflate;

    }

    private void initView() {
        //title_list.add("拓展总数" + BaseBean.getDirectNum());
        title_list.add("直接人数" + BaseBean.getPerNum1());
        title_list.add("间接人数" + (BaseBean.getDirectNum()-BaseBean.getPerNum1()));
        //title_list.add("隔代人数" + BaseBean.getpNum3());
        //title_list.add("四级人数" + BaseBean.getpNum4());
        //fragment_list.add(TradeMonthListFragment.newInstance(shareType, "0"));
        fragment_list.add(TradeMonthListFragment.newInstance(shareType, "1"));
        fragment_list.add(TradeMonthListFragment.newInstance(shareType, "0"));
        //fragment_list.add(TradeMonthListFragment.newInstance(shareType, "3"));
        //fragment_list.add(TradeMonthListFragment.newInstance(shareType, "4"));
        MyFragmentPagerAdapter mFragmentAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragment_list, title_list);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);


    }


}
