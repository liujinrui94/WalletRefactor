package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/11 17:50
 * @description: 银行卡信息
 */


public class BankInfoBean {
    private String carNo;//银行卡号
    private String bankName;//所属银行

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
