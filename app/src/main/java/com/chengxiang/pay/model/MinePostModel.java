package com.chengxiang.pay.model;

import android.text.TextUtils;

import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.BaseResponseParams;
import com.chengxiang.pay.bean.ResponseParamsGetUserInfo;
import com.chengxiang.pay.bean.ResponseParamsMyInfo;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/5 15:09
 * @description: 我的页面相关Model
 */


public interface MinePostModel {
    /**
     * 我的基本信息
     */
    class PostMineInfo implements BaseModelUtils.MineInfo {

        @Override
        public void postMineInfo(String mineInfoJsonString, final CallBackUtils.MineInfoCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + mineInfoJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("我的基本信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("我的基本信息返回成功__" + response);
                    ResponseParamsMyInfo info = new Gson().fromJson(response,
                            ResponseParamsMyInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"ID", "level", "tjUserPhone", "directNum", "processAduit"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            BaseBean.saveLevelStr(info.getLevelStr());
                            BaseBean.saveProcessAudit(info.getProcessAduit());
                            BaseBean.saveLevel(info.getLevel());
                            BaseBean.savePhoneNum(info.getId());
                            if (TextUtils.isEmpty(info.getWxFeeRate())) {
                                BaseBean.saveCurrentFee("0.5");
                            } else {
                                BaseBean.saveCurrentFee(info.getWxFeeRate());
                            }
                            BaseBean.saveTjUserPhone(info.getTjUserPhone());
                            BaseBean.saveWechatFee(info.getWxFeeRate());
                            BaseBean.saveAliFee(info.getAliFeeRate());
                            BaseBean.saveUnionFee(info.getUnFeeRate());
                            BaseBean.saveSolidCodeFee(info.getFixFeeRate());
                            BaseBean.saveUnReadNum(Integer.parseInt(info.getNewsNum()));
                            callBack.mineInfoResponse();
                        } else {
                            ToastUtils.showLongToast("我的基本信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 用户信息查询
     */
    class PostUserInfo implements BaseModelUtils.UserInfo {

        @Override
        public void postUserInfo(String userInfoJsonString, final CallBackUtils.UserInfoCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + userInfoJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("用户信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("用户信息返回成功__" + response);
                    ResponseParamsGetUserInfo info = new Gson().fromJson(response,
                            ResponseParamsGetUserInfo.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"cardNo", "certCode"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            BaseBean.saveRealName(info.getUserName());
                            BaseBean.saveMerchantName(info.getMerName());
                            BaseBean.saveCertCode(info.getCertCode());
                            BaseBean.saveCardNo(info.getCardNo());
                            BaseBean.saveOpenBank(info.getOpenBank());
                            BaseBean.savePositive(info.getPositive());
                            BaseBean.saveOpposite(info.getOpposite());
                            BaseBean.saveMeet(info.getMeet());
                            callBack.userInfoResponse();
                        } else {
                            ToastUtils.showLongToast("我的基本信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 上传图片
     */
    class PostUploadFile implements BaseModelUtils.UploadFile {

        @Override
        public void postUploadFile(String uploadFileJsonString, File file, final CallBackUtils.UploadFileCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_UPLOAD + uploadFileJsonString).addFile("picture_0", file.getName(), file).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("图片上传加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("图片上传返回成功__" + response);
                    try {
                        JSONObject info = new JSONObject(response);
                        String fileName = info.getString("fileName");
                        callBack.uploadFileResponse(fileName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Logger.e("图片上传返回解析异常__" + e.toString());
                        callBack.CodeError("图片上传返回解析异常");
                    }
                }
            });
        }
    }

    /**
     * 完善用户照片信息
     */
    class PerfectPhotoData implements BaseModelUtils.PerfectPhotoData {

        @Override
        public void postPerfectPhotoData(String perfectPhotoDataJsonString, final CallBackUtils.PerfectPhotoDataCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + perfectPhotoDataJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("完善用户照片信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("完善用户照片信息返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            switch (BaseBean.getProcessAudit()) {
                                case "0":
                                case "5":
                                    BaseBean.saveProcessAudit("7");
                                    ToastUtils.showLongToast("照片信息完善成功，请完善银行卡基本信息！");
                                    break;
                                case "6":
                                    BaseBean.saveProcessAudit("3");
                                    break;
                            }
                            callBack.perfectPhotoDataResponse();
                        } else {
                            ToastUtils.showLongToast("完善用户照片信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 完善用户银行卡等基本信息
     */
    class PerfectBankData implements BaseModelUtils.PerfectBankData {

        @Override
        public void postPerfectBankData(String perfectBankDataJsonString, final CallBackUtils.PerfectBankDataCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + perfectBankDataJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("完善用户银行卡等基本信息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("完善用户银行卡信息返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            switch (BaseBean.getProcessAudit()) {
                                case "0":
                                case "5":
                                    BaseBean.saveProcessAudit("6");
                                    ToastUtils.showLongToast("银行卡基本信息完善成功，请完善照片信息！");
                                    break;
                                case "7":
                                    BaseBean.saveProcessAudit("3");
                                    break;
                            }
                            callBack.perfectBankDataResponse();
                        } else {
                            ToastUtils.showLongToast("完善用户银行卡信息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

}




