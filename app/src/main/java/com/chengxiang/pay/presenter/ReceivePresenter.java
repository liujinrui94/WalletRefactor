package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.ReceivePostModel;
import com.chengxiang.pay.view.ReceiveView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/29 9:47
 * @description: 收款相关（包括账单信息等）请求层
 */


public interface ReceivePresenter {

    /**
     * 首页公共信息
     */
    class HomePage {
        private ReceivePostModel.PostHomePage postHomePage;
        private ReceiveView.HomePageView homePageView;

        public HomePage(ReceiveView.HomePageView homePageView) {
            this.homePageView = homePageView;
            postHomePage = new ReceivePostModel.PostHomePage();
        }

        public void postHomePage() {
            postHomePage.postHomePage(homePageView.getHomePageJsonString(), new CallBackUtils.HomePageCallBack() {
                @Override
                public void homePageResponse(ArrayList<PaymentMethodBean> paymentMethodBeanArrayList) {
                    homePageView.homePageResponse(paymentMethodBeanArrayList);
                }

                @Override
                public void OnNetError() {
                    homePageView.showCordError("首页公共信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    homePageView.showCordError(msg);
                }
            });
        }
    }


    /**
     * 账单列表
     */
    class BillList {
        private ReceivePostModel.PostBillList postBillList;
        private ReceiveView.BillListView billListView;

        public BillList(ReceiveView.BillListView billListView) {
            this.billListView = billListView;
            postBillList = new ReceivePostModel.PostBillList();
        }

        public void PostBillList() {
            postBillList.postBillList(billListView.getBillListJsonString(), new CallBackUtils.BillListCallBack() {
                @Override
                public void billListResponse(String totalAmt, String pageTotal, ArrayList<BillBean> billLists) {
                    billListView.billListResponse(totalAmt, pageTotal, billLists);
                }

                @Override
                public void OnNetError() {
                    billListView.showCordError("获取账单列表网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    billListView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 二维码收款(微信、支付宝)
     */
    class CodeReceive {
        private ReceivePostModel.PostCodeReceive postCodeReceive;
        private ReceiveView.CodeReceiveView codeReceiveView;

        public CodeReceive(ReceiveView.CodeReceiveView codeReceiveView) {
            this.codeReceiveView = codeReceiveView;
            postCodeReceive = new ReceivePostModel.PostCodeReceive();
        }

        public void PostCodeReceive() {
            postCodeReceive.postCodeReceive(codeReceiveView.getCodeReceiveJsonString(), new CallBackUtils.CodeReceiveCallBack() {
                @Override
                public void codeReceiveResponse(String url) {
                    codeReceiveView.codeReceiveResponse(url);
                }

                @Override
                public void OnNetError() {
                    codeReceiveView.showCordError("二维码收款网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    codeReceiveView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 固码
     */
    class GuMaReceive {
        private ReceivePostModel.PostGuMaReceive postGuMaReceive;
        private ReceiveView.GuMaReceiveView guMaReceiveView;

        public GuMaReceive(ReceiveView.GuMaReceiveView guMaReceiveView) {
            this.guMaReceiveView = guMaReceiveView;
            postGuMaReceive = new ReceivePostModel.PostGuMaReceive();
        }

        public void PostGuMaReceive() {
            postGuMaReceive.postGuMaReceive(guMaReceiveView.getGuMaReceiveJsonString(), new CallBackUtils.GuMaReceiveCallBack() {
                @Override
                public void guMaReceiveResponse(String url) {
                    guMaReceiveView.guMaReceiveResponse(url);
                }

                @Override
                public void OnNetError() {
                    guMaReceiveView.showCordError("二维码收款网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    guMaReceiveView.showCordError(msg);
                }
            });
        }
    }

}

