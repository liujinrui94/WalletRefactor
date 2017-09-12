package com.chengxiang.pay.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 8:29
 * @description:
 */
public class BalanceModel {
    private String okAmt;//可提现金额
    private String noAmt;//不可提现金额
    private String balance;//账户余额

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOkAmt() {
        return okAmt;
    }

    public void setOkAmt(String okAmt) {
        this.okAmt = okAmt;
    }

    public String getNoAmt() {
        return noAmt;
    }

    public void setNoAmt(String noAmt) {
        this.noAmt = noAmt;
    }
}
