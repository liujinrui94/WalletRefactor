package com.chengxiang.pay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.GuideViewPagerAdapter;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Common;
import com.chengxiang.pay.framework.custom.BezierRoundView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/4 15:34
 * @description: 应用首次进入引导页
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ArrayList<View> views;// 定义一个ArrayList来存放View
    private ViewPager viewPager;// 定义ViewPager对象
    private GuideViewPagerAdapter vpAdapter;// 定义ViewPager适配器
    private Button intoSplash;//进入splash按钮

    // 引导图片资源
    private static final int[] pics = {R.mipmap.guide_one,
            R.mipmap.guide_two, R.mipmap.guide_three};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstTime = sharedPreferences.getBoolean(Common.LOGIN, true);
        if (firstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Common.LOGIN, false);
            editor.apply();
        } else {
            intoSplashActivity();
        }
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<>();
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.guide_vp);
        // 实例化ViewPager适配器
        vpAdapter = new GuideViewPagerAdapter(views);
        intoSplash = (Button) findViewById(R.id.guide_btn);
        intoSplash.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                intoSplashActivity();
            }
        });
    }

    /**
     * 初始化viewpager需加载的数据
     */
    private void initData() {
        //定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导页图片
        for (int pic : pics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//防止图片不填充满屏幕
            iv.setImageResource(pic);//加载图片资源
            views.add(iv);
        }
        viewPager.setAdapter(vpAdapter);//设置数据
        viewPager.setOnPageChangeListener(this);//设置监听
        BezierRoundView bezRound = (BezierRoundView) findViewById(R.id.guide_bezier);
        bezRound.attachViewPage(viewPager);
    }

    private void intoSplashActivity() {
        Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 当前页面滑动时调用
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        if (position == pics.length - 1) {
            intoSplash.setVisibility(View.VISIBLE);
        } else {
            intoSplash.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
