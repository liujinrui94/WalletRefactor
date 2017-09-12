package com.chengxiang.pay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.chengxiang.pay.R;

import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/10 15:45
 * @description: 收益四界面adapter
 */
public class ProfitTabFragmentAdapter implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private FragmentActivity fragmentActivity;
    private int fragmentContentId;
    private int currentTab;

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;

    public ProfitTabFragmentAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId,
                                    RadioGroup radioGroup, int currentOption) {
        this.fragments = fragments;
        this.radioGroup = radioGroup;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;
        this.currentTab = currentOption;
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(currentOption));
        ft.commit();
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < this.radioGroup.getChildCount(); i++) {
            if (this.radioGroup.getChildAt(i).getId() == checkedId) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onPause();
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    ft.add(fragmentContentId, fragment);
                }
                showTab(i);
                ft.commit();
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }
            }
        }

    }

    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;
    }

    private FragmentTransaction obtainFragmentTransaction() {
        return fragmentActivity.getSupportFragmentManager().beginTransaction();
    }

    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        // 设置切换动画
        if (index > currentTab) {
            ft.setCustomAnimations(R.anim.activity_into_from_right, R.anim.activity_out_to_left);
        } else {
            ft.setCustomAnimations(R.anim.activity_into_from_left, R.anim.activity_out_to_right);
        }
        return ft;
    }


    private Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    public static class OnRgsExtraCheckedChangedListener {

        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {


        }
    }

}
