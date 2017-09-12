package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.CashRegisterBean;
import com.chengxiang.pay.bean.MonthProfitUserBean;
import com.chengxiang.pay.bean.ProfitDetailBean;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/9 15:33
 * @description:
 */
public interface ProfitView {
    /**
     * 收益信息
     */
    interface ProfitInfoView extends BaseView {
        String getProfitInfo();

        void profitInfoResponse(ResponseParamsProfitInfo mResponseParamsProfitInfo);
    }

    /**
     * 提现风控(9002)
     */
    interface WithdrawCashControlView extends BaseView {
        String getWithdrawCashControlJsonString();

        void withdrawCashControlResponse(String fee, String minAmount, String maxAmount, String accountTime);
    }

    /**
     * 提现申请(9003)
     */
    interface WithdrawCashView extends BaseView {
        String getWithdrawCashJsonString();

        void withdrawCashResponse();
    }

    /**
     * 提现记录(9004)
     */
    interface WithdrawCashRecordView extends BaseView {

        String getWithdrawCashRecordJsonString();

        void withdrawCashRecordResponse(String pageTotal, ArrayList<CashRegisterBean> cashLists);
    }

    /**
     * 本月收益(9007)
     */
    interface MonthProfitView extends BaseView {

        String getMonthProfitJsonString();

        void monthProfitResponse(String pageTotal,ArrayList<MonthProfitUserBean> amtList);
    }

    /**
     * 收益列表明细(9008)
     */
    interface ProfitListDetailView extends BaseView {

        String getProfitListDetailJsonString();

        void profitListDetailResponse(ArrayList<ProfitDetailBean> amtList);
    }


}
