package com.chengxiang.pay.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 9:58
 * @description: 轮播图adapter
 */


public class SlideShowAdapter extends PagerAdapter {
    //界面列表
    private ArrayList<View> views;

    public SlideShowAdapter(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int i = position % views.size();
        // 预防负值
        position = Math.abs(i);
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int i = position % views.size();
        // 预防负值
        position = Math.abs(i);
        container.removeView(views.get(position));
    }
}