package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.ExpandUserBean;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.UserGradePostModel;
import com.chengxiang.pay.view.UserGradeView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/22 18:25
 * @description: 用户等级页面请求
 */


public interface UserGradePresenter {
    /**
     * 用户升级条件
     */
    class UpgradeCondition {

        private UserGradeView.UpgradeConditionView upgradeConditionView;
        private UserGradePostModel.PostUpgradeCondition postUpgradeCondition;

        public UpgradeCondition(UserGradeView.UpgradeConditionView upgradeConditionView) {
            this.upgradeConditionView = upgradeConditionView;
            postUpgradeCondition = new UserGradePostModel.PostUpgradeCondition();
        }

        public void PostUpgradeCondition() {
            postUpgradeCondition.postUpgradeCondition(upgradeConditionView.getUpgradeConditionJsonString(), new CallBackUtils.UpgradeConditionCallBack() {
                @Override
                public void upgradeConditionResponse(ResponseParamsUpgradeCondition responseParamsUpgradeCondition) {
                    upgradeConditionView.upgradeConditionResponse(responseParamsUpgradeCondition);
                }

                @Override
                public void OnNetError() {
                    upgradeConditionView.showCordError("用户升级条件请求网络异常");
                }

                @Override
                public void CodeError(String msg) {
                    upgradeConditionView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 用户支付升级
     */
    class PaymentUpgrade {

        private UserGradeView.PaymentUpgradeView paymentUpgradeView;
        private UserGradePostModel.PostPaymentUpgrade postPaymentUpgrade;

        public PaymentUpgrade(UserGradeView.PaymentUpgradeView paymentUpgradeView) {
            this.paymentUpgradeView = paymentUpgradeView;
            postPaymentUpgrade = new UserGradePostModel.PostPaymentUpgrade();
        }

        public void PostPaymentUpgrade() {
            postPaymentUpgrade.postPaymentUpgrade(paymentUpgradeView.getPaymentUpgradeJsonString(), new CallBackUtils.PaymentUpgradeCallBack() {
                @Override
                public void paymentUpgradeResponse(String url) {
                    paymentUpgradeView.paymentUpgradeResponse(url);
                }

                @Override
                public void OnNetError() {
                    paymentUpgradeView.showCordError("用户升级条件请求网络异常");
                }

                @Override
                public void CodeError(String msg) {
                    paymentUpgradeView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 累计拓展人(9005)
     */
    class TotalExpand {

        private UserGradeView.TotalExpandView totalExpandView;
        private UserGradePostModel.PostTotalExpand postTotalExpand;

        public TotalExpand(UserGradeView.TotalExpandView totalExpandView) {
            this.totalExpandView = totalExpandView;
            postTotalExpand = new UserGradePostModel.PostTotalExpand();
        }

        public void PostTotalExpand() {
            postTotalExpand.postTotalExpand(totalExpandView.getTotalExpandJsonString(), new CallBackUtils.TotalExpandCallBack() {
                @Override
                public void totalExpandResponse(String totalPage, ArrayList<ExpandUserBean> expandUserBeanArrayList) {
                    totalExpandView.totalExpandResponse(totalPage, expandUserBeanArrayList);
                }

                @Override
                public void OnNetError() {
                    totalExpandView.showCordError("用户升级条件请求网络异常");
                }

                @Override
                public void CodeError(String msg) {
                    totalExpandView.showCordError(msg);
                }

            });
        }
    }

}
