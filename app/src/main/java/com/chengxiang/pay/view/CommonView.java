package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.SlideImageBean;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/8 8:33
 * @description: 公共接口页面
 */
public interface CommonView {
    /**
     * 轮播图
     */
    interface SlideShowView extends BaseView {

        String getSlideShowJsonString();

        void slideShowResponse(ArrayList<SlideImageBean> newList);
    }








}
