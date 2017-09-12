package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 12:24
 * @description: 二维码收款（5001）
 */

public class RequestParamsCodeReceive extends BaseRequestParams {

    private String amt;// 收款金额
    private String phoneNum;//用户注册手机号
    private String source;// 渠道类型0:微信,1:支付宝

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
