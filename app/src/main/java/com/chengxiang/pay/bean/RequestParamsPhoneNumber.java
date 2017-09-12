package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 9:30
 * @description: 请求参数只有手机号码
 */

public class RequestParamsPhoneNumber extends BaseRequestParams {

    private String phoneNum;//用户登录手机号

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


}
