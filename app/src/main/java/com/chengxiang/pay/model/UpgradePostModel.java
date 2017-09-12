package com.chengxiang.pay.model;

import com.chengxiang.pay.framework.constant.Constant;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/30 10:03
 * @description: 升级相关Model
 */


public interface UpgradePostModel {

    /**
     * 检测版本更新
     */
    class PostCheckUpgrade implements BaseModelUtils.Upgrade {

        @Override
        public void postCheckUpgrade(String requestString, final CallBackUtils.UpgradeCallBack callBack) {

            OkHttpUtils.post().url(Constant.MOBILE_FRONT + requestString)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("检查版本更新网络异常__" + e.toString());
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i("版本信息__" + response);
                    try {
                        JSONObject info = new JSONObject(response);
                        String retCode = info.getString("retCode");
                        //retCode="0000"有更新,"9987"最新版本,其他参数错误
                        switch (retCode) {
                            case Constant.RESPONSE_SUCCESS:
                                String isUpVersion = info.getString("isUpVersion");//是否强制更新
                                String verDesc = info.getString("verDesc");//升级内容
                                String downloadUrl = info.getString("downloadUrl");//下载地址
                                callBack.callResponse(isUpVersion, verDesc, downloadUrl);
                                break;
                            case "9987":
                                String retMsg1 = info.getString("retMsg");
                                callBack.notUpgrade(retMsg1);//最新版本
                                break;
                            default:
                                String retMsg = info.getString("retMsg");//返回信息
                                callBack.CodeError(retMsg);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Logger.e("检查升级解析异常_" + e.toString());
                        callBack.CodeError("检查升级解析异常");
                    }
                }
            });
        }

    }

    /**
     * 下载新版本APK
     */
    class PostDownloadApk implements BaseModelUtils.DownloadApk {
        @Override
        public void postDownloadApk(String downloadUrl, String fileAddress, String name, final CallBackUtils.DownloadApkCallBack callBack) {
            OkHttpUtils.get().url(downloadUrl).build().execute(new FileCallBack(fileAddress, name) {
                @Override
                public void inProgress(float progress, long total, int id) {
                    super.inProgress(progress, total, id);
                    callBack.callProgress(progress);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("下载出错_" + call.toString() + "------" + e.toString() + "------" + id);
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(File response, int id) {
                    Logger.i("response_" + response.toString());
                    callBack.callResponse(response.toString());
                }
            });
        }
    }

}
