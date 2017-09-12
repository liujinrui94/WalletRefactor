package com.chengxiang.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.UserUpgradeViewPagerAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.UserGradePresenter;
import com.chengxiang.pay.view.UserGradeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 9:16
 * @description: 用户升级
 */
public class UserUpgradeFragment extends BaseFragment implements UserGradeView.UpgradeConditionView {

    private View inflate;
    private Context mContext;
    private EncryptManager encryptManager;
    private TextView levelTv;//等级
    private TextView currentProfitTv;//当前费率和收益
    private ViewPager levelViewPager;//等级显示的viewpager
    private ImageView slideIv;//滑动指示图片

    private List<View> viewList;

    private List<ResponseParamsUpgradeCondition.LevelInformation> levelList;//等级列表

    private String levelName;//当前等级

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_user_upgrade, container, false);
            initView();
            progressShow();
            new UserGradePresenter.UpgradeCondition(this).PostUpgradeCondition();

        }
        return inflate;
    }

    private void initView() {
//        TextView title = (TextView) inflate.findViewById(R.id.action_bar_title_tv);
//        title.setText(getResources().getString(R.string.mine_up_grade));
        ImageView headIv = (ImageView)inflate. findViewById(R.id.user_upgrade_head_iv);
        levelTv = (TextView) inflate.findViewById(R.id.user_upgrade_level_tv);
        currentProfitTv = (TextView)inflate. findViewById(R.id.user_upgrade_current_profit_tv);
        levelViewPager = (ViewPager) inflate.findViewById(R.id.user_upgrade_level_viewpager);
        slideIv = (ImageView) inflate.findViewById(R.id.user_upgrade_slide_iv);
        viewList = new ArrayList<>();
        GlideUtils.getInstance().loadNetCircleDefaultImage(BaseBean.getHeadImgUrl(), headIv);
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg,mContext);
    }


    @Override
    public String getUpgradeConditionJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "9010");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void upgradeConditionResponse(ResponseParamsUpgradeCondition response) {
        progressCancel();
        String key = encryptManager.getPingKey();
        levelName = encryptManager.getDecryptDES(response.getLevelName(), key);
        String feeRate = encryptManager.getDecryptDES(response.getFeeRate(), key);
        levelTv.setText("级别：" + levelName);
        currentProfitTv.setText("当前本月收益：" + BaseBean.getMonthProfit() + " 费率：" + feeRate + "%");
        levelList = response.getLevelList();
        initViewPager();
    }

    private void initViewPager() {
        if (levelList.isEmpty()) {
            slideIv.setVisibility(View.GONE);
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_user_upgrade, null);
            TextView levelDescribeTv = (TextView) view.findViewById(R.id.viewpager_level_describe_tv);
            LinearLayout upgradeConditionLL = (LinearLayout) view.findViewById(R.id.viewpager_upgrade_condition_ll);
            TextView highestRankTv = (TextView) view.findViewById(R.id.viewpager_highest_rank_tv);
            TextView isUpgradeTv = (TextView) view.findViewById(R.id.viewpager_is_upgrade_tv);
            highestRankTv.setVisibility(View.VISIBLE);
            upgradeConditionLL.setVisibility(View.GONE);
            levelDescribeTv.setVisibility(View.GONE);
            isUpgradeTv.setVisibility(View.GONE);
            viewList.add(view);
        } else {
            slideIv.setVisibility(View.VISIBLE);
            for (int i = 0; i < levelList.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_user_upgrade, null);
                TextView levelDescribeTv = (TextView) view.findViewById(R.id.viewpager_level_describe_tv);
                TextView nextMonthProfitTv = (TextView) view.findViewById(R.id.viewpager_next_month_profit_tv);
                TextView upgradeCondition1Tv = (TextView) view.findViewById(R.id.viewpager_upgrade_condition1_tv);
                TextView upgradeCondition2Tv = (TextView) view.findViewById(R.id.viewpager_upgrade_condition2_tv);
                TextView isUpgradeTv = (TextView) view.findViewById(R.id.viewpager_is_upgrade_tv);
                if (i == 0) {
                    levelDescribeTv.setText(levelName + "升级到" + levelList.get(i).getLevelName());
                } else {
                    levelDescribeTv.setText(levelList.get(i - 1).getLevelName() + "升级到" + levelList.get(i).getLevelName());
                    isUpgradeTv.setVisibility(View.GONE);
                }
                upgradeCondition1Tv.setText("1、累计推荐有效用户数" + levelList.get(i).getUpUserNum() + "人；");
                upgradeCondition2Tv.setText("2、缴纳" + levelList.get(i).getUnAmt() + "元,自动升级为" + levelList.get(i).getLevelName());
                if (BaseBean.getIsDisplay().isEmpty()) {
                    nextMonthProfitTv.setVisibility(View.GONE);
                } else {
                    nextMonthProfitTv.setVisibility(View.VISIBLE);
                    nextMonthProfitTv.setText("预计下月收益：" + levelList.get(i).getProAmt());
                }
                viewList.add(view);
            }
        }
        levelViewPager.setAdapter(new UserUpgradeViewPagerAdapter(viewList, getActivity(), levelList));
    }
    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }
}
