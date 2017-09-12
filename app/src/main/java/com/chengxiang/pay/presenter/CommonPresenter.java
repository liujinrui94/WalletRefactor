package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.SlideImageBean;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.CommonPostModel;
import com.chengxiang.pay.view.CommonView;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/7 17:21
 * @description: 公共接口请求
 */
public interface CommonPresenter {
    /**
     * 轮播图
     */
    class SlideShow {
        private CommonPostModel.PostSlideShow postSlideShow;
        private CommonView.SlideShowView slideShowView;

        public SlideShow(CommonView.SlideShowView slideShowView) {
            this.slideShowView = slideShowView;
            postSlideShow = new CommonPostModel.PostSlideShow();
        }

        public void postSlideShow() {
            postSlideShow.postSlideShow(slideShowView.getSlideShowJsonString(), new CallBackUtils.SlideShowCallBack() {
                @Override
                public void slideShowResponse(ArrayList<SlideImageBean> newList) {
                    slideShowView.slideShowResponse(newList);
                }

                @Override
                public void OnNetError() {
                    slideShowView.showCordError("轮播图网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    slideShowView.showCordError(msg);
                }
            });
        }
    }


}
