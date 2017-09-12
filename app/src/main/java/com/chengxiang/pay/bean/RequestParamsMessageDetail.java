package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/2 19:35
 * @description: 消息详情请求
 */

public class RequestParamsMessageDetail extends BaseRequestParams {
    private String id;//消息id消息唯一标识
    private String phoneNum;//手机号码

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
