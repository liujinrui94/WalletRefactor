package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.bean.ResponseParamsBillList;
import com.chengxiang.pay.bean.ResponseParamsCodeReceive;
import com.chengxiang.pay.bean.ResponseParamsGuMaReceive;
import com.chengxiang.pay.bean.ResponseParamsHomePageInfo;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:25
 * @description: 收款相关（包括账单信息等）Model
 */


public interface ReceivePostModel {

    /**
     * 首页公共信息
     */
    class PostHomePage implements BaseModelUtils.HomePage {
        @Override
        public void postHomePage(String requestString, final CallBackUtils.HomePageCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("首页公共信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("首页公共信息返回成功__" + response);
                    ResponseParamsHomePageInfo info = new Gson().fromJson(response,
                            ResponseParamsHomePageInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"balance", "totalCollectAmt", "zaituAmt"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            BaseBean.saveUnReadNum(Integer.parseInt(info.getNewNum()));
                            ArrayList<PaymentMethodBean> paymentMethodBeanArrayList = info.getPayList();
                            callBack.homePageResponse(paymentMethodBeanArrayList);
                        } else {
                            ToastUtils.showLongToast("首页公共信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }


    /**
     * 账单列表
     */
    class PostBillList implements BaseModelUtils.BillList {
        @Override
        public void postBillList(String requestString, final CallBackUtils.BillListCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("账单列表信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("账单列表信息返回成功__" + response);
                    Logger.json(response);
                    ResponseParamsBillList info = new Gson().fromJson(response,
                            ResponseParamsBillList.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"totalAmt"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            String totalPage = info.getPageTotal();
                            String totalAmt = EncryptManager.getEncryptManager().getDecryptDES(info.getTotalAmt());
                            ArrayList<BillBean> payForList = info.getPayForList();
                            callBack.billListResponse(totalAmt, totalPage, payForList);
                        } else {
                            ToastUtils.showLongToast("账单列表信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 二维码收款(微信、支付宝)
     */
    class PostCodeReceive implements BaseModelUtils.CodeReceive {
        @Override
        public void postCodeReceive(String requestString, final CallBackUtils.CodeReceiveCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("二维码收款信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("二维码收款信息返回成功__" + response);
                    ResponseParamsCodeReceive info = new Gson().fromJson(response,
                            ResponseParamsCodeReceive.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"amt", "qrlink"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            String url = info.getQrlink();
                            BaseBean.saveMerchantName(info.getUserName());
                            callBack.codeReceiveResponse(url);
                        } else {
                            ToastUtils.showLongToast("二维码收款信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 固码
     */
    class PostGuMaReceive implements BaseModelUtils.GuMaReceive {
        @Override
        public void postGuMaReceive(String requestString, final CallBackUtils.GuMaReceiveCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("固码信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("固码信息返回成功__" + response);
                    ResponseParamsGuMaReceive info = new Gson().fromJson(response,
                            ResponseParamsGuMaReceive.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            String url = info.getFkUrl();
                            callBack.guMaReceiveResponse(url);
                        } else {
                            ToastUtils.showLongToast("固码信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

}




