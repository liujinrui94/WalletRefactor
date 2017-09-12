package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.BaseResponseParams;
import com.chengxiang.pay.bean.MessageBean;
import com.chengxiang.pay.bean.ResponseParamsMessageDetail;
import com.chengxiang.pay.bean.ResponseParamsMessageList;
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
 * @description: 消息相关Model
 */


public interface MessagePostModel {
    /**
     * 消息列表
     */
    class PostMessageList implements BaseModelUtils.MessageList {
        @Override
        public void postMessageList(String requestString, final CallBackUtils.MessageListCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("消息列表加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("消息列表返回成功__" + response);
                    ResponseParamsMessageList info = new Gson().fromJson(response,
                            ResponseParamsMessageList.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            ArrayList<MessageBean> messageList = info.getList();
                            callBack.messageListResponse(messageList);
                        } else {
                            ToastUtils.showLongToast("消息列表验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 消息详情（调用后消息标记为已读）
     */
    class PostMessageDetail implements BaseModelUtils.MessageDetail {
        @Override
        public void postMessageDetail(String requestString, final CallBackUtils.MessageDetailCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("消息详情加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("消息详情返回成功__" + response);
                    ResponseParamsMessageDetail info = new Gson().fromJson(response,
                            ResponseParamsMessageDetail.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.messageDetailResponse();
                        } else {
                            ToastUtils.showLongToast("消息详情验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

    /**
     * 编辑消息（已读、删除）
     */
    class PostMessageModify implements BaseModelUtils.MessageModify {
        @Override
        public void postMessageModify(String requestString, final CallBackUtils.MessageModifyCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("编辑消息加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("编辑消息返回成功__" + response);
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            callBack.messageModifyResponse();
                        } else {
                            ToastUtils.showLongToast("编辑消息验签失败");
                        }
                    } else {
                        callBack.CodeError(info.getRetMsg());
                    }
                }
            });
        }
    }

}




