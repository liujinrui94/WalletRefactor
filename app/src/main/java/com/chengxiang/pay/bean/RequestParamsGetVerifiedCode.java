package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/3 9:27
 * @description: 获取短信验证码
 */

public class RequestParamsGetVerifiedCode extends BaseRequestParams {

    private String phoneNum;//手机号码
    private String type;//0、注册;1、找回密码;2、微信登录后完善注册信息

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
