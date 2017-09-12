package com.chengxiang.pay.bean;

import java.io.Serializable;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/28 22:54
 * @description: 账单列表单个bean
 */


public class BillBean implements Serializable{
    private String type;//收款类型（0 微信  1 支付宝  2 固码  3 银联快捷）
    private String payTime;//交易时间
    private String accountTime;//预计到账时间
    private String amt;//订单金额
    private String state;//订单状态（0 未完成  1 成功  2 失败  99 失效）
    private String fee;//手续费
    private String liqState;//出款状态（0 未出  1 已出）
    private String settleAmt;//结算金额
    private String settleCard;//结算卡号
    private String settleBank;//结算银行
    private String settleState;//结算状态（0 未结  1 已结）
    private String payType;// 订单类型 1 升级 0 收款
    private String orderId;//订单号

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getSettleCard() {
        return settleCard;
    }

    public void setSettleCard(String settleCard) {
        this.settleCard = settleCard;
    }

    public String getSettleBank() {
        return settleBank;
    }

    public void setSettleBank(String settleBank) {
        this.settleBank = settleBank;
    }

    public String getSettleState() {
        return settleState;
    }

    public void setSettleState(String settleState) {
        this.settleState = settleState;
    }
}
