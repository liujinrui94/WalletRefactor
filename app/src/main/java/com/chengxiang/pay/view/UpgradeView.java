package com.chengxiang.pay.view;

import com.chengxiang.pay.framework.base.BaseView;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/26 17:41
 * @description: 检测版本升级
 */


public interface UpgradeView {

    /**
     * 检测版本更新
     */
    interface CheckUpgradeView extends BaseView {
        /**
         * @return 检查升级字符串
         */
        String getCheckUpgradeString();

        /**
         * @param isUpVersion 0不需更新 ;1 需更新 2:需强制更新
         * @param versionDesc 更新内容
         * @param downUrl     下载地址
         */
        void setUpgradeDate(String isUpVersion, String versionDesc, String downUrl);

        /**
         * 不需要更新
         */
        void notUpgrade(String retMsg);

    }

    interface DownloadApkView {
        void DownloadProgress(float progress);

        /**
         * @param netMsg 网络异常
         */
        void showNetError(String netMsg);

        /**
         * @param saveAddress 下载完成，完成地址
         */
        void downloadComplete(String saveAddress);
    }

}