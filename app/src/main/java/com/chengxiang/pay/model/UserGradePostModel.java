package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.ExpandUserBean;
import com.chengxiang.pay.bean.ResponseParamsPaymentUpgrade;
import com.chengxiang.pay.bean.ResponseParamsTotalExpand;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;
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
 * @time: 2017/7/22 18:32
 * @description: 用户等级相关Model
 */


public interface UserGradePostModel {

    /**
     * 用户升级条件
     */
    class PostUpgradeCondition implements BaseModelUtils.UpgradeCondition {

        @Override
        public void postUpgradeCondition(String requestString, final CallBackUtils.UpgradeConditionCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("用户升级条件请求网络异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("用户升级条件信息__" + response);
                    ResponseParamsUpgradeCondition info = new Gson().fromJson(response,
                            ResponseParamsUpgradeCondition.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"feeRate"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            callBack.upgradeConditionResponse(info);
                        } else {
                            ToastUtils.showLongToast("用户升级条件信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 用户支付升级
     */
    class PostPaymentUpgrade implements BaseModelUtils.PaymentUpgrade {

        @Override
        public void postPaymentUpgrade(String requestString, final CallBackUtils.PaymentUpgradeCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("用户支付升级请求网络异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("用户支付升级信息__" + response);
                    ResponseParamsPaymentUpgrade info = new Gson().fromJson(response,
                            ResponseParamsPaymentUpgrade.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"notifyurl", "orderAmt", "orderId"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            callBack.paymentUpgradeResponse(info.getUrl());
                        } else {
                            ToastUtils.showLongToast("用户支付升级信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 累计拓展人（9005）
     */
    class PostTotalExpand implements BaseModelUtils.TotalExpand {

        @Override
        public void postTotalExpand(String requestString, final CallBackUtils.TotalExpandCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("累计拓展人请求网络异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("累计拓展人信息__" + response);
                    ResponseParamsTotalExpand info = new Gson().fromJson(response,
                            ResponseParamsTotalExpand.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            ArrayList<ExpandUserBean> expandUserBeanArrayList =info.getUserList();
                            String totalPage = info.getPageTotal();
                            callBack.totalExpandResponse(totalPage,expandUserBeanArrayList);
                        } else {
                            ToastUtils.showLongToast("累计拓展人信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }
}
