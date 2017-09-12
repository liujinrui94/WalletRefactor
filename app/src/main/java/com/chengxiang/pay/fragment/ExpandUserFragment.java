package com.chengxiang.pay.fragment;

import android.content.Context;
import android.os.Bundle;
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
 * @time: 2017/8/11 8:49
 * @description: 我的客户
 */
public class ExpandUserFragment extends BaseFragment {
    private View inflate;
    private Unbinder unbinder;


    private List<String> title_list = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.fragment_expand_user_tl)
     TabLayout mTabLayout;
    @BindView(R.id.fragment_expand_user_vp)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context mContext = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_expand_user, container, false);
            unbinder = ButterKnife.bind(this, inflate);
            initView();
        }
        return inflate;
    }

    private void initView() {
        //title_list.add("拓展总数"+ BaseBean.getDirectNum());
        title_list.add("直接人数"+BaseBean.getPerNum1());
        title_list.add("间接人数"+(BaseBean.getDirectNum()-BaseBean.getPerNum1()));
        //title_list.add("隔代人数"+BaseBean.getPerNum3());
        //title_list.add("四级人数"+BaseBean.getPerNum4());

        //fragments.add(ExpandUserListFragment.newInstance("0"));
        fragments.add(ExpandUserListFragment.newInstance("1"));
        fragments.add(ExpandUserListFragment.newInstance("0"));
        //fragments.add(ExpandUserListFragment.newInstance("3"));
        //fragments.add(ExpandUserListFragment.newInstance("4"));
        MyFragmentPagerAdapter mFragmentAdapter = new MyFragmentPagerAdapter(getFragmentManager(), fragments, title_list);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}