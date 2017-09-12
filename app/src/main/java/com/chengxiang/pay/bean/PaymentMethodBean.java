package com.chengxiang.pay.bean;

import java.io.Serializable;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/9 19:25
 * @description: 收款方式
 */

public class PaymentMethodBean implements Serializable {

    private String payName;//付款方式名称
    private String payType;//付款方式id
    private String cardLimit;//单卡限额
    private String accountDate;//到账日期
    private String totalCardLimit;//单卡单日限额

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getTotalCardLimit() {
        return totalCardLimit;
    }

    public void setTotalCardLimit(String totalCardLimit) {
        this.totalCardLimit = totalCardLimit;
    }
}
