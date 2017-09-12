package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.base.BaseRecyclerAdapter;
import com.chengxiang.pay.framework.base.SmartViewHolder;

import java.util.Arrays;
import java.util.Collection;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/10 9:16
 * @description: 使用说明
 */
public class DescriptionActivity extends BaseActivity {

    private BaseRecyclerAdapter mBaseRecyclerAdapter;
    private Animation mAnimation;
    private SnapHelper snapHelper;

    @BindView(R.id.activity_description_of_product_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_description_back_top_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.activity_description_back_iv)
    ImageView activity_description_back_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_of_product);
        initView();
        initEvent();
    }

    private void initEvent() {

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
                mFloatingActionButton.setVisibility(View.VISIBLE);
                addAnimation();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                if (dy < 0) {
                    addAnimation();
                    if (firstVisibleItemPosition == 0) {
                        mFloatingActionButton.setVisibility(View.GONE);
                    }

                }
                if (dy > 0 && firstVisibleItemPosition > 0) {
                    if (!mRecyclerView.canScrollVertically(mRecyclerView.getScrollState())) {
                        cleanAnimation();
                    } else {
                        addAnimation();
                    }
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    private void addAnimation() {
        if (mAnimation == null) {
            mAnimation = new TranslateAnimation(0, 0, 0, -15);
        }
        mAnimation.setDuration(1000);     //动画持续时间
        mAnimation.setRepeatCount(10000);//动画次数
        mAnimation.setFillAfter(false);
        mAnimation.setStartOffset(0);
        activity_description_back_iv.setAnimation(mAnimation);
        activity_description_back_iv.setVisibility(View.VISIBLE);
        mAnimation.startNow();
    }

    private void cleanAnimation() {
        mAnimation.cancel();
        activity_description_back_iv.clearAnimation();
        activity_description_back_iv.setVisibility(View.GONE);
    }


    private void initView() {
        setTitle(getResources().getString(R.string.description_of_products));
        addAnimation();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Integer>(loadModels(), R.layout.description_rl_item) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Integer model, int position) {
                holder.image(R.id.imageView, model);

            }
        };
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);


    }

    private Collection<Integer> loadModels() {
        return Arrays.asList(
                R.mipmap.product_one,
                R.mipmap.product_two,
                R.mipmap.product_three,
                R.mipmap.product_four,
                R.mipmap.product_five,
                R.mipmap.product_six,
                R.mipmap.product_seven,
                R.mipmap.product_eight,
                R.mipmap.product_nine,
                R.mipmap.product_ten
        );
    }


}
