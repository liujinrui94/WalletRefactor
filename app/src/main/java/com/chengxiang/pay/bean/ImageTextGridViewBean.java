package com.chengxiang.pay.bean;

import java.io.Serializable;

public class ImageTextGridViewBean implements Serializable {
    private int imageId;//图片id
    private String describe;//描述

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
