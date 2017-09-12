package com.chengxiang.pay.presenter;

import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.UpgradePostModel;
import com.chengxiang.pay.view.UpgradeView;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/27 14:55
 * @description: 升级页面请求层
 */


public interface UpgradePresenter {
    /**
     * 检测版本更新
     */
    class Upgrade {

        private UpgradeView.CheckUpgradeView checkUpgradeView;
        private UpgradePostModel.PostCheckUpgrade postCheckUpgrade;

        public Upgrade(UpgradeView.CheckUpgradeView checkUpgradeView) {
            this.checkUpgradeView = checkUpgradeView;
            postCheckUpgrade = new UpgradePostModel.PostCheckUpgrade();
        }

        //检测升级
        public void PostCheckUpgrade() {
            postCheckUpgrade.postCheckUpgrade(checkUpgradeView.getCheckUpgradeString(), new CallBackUtils.UpgradeCallBack() {

                @Override
                public void notUpgrade( String retMsg) {
                    checkUpgradeView.notUpgrade(retMsg);
                }

                @Override
                public void callResponse(String isUpVersion, String versionDesc, String downUrl) {
                    checkUpgradeView.setUpgradeDate(isUpVersion, versionDesc, downUrl);
                }

                @Override
                public void OnNetError() {
                    checkUpgradeView.showCordError("校验应用版本信息网络异常");
                }

                @Override
                public void CodeError(String msg) {
                    checkUpgradeView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 下载新版本apk
     */
    class DownloadApk {
        private UpgradeView.DownloadApkView downloadApkView;
        private String downloadApk;
        private String fileAddress;
        private String name;
        private UpgradePostModel.PostDownloadApk postDownloadApk;

        public DownloadApk(UpgradeView.DownloadApkView downloadApkView, String downloadApk, String fileAddress, String name) {
            this.downloadApkView = downloadApkView;
            this.downloadApk = downloadApk;
            this.fileAddress = fileAddress;
            this.name = name;
            postDownloadApk = new UpgradePostModel.PostDownloadApk();
        }

        //检测升级
        public void PostDownloadApk() {
            postDownloadApk.postDownloadApk(downloadApk, fileAddress, name, new CallBackUtils.DownloadApkCallBack() {
                @Override
                public void callProgress(float progress) {
                    downloadApkView.DownloadProgress(progress);
                }

                @Override
                public void callResponse(String saveAddress) {
                    downloadApkView.downloadComplete(saveAddress);
                }

                @Override
                public void OnNetError() {
                    downloadApkView.showNetError("apk下载网络异常");
                }
            });
        }
    }
}
