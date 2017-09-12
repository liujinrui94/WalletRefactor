package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/8 10:46
 * @description: 轮播图响应
 */


public class ResponseParamsSlideShow extends BaseResponseParams {

    private ArrayList<SlideImageBean> newList;

    public ArrayList<SlideImageBean> getNewList() {
        return newList;
    }

    public void setNewList(ArrayList<SlideImageBean> newList) {
        this.newList = newList;
    }


}
