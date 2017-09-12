package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.BaseResponseParams;
import com.chengxiang.pay.bean.CashRegisterBean;
import com.chengxiang.pay.bean.MonthProfitUserBean;
import com.chengxiang.pay.bean.ProfitDetailBean;
import com.chengxiang.pay.bean.ResponseParamsMonthProfit;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.bean.ResponseParamsProfitListDetail;
import com.chengxiang.pay.bean.ResponseParamsWithdrawCashControl;
import com.chengxiang.pay.bean.ResponseParamsWithdrawCashRecord;
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
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/9 15:33
 * @description: 收益相关
 */
public interface ProfitPostModel {

    /**
     * 收益信息详情 9001
     */
    class ProfitPostInfo implements BaseModelUtils.Profit {
        @Override
        public void profit(String requestString, final CallBackUtils.ProfitCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("收益详情信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("收益详情信息返回成功__" + response);
                    ResponseParamsProfitInfo info = new Gson().fromJson(response,
                            ResponseParamsProfitInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        String[] para = {"balance", "amt", "zaituAmt", "ordShareAmt", "upShareAmt", "upAmt", "addAmt", "allShareAmt"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            callBack.profitResponse(info);
                        } else {
                            ToastUtils.showLongToast("收益详情验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 提现风控(9002)
     */
    class PostWithdrawWithdrawCashControl implements BaseModelUtils.WithdrawCashControl {
        @Override
        public void postWithdrawCashControl(String requestString, final CallBackUtils.WithdrawCashControlCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("提现风控信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("提现风控信息返回成功__" + response);
                    ResponseParamsWithdrawCashControl info = new Gson().fromJson(response,
                            ResponseParamsWithdrawCashControl.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        String[] para = {"fee", "drawmaxamt", "drawminamt"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            String fee = info.getFee();//手续费
                            String minAmount = info.getDrawminamt();//最低金额
                            String maxAmount = info.getDrawmaxamt();//最高金额
                            String accountTime = info.getAccountTime();//预计到账时间
                            callBack.withdrawCashControlResponse(fee, minAmount, maxAmount, accountTime);
                        } else {
                            ToastUtils.showLongToast("提现风控数据验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 提现申请(9003)
     */
    class PostWithdrawCash implements BaseModelUtils.WithdrawCash {
        @Override
        public void postWithdrawCash(String requestString, final CallBackUtils.WithdrawCashCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("提现申请加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("提现申请返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.withdrawCashResponse();
                        } else {
                            ToastUtils.showLongToast("提现申请验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 提现记录(9004)
     */
    class PostWithdrawCashRecord implements BaseModelUtils.WithdrawCashRecord {
        @Override
        public void postWithdrawCashRecord(String requestString, final CallBackUtils.WithdrawCashRecordCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("提现记录信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("提现记录信息返回成功__" + response);
                    ResponseParamsWithdrawCashRecord info = new Gson().fromJson(response,
                            ResponseParamsWithdrawCashRecord.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            String pageTotal = info.getPageTotal();
                            ArrayList<CashRegisterBean> liqList = info.getLiqList();
                            callBack.withdrawCashRecordResponse(pageTotal, liqList);
                        } else {
                            ToastUtils.showLongToast("提现记录信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 本月收益(9007)
     */
    class PostMonthProfit implements BaseModelUtils.MonthProfit {
        @Override
        public void postMonthProfit(String requestString, final CallBackUtils.MonthProfitCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("本月收益信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("本月收益信息返回成功__" + response);
                    ResponseParamsMonthProfit info = new Gson().fromJson(response,
                            ResponseParamsMonthProfit.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            String pageTotal = info.getPageTotal();
                            ArrayList<MonthProfitUserBean> amtList = info.getAmtList();
                            callBack.monthProfitResponse(pageTotal, amtList);
                        } else {
                            ToastUtils.showLongToast("本月收益信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 收益列表明细(9008)
     */
    class PostProfitListDetail implements BaseModelUtils.ProfitListDetail {
        @Override
        public void postProfitListDetail(String requestString, final CallBackUtils.ProfitListDetailCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("收益列表明细加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("收益列表明细返回成功__" + response);
                    ResponseParamsProfitListDetail info = new Gson().fromJson(response,
                            ResponseParamsProfitListDetail.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            ArrayList<ProfitDetailBean> amtList = info.getAmtList();
                            callBack.profitListDetailResponse(amtList);
                        } else {
                            ToastUtils.showLongToast("收益列表明细验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }
}
