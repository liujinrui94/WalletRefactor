package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 12:27
 * @description: 二维码收款返回链接
 */

public class ResponseParamsCodeReceive extends BaseResponseParams {

    private String amt;//金额
    private String qrlink;//商户名
    private String userName;//商户名

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getQrlink() {
        return qrlink;
    }

    public void setQrlink(String qrlink) {
        this.qrlink = qrlink;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
