package com.chengxiang.pay.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 8:54
 * @description:
 */
public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {


    private List<Fragment> list_fragment;                         //fragment列表

    private List<String> list_Title;                              //tab名的列表


    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }



    @Override
    public Fragment getItem(int i) {

        return list_fragment.get(i);
    }

    @Override
    public int getCount() {

        return list_fragment.size();

    }

    /**
     * 此方法是给tablayout中的tab赋值的，就是显示名称
     */

    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());

    }


}