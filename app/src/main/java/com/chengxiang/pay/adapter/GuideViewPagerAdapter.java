package com.chengxiang.pay.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/4 15:46
 * @description: 首次进入引导页viewPager适配器
 */

public class GuideViewPagerAdapter extends PagerAdapter {
    //界面列表
    private ArrayList<View> views;

    public GuideViewPagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        } else
            return 0;
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    /**
     * 销毁position位置的界面
     */
    @SuppressWarnings("deprecation")
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    /**
     * 初始化position位置的界面
     */
    @SuppressWarnings("deprecation")
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }
}
