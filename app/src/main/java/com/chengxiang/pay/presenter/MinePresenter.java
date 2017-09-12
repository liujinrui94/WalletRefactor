package com.chengxiang.pay.presenter;

import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.MinePostModel;
import com.chengxiang.pay.view.MineView;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:41
 * @description: 我的页面请求层
 */


public interface MinePresenter {
    /**
     * 我的相关信息
     */
    class MineInfo {
        private MinePostModel.PostMineInfo mineInfo;
        private MineView.MineInfoView mineInfoView;

        public MineInfo(MineView.MineInfoView mineInfoView) {
            this.mineInfoView = mineInfoView;
            mineInfo = new MinePostModel.PostMineInfo();
        }

        public void PostMineInfo() {
            mineInfo.postMineInfo(mineInfoView.getMineInfoJsonString(), new CallBackUtils.MineInfoCallBack() {
                @Override
                public void mineInfoResponse() {
                    mineInfoView.mineInfoResponse();
                }

                @Override
                public void OnNetError() {
                    mineInfoView.showCordError("获取我的个人信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    mineInfoView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 查询用户信息
     */
    class UserInfo {
        private MinePostModel.PostUserInfo userInfo;
        private MineView.UserInfoView userInfoView;

        public UserInfo(MineView.UserInfoView userInfoView) {
            this.userInfoView = userInfoView;
            userInfo = new MinePostModel.PostUserInfo();
        }

        public void PostUserInfo() {
            userInfo.postUserInfo(userInfoView.getUserInfoJsonString(), new CallBackUtils.UserInfoCallBack() {
                @Override
                public void userInfoResponse() {
                    userInfoView.userInfoResponse();
                }

                @Override
                public void OnNetError() {
                    userInfoView.showCordError("获取用户信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    userInfoView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 上传File(图片)
     */
    class UploadFile {
        private MinePostModel.PostUploadFile uploadFile;
        private MineView.UploadFileView uploadFileView;

        public UploadFile(MineView.UploadFileView uploadFileView) {
            this.uploadFileView = uploadFileView;
            uploadFile = new MinePostModel.PostUploadFile();
        }

        public void PostUploadFile() {
            uploadFile.postUploadFile(uploadFileView.getUploadFileJsonString(), uploadFileView.getUploadFileImage(), new CallBackUtils.UploadFileCallBack() {
                @Override
                public void uploadFileResponse(String fileName) {
                    uploadFileView.uploadFileResponse(fileName);
                }

                @Override
                public void OnNetError() {
                    uploadFileView.showCordError("上传图片网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    uploadFileView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 完善用户照片信息
     */
    class PerfectPhotoData {
        private MinePostModel.PerfectPhotoData perfectPhotoData;
        private MineView.PerfectPhotoDataView perfectPhotoDataView;

        public PerfectPhotoData(MineView.PerfectPhotoDataView perfectPhotoDataView) {
            this.perfectPhotoDataView = perfectPhotoDataView;
            perfectPhotoData = new MinePostModel.PerfectPhotoData();
        }

        public void PostPerfectPhotoData() {
            perfectPhotoData.postPerfectPhotoData(perfectPhotoDataView.getPerfectPhotoDataJsonString(), new CallBackUtils.PerfectPhotoDataCallBack() {
                @Override
                public void perfectPhotoDataResponse() {
                    perfectPhotoDataView.perfectPhotoDataResponse();
                }

                @Override
                public void OnNetError() {
                    perfectPhotoDataView.showCordError("完善用户照片信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    perfectPhotoDataView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 完善用户银行卡等基本信息
     */
    class PerfectBankData {
        private MinePostModel.PerfectBankData perfectBankData;
        private MineView.PerfectBankDataView perfectBankDataView;

        public PerfectBankData(MineView.PerfectBankDataView perfectBankDataView) {
            this.perfectBankDataView = perfectBankDataView;
            perfectBankData = new MinePostModel.PerfectBankData();
        }

        public void PostPerfectBankData() {
            perfectBankData.postPerfectBankData(perfectBankDataView.getPerfectBankDataJsonString(), new CallBackUtils.PerfectBankDataCallBack() {
                @Override
                public void perfectBankDataResponse() {
                    perfectBankDataView.perfectBankDataResponse();
                }

                @Override
                public void OnNetError() {
                    perfectBankDataView.showCordError("完善银行卡信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    perfectBankDataView.showCordError(msg);
                }

            });
        }
    }
}

