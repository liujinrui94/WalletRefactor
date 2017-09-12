package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/26 17:41
 * @description: 收款相关（包括账单信息等）
 */


public interface ReceiveView {

    /**
     * 首页公共信息
     */
    interface HomePageView extends BaseView {

        String getHomePageJsonString();

        void homePageResponse(ArrayList<PaymentMethodBean> paymentMethodBeanArrayList);
    }


    /**
     * 账单列表
     */
    interface BillListView extends BaseView {

        String getBillListJsonString();

        void billListResponse(String totalAmt, String pageTotal, ArrayList<BillBean> billLists);

    }

    /**
     * 二维码收款（微信、支付宝）
     */
    interface CodeReceiveView extends BaseView {

        String getCodeReceiveJsonString();

        void codeReceiveResponse(String url);
    }
    /**
     * 固码
     */
    interface GuMaReceiveView extends BaseView {

        String getGuMaReceiveJsonString();

        void guMaReceiveResponse(String url);
    }

}