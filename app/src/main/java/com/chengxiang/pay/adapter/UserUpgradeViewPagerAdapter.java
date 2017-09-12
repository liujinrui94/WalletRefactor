package com.chengxiang.pay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.UserUpgradeModeActivity;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;

import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/22 22:18
 * @description: 用户升级展示费率adapter
 */

public class UserUpgradeViewPagerAdapter extends PagerAdapter {
    private List<View> list;
    private Activity activity;
    private List<ResponseParamsUpgradeCondition.LevelInformation> levelList;//等级列表
    private int listSizes;


    public UserUpgradeViewPagerAdapter(List<View> list, Activity activity, List<ResponseParamsUpgradeCondition.LevelInformation> levelList) {
        this.list = list;
        this.activity = activity;
        this.levelList = levelList;
        listSizes = list.size();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //((ViewPager) container).removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        if (listSizes != 0) {
            View view = list.get(position % listSizes);

            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            container.addView(view);

            TextView isUpgradeTv = (TextView) list.get(position).findViewById(R.id.viewpager_is_upgrade_tv);
            isUpgradeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, UserUpgradeModeActivity.class);
                    intent.putExtra("levelId", levelList.get(position).getLevelId());
                    intent.putExtra("levelName", levelList.get(position).getLevelName());
                    intent.putExtra("orderAmt", levelList.get(position).getUnAmt());
                    activity.startActivityForResult(intent, 1);
                }
            });
            return view;
        }
        return null;
    }
}
