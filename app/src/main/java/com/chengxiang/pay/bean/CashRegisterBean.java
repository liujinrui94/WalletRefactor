package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 16:35
 * @description: 提现记录响应
 */


public class CashRegisterBean {

    private String appTime;//申请时间
    private String liqTime;//划款时间
    private String liqAmt;//划款金额
    private String amt;//提现金额
    private String fee;//手续费
    private String liqState;//出款状态（2成功 3 失败 其余是提现处理中）

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getLiqTime() {
        return liqTime;
    }

    public void setLiqTime(String liqTime) {
        this.liqTime = liqTime;
    }

    public String getLiqAmt() {
        return liqAmt;
    }

    public void setLiqAmt(String liqAmt) {
        this.liqAmt = liqAmt;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getLiqState() {
        return liqState;
    }

    public void setLiqState(String liqState) {
        this.liqState = liqState;
    }
}
