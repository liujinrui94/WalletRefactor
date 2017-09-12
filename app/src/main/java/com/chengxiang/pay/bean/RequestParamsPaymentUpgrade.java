package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/23 13:43
 * @description: 支付升级请求
 */

public class RequestParamsPaymentUpgrade extends BaseRequestParams {

    private String levelId;//将要升级到的等级
    private String orderAmt;//升级所需费用
    private String phoneNum;//升级人手机号码
    private String type;//0 微信 1 支付宝

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
