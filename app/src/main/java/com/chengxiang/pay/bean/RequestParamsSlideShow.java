package com.chengxiang.pay.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/8 8:53
 * @description: 轮播图(8003)
 */
public class RequestParamsSlideShow extends BaseRequestParams {
    private String type;//操作类型 1 消息页 2 更多服务页

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
