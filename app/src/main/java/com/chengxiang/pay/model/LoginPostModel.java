package com.chengxiang.pay.model;

import android.text.TextUtils;

import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.BaseResponseParams;
import com.chengxiang.pay.bean.ResponseParamsLogin;
import com.chengxiang.pay.bean.ResponseParamsVerifiedCode;
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
 * @time: 2017/7/1 8:42
 * @description: 登录、注册相关Model
 */


public interface LoginPostModel {
    /**
     * 用户登录
     */
    class PostUserLogin implements BaseModelUtils.UserLogin {
        @Override
        public void postUserLogin(String loginJsonString, final CallBackUtils.UserLoginCallBack callBack) {

            OkHttpUtils.post().url(Constant.MOBILE_FRONT + loginJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("登录加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("登录成功__" + response);
                    ResponseParamsLogin info = new Gson().fromJson(
                            response, ResponseParamsLogin.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        String[] para = {"level", "processAduit", "id", "isBind"};
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, para)) {
                            callBack.startActivity(info);
                        } else {
                            ToastUtils.showLongToast("登录验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }


    /**
     * 获取短信验证码
     */
    class PostUserGetVerifiedCode implements BaseModelUtils.UserGetVerifiedCode {

        @Override
        public void postUserGetVerifiedCode(String verifiedCodeJsonString, final CallBackUtils.UserGetVerifiedCodeCallBack callBack) {
            Logger.i(verifiedCodeJsonString);

            OkHttpUtils.post().url(Constant.MOBILE_FRONT + verifiedCodeJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("获取短信验证码加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("获取短信验证码成功__" + response);
                    ResponseParamsVerifiedCode info = new Gson().fromJson(response,
                            ResponseParamsVerifiedCode.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            String isUser = info.getIsUser();
                            callBack.CallVerifiedCode(isUser);
                        } else {
                            ToastUtils.showLongToast("获取短信验证码验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }


    /**
     * 用户注册
     */

    class PostUserRegister implements BaseModelUtils.UserRegister {

        @Override
        public void postUserRegister(String registerJsonString, final CallBackUtils.UserRegisterCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + registerJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("用户注册网络加载异常_" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("用户注册返回_" + response);
                    ResponseParamsLogin info = new Gson().fromJson(
                            response, ResponseParamsLogin.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        if (!TextUtils.isEmpty(BaseBean.getOpenId())) {
                            BaseBean.saveIsBinder("1");
                            callBack.callRegisterResponse("wechat");//微信绑定成功
                        } else {
                            callBack.callRegisterResponse("login");//登录
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 重置密码
     */
    class PostUserResetPwd implements BaseModelUtils.UserResetPwd {

        @Override
        public void postUserResetPwd(String getUserResetPasswordJsonString, final CallBackUtils.UserResetPwdCallBack callBack) {

            OkHttpUtils.post().url(Constant.MOBILE_FRONT + getUserResetPasswordJsonString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("密码重置网络加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("密码重置加载成功_" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.Response();
                        } else {
                            ToastUtils.showLongToast("密码重置验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }

    }
}




