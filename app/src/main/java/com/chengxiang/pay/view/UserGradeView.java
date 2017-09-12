package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.ExpandUserBean;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/21 17:28
 * @description: 用户等级相关
 */


public interface UserGradeView {

    /**
     * 用户升级条件
     */
    interface UpgradeConditionView extends BaseView {

        String getUpgradeConditionJsonString();

        void upgradeConditionResponse(ResponseParamsUpgradeCondition responseParamsUpgradeCondition);
    }

    /**
     * 用户付款升级
     */
    interface PaymentUpgradeView extends BaseView {

        String getPaymentUpgradeJsonString();

        void paymentUpgradeResponse(String url);
    }

    /**
     * 累计拓展人（9005）
     */
    interface TotalExpandView extends BaseView {

        String getTotalExpandJsonString();

        void totalExpandResponse(String totalPage,ArrayList<ExpandUserBean> expandUserBeanArrayList);
    }


}