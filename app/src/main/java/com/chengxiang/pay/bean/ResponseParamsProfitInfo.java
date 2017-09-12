package com.chengxiang.pay.bean;

import java.io.Serializable;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/9 15:38
 * @description:
 */
public class ResponseParamsProfitInfo extends BaseResponseParams implements Serializable {

    private String balance;//账户余额
    private String amt;//可提现金额
    private String zaituAmt;//待收入
    private int directNum;//累计拓展总数（5级）
    private int pNum1;//直接人数
    private int pNum2;//间接人数
    private int pNum3;//隔代人数
    private int pNum4;//四级以后人数
    private String totleAmt;//本月收益总数
    private String ordShareAmt;//交易分润
    private String upShareAmt;//升级分润
    private String upAmt;//提升收益
    private String addAmt;//可增加收益
    private String nextLevelName;//下一等级名称
    private String allShareAmt;//所有分润总额
    private String isDisplay;//是否显示

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getAllShareAmt() {
        return allShareAmt;
    }

    public void setAllShareAmt(String allShareAmt) {
        this.allShareAmt = allShareAmt;
    }

    public String getNextLevelName() {
        return nextLevelName;
    }

    public void setNextLevelName(String nextLevelName) {
        this.nextLevelName = nextLevelName;
    }

    public String getTotleAmt() {
        return totleAmt;
    }

    public void setTotleAmt(String totleAmt) {
        this.totleAmt = totleAmt;
    }

    public String getOrdShareAmt() {
        return ordShareAmt;
    }

    public void setOrdShareAmt(String ordShareAmt) {
        this.ordShareAmt = ordShareAmt;
    }

    public String getUpShareAmt() {
        return upShareAmt;
    }

    public void setUpShareAmt(String upShareAmt) {
        this.upShareAmt = upShareAmt;
    }

    public String getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(String upAmt) {
        this.upAmt = upAmt;
    }

    public String getAddAmt() {
        return addAmt;
    }

    public void setAddAmt(String addAmt) {
        this.addAmt = addAmt;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getZaituAmt() {
        return zaituAmt;
    }

    public void setZaituAmt(String zaituAmt) {
        this.zaituAmt = zaituAmt;
    }

    public int getDirectNum() {
        return directNum;
    }

    public void setDirectNum(int directNum) {
        this.directNum = directNum;
    }

    public int getpNum1() {
        return pNum1;
    }

    public void setpNum1(int pNum1) {
        this.pNum1 = pNum1;
    }

    public int getpNum2() {
        return pNum2;
    }

    public void setpNum2(int pNum2) {
        this.pNum2 = pNum2;
    }

    public int getpNum3() {
        return pNum3;
    }

    public void setpNum3(int pNum3) {
        this.pNum3 = pNum3;
    }

    public int getpNum4() {
        return pNum4;
    }

    public void setpNum4(int pNum4) {
        this.pNum4 = pNum4;
    }

    @Override
    public String toString() {
        return "ResponseParamsProfiInfo{" +
                "balance='" + balance + '\'' +
                ", amt='" + amt + '\'' +
                ", zaituAmt='" + zaituAmt + '\'' +
                ", directNum='" + directNum + '\'' +
                ", pNum1='" + pNum1 + '\'' +
                ", pNum2='" + pNum2 + '\'' +
                ", pNum3='" + pNum3 + '\'' +
                ", pNum4='" + pNum4 + '\'' +
                ", totleAmt='" + totleAmt + '\'' +
                ", ordShareAmt='" + ordShareAmt + '\'' +
                ", upShareAmt='" + upShareAmt + '\'' +
                ", upAmt='" + upAmt + '\'' +
                ", addAmt='" + addAmt + '\'' +
                ", nextLevelName='" + nextLevelName + '\'' +
                ", allShareAmt='" + allShareAmt + '\'' +
                ", isDisplay='" + isDisplay + '\'' +
                '}';
    }
}