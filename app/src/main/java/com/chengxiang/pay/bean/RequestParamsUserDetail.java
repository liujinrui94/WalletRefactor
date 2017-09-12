package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/13 21:28
 * @description: 收益列表明细（9008）
 */


public class RequestParamsUserDetail extends BaseRequestParams {
    private String phoneNum;//手机号码
    private String lowPhoneNum;//下级用户id

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLowPhoneNum() {
        return lowPhoneNum;
    }

    public void setLowPhoneNum(String lowPhoneNum) {
        this.lowPhoneNum = lowPhoneNum;
    }
}
