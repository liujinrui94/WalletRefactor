package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.BaseResponseParams;
import com.chengxiang.pay.bean.ResponseParamsBranchInfo;
import com.chengxiang.pay.bean.ResponseParamsCityInfo;
import com.chengxiang.pay.bean.ResponseParamsHeadOfficeInfo;
import com.chengxiang.pay.bean.ResponseParamsProvinceInfo;
import com.chengxiang.pay.bean.ResponseParamsBankCardInfo;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:25
 * @description: 银行页面相关Model
 */


public interface BankPostModel {
    /**
     * 银行总行信息
     */
    class PostHeadOffice implements BaseModelUtils.HeadOffice {

        @Override
        public void postHeadOffice(String headOfficeJsonString, final CallBackUtils.HeadOfficeCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + headOfficeJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("银行总行信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("银行总行信息返回成功__" + response);
                    ResponseParamsHeadOfficeInfo info = new Gson().fromJson(response,
                            ResponseParamsHeadOfficeInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.headOfficeResponse(info.getBankList());//返回银行列表
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 银行对应省
     */
    class PostProvince implements BaseModelUtils.Province {
        @Override
        public void postProvince(String provinceJsonString, final CallBackUtils.ProvinceCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + provinceJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("银行对应省信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("银行对应省信息返回成功__" + response);
                    ResponseParamsProvinceInfo info = new Gson().fromJson(response,
                            ResponseParamsProvinceInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.provinceResponse(info.getProvList());//返回银行对应省列表
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 银行对应省对应市
     */
    class PostCity implements BaseModelUtils.City {
        @Override
        public void postCity(String cityJsonString, final CallBackUtils.CityCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + cityJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("银行对应市信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("银行对应市信息返回成功__" + response);
                    ResponseParamsCityInfo info = new Gson().fromJson(response,
                            ResponseParamsCityInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.cityResponse(info.getCityList());//返回银行对应市列表
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 支行信息
     */
    class PostBranch implements BaseModelUtils.Branch {

        @Override
        public void postBranch(String branchJsonString, final CallBackUtils.BranchCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + branchJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("支行信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("支行信息返回成功__" + response);
                    ResponseParamsBranchInfo info = new Gson().fromJson(response,
                            ResponseParamsBranchInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.branchResponse(info.getPmsBankList());//返回银行对应市列表
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 获取我的银行卡相关信息
     */
    class PostBankCard implements BaseModelUtils.BankCard {

        @Override
        public void postBankCard(String bankCardJsonString, final CallBackUtils.BankCardCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + bankCardJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("获取我的银行卡相关信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("获取我的银行卡相关信息返回成功__" + response);
                    ResponseParamsBankCardInfo info = new Gson().fromJson(response,
                            ResponseParamsBankCardInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.bankCardResponse(info.getCarList());
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 删除银行卡
     */
    class PostDeleteBankCard implements BaseModelUtils.DeleteBankCard {

        @Override
        public void postDeleteBankCard(String deleteBankCardJsonString, final CallBackUtils.DeleteBankCardCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + deleteBankCardJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("删除我的银行卡相关信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("删除我的银行卡相关信息返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.deleteBankCardResponse();
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
     * 添加银行卡
     */
    class PostAddBankCard implements BaseModelUtils.AddBankCard {

        @Override
        public void postAddBankCard(String addBankCardJsonString, final CallBackUtils.AddBankCardCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + addBankCardJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("添加银行卡相关信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("添加银行卡相关信息返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.addBankCardResponse();
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


}




