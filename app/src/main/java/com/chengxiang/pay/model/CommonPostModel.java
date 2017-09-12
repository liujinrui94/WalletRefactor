package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.ResponseParamsSlideShow;
import com.chengxiang.pay.bean.SlideImageBean;
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
 * @time: 2017/8/7 16:19
 * @description:
 */
public interface CommonPostModel {

    /**
     * 轮播图
     */
    class PostSlideShow implements BaseModelUtils.SlideShow {
        @Override
        public void postSlideShow(String requestString, final CallBackUtils.SlideShowCallBack callBack) {
            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("轮播图加载异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("轮播图信息返回成功__" + response);
                    ResponseParamsSlideShow info = new Gson().fromJson(response,
                            ResponseParamsSlideShow.class);
                    if (info.getRetCode().equals(Constant.RESPONSE_SUCCESS)) {
                        // 校验签名 同理 如果只校验公共参数则传空，否则传私有
                        // audit n.审计，查账;vt.审计，查账;旁听;vi.审计
                        if (SignUtil.verifyParams(EncryptManager.getEncryptManager(), info, null)) {
                            ArrayList<SlideImageBean> newList = info.getNewList();
                            callBack.slideShowResponse(newList);
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


}
