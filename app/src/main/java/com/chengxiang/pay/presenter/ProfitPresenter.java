package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.CashRegisterBean;
import com.chengxiang.pay.bean.MonthProfitUserBean;
import com.chengxiang.pay.bean.ProfitDetailBean;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.ProfitPostModel;
import com.chengxiang.pay.view.ProfitView;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/9 15:32
 * @description:
 */
public interface ProfitPresenter {

    /**
     * 收益信息详情（9001）
     */
    class ProfitInfo {
        private ProfitPostModel.ProfitPostInfo profitPostInfo;
        private ProfitView.ProfitInfoView profitInfoView;

        public ProfitInfo(ProfitView.ProfitInfoView profitInfoView) {
            this.profitInfoView = profitInfoView;
            profitPostInfo = new ProfitPostModel.ProfitPostInfo();
        }

        public void PostProfitInfo() {
            profitPostInfo.profit(profitInfoView.getProfitInfo(), new CallBackUtils.ProfitCallBack() {
                @Override
                public void profitResponse(ResponseParamsProfitInfo mResponseParamsProfitInfo) {
                    profitInfoView.profitInfoResponse(mResponseParamsProfitInfo);
                }


                @Override
                public void OnNetError() {
                    profitInfoView.showCordError("获取收益信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    profitInfoView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 提现风控(9002)
     */
    class WithdrawCashControl {
        private ProfitPostModel.PostWithdrawWithdrawCashControl postWithdrawCashControl;
        private ProfitView.WithdrawCashControlView withdrawCashControlView;

        public WithdrawCashControl(ProfitView.WithdrawCashControlView withdrawCashControlView) {
            this.withdrawCashControlView = withdrawCashControlView;
            postWithdrawCashControl = new ProfitPostModel.PostWithdrawWithdrawCashControl();
        }

        public void PostWithdrawCashControl() {
            postWithdrawCashControl.postWithdrawCashControl(withdrawCashControlView.getWithdrawCashControlJsonString(), new CallBackUtils.WithdrawCashControlCallBack() {
                @Override
                public void withdrawCashControlResponse(String fee, String minAmount, String maxAmount, String accountTime) {
                    withdrawCashControlView.withdrawCashControlResponse(fee, minAmount, maxAmount, accountTime);
                }

                @Override
                public void OnNetError() {
                    withdrawCashControlView.showCordError("提现风控网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    withdrawCashControlView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 提现申请(9003)
     */
    class WithdrawCash {
        private ProfitPostModel.PostWithdrawCash postWithdrawCash;
        private ProfitView.WithdrawCashView withdrawCashView;

        public WithdrawCash(ProfitView.WithdrawCashView withdrawCashView) {
            this.withdrawCashView = withdrawCashView;
            postWithdrawCash = new ProfitPostModel.PostWithdrawCash();
        }

        public void PostWithdrawCash() {
            postWithdrawCash.postWithdrawCash(withdrawCashView.getWithdrawCashJsonString(), new CallBackUtils.WithdrawCashCallBack() {
                @Override
                public void withdrawCashResponse() {
                    withdrawCashView.withdrawCashResponse();
                }

                @Override
                public void OnNetError() {
                    withdrawCashView.showCordError("提现申请网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    withdrawCashView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 提现记录(9004)
     */
    class WithdrawCashRecord {
        private ProfitPostModel.PostWithdrawCashRecord postWithdrawCashRecord;
        private ProfitView.WithdrawCashRecordView withdrawCashRecordView;

        public WithdrawCashRecord(ProfitView.WithdrawCashRecordView withdrawCashRecordView) {
            this.withdrawCashRecordView = withdrawCashRecordView;
            postWithdrawCashRecord = new ProfitPostModel.PostWithdrawCashRecord();
        }

        public void PostWithdrawCashRecord() {
            postWithdrawCashRecord.postWithdrawCashRecord(withdrawCashRecordView.getWithdrawCashRecordJsonString(), new CallBackUtils.WithdrawCashRecordCallBack() {
                @Override
                public void withdrawCashRecordResponse(String pageTotal, ArrayList<CashRegisterBean> cashLists) {
                    withdrawCashRecordView.withdrawCashRecordResponse(pageTotal, cashLists);
                }

                @Override
                public void OnNetError() {
                    withdrawCashRecordView.showCordError("提现记录网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    withdrawCashRecordView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 本月收益(9007)
     */
    class MonthProfit {
        private ProfitPostModel.PostMonthProfit postMonthProfit;
        private ProfitView.MonthProfitView monthProfitView;

        public MonthProfit(ProfitView.MonthProfitView monthProfitView) {
            this.monthProfitView = monthProfitView;
            postMonthProfit = new ProfitPostModel.PostMonthProfit();
        }

        public void PostMonthProfit() {
            postMonthProfit.postMonthProfit(monthProfitView.getMonthProfitJsonString(), new CallBackUtils.MonthProfitCallBack() {
                @Override
                public void monthProfitResponse(String pageTotal, ArrayList<MonthProfitUserBean> amtList) {
                    monthProfitView.monthProfitResponse(pageTotal, amtList);
                }

                @Override
                public void OnNetError() {
                    monthProfitView.showCordError("本月收益网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    monthProfitView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 收益列表明细(9008)
     */
    class ProfitListDetail {
        private ProfitPostModel.PostProfitListDetail postProfitListDetail;
        private ProfitView.ProfitListDetailView profitListDetailView;

        public ProfitListDetail(ProfitView.ProfitListDetailView profitListDetailView) {
            this.profitListDetailView = profitListDetailView;
            postProfitListDetail = new ProfitPostModel.PostProfitListDetail();
        }

        public void PostProfitListDetail() {
            postProfitListDetail.postProfitListDetail(profitListDetailView.getProfitListDetailJsonString(), new CallBackUtils.ProfitListDetailCallBack() {
                @Override
                public void profitListDetailResponse(ArrayList<ProfitDetailBean> amtList) {
                    profitListDetailView.profitListDetailResponse(amtList);
                }

                @Override
                public void OnNetError() {
                    profitListDetailView.showCordError("收益列表明细网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    profitListDetailView.showCordError(msg);
                }
            });
        }
    }

}
