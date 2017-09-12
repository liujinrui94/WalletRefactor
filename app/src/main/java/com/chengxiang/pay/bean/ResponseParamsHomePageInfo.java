package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/9 19:23
 * @description: 首页返回信息（8001）
 */


public class ResponseParamsHomePageInfo extends BaseResponseParams {

    private String newText;//最新消息内容
    private String newTitle;//最新消息标题
    private String newNum;//消息未读条数
    private String balance;//账户余额
    private String totalCollectAmt;//累计收益
    private String zaituAmt;//待入账收益
    private ArrayList<PaymentMethodBean> payList;
    private ArrayList<ProfitSummaryBean> inList;

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewNum() {
        return newNum;
    }

    public void setNewNum(String newNum) {
        this.newNum = newNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotalCollectAmt() {
        return totalCollectAmt;
    }

    public void setTotalCollectAmt(String totalCollectAmt) {
        this.totalCollectAmt = totalCollectAmt;
    }

    public String getZaituAmt() {
        return zaituAmt;
    }

    public void setZaituAmt(String zaituAmt) {
        this.zaituAmt = zaituAmt;
    }

    public ArrayList<PaymentMethodBean> getPayList() {
        return payList;
    }

    public void setPayList(ArrayList<PaymentMethodBean> payList) {
        this.payList = payList;
    }

    public ArrayList<ProfitSummaryBean> getInList() {
        return inList;
    }

    public void setInList(ArrayList<ProfitSummaryBean> inList) {
        this.inList = inList;
    }
}
