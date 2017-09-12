package com.chengxiang.pay.view;

import com.chengxiang.pay.framework.base.BaseView;

import java.io.File;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:19
 * @description: 我的相关页面请求
 */


public interface MineView {

    /**
     * 我的视图层
     */
    interface MineInfoView extends BaseView {

        String getMineInfoJsonString();

        void mineInfoResponse();
    }

    /**
     * 查看用户资料
     */
    interface UserInfoView extends BaseView {

        String getUserInfoJsonString();

        void userInfoResponse();
    }

    /**
     * 上传File(图片)
     */
    interface UploadFileView extends BaseView {

        String getUploadFileJsonString();

        File getUploadFileImage();

        void uploadFileResponse(String fileName);
    }

    /**
     * 完善用户照片信息
     */
    interface PerfectPhotoDataView extends BaseView {

        String getPerfectPhotoDataJsonString();

        void perfectPhotoDataResponse();
    }

    /**
     * 完善用户银行卡等基本信息
     */
    interface PerfectBankDataView extends BaseView {

        String getPerfectBankDataJsonString();

        void perfectBankDataResponse();
    }

}