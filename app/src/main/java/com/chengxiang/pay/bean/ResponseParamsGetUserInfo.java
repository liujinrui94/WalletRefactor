package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/14 16:07
 * @description: 用户信息
 */

public class ResponseParamsGetUserInfo extends BaseResponseParams {

    private String cardNo;//结算卡号
    private String openBank;//开户行
    private String certCode;//身份证号
    private String merName;//商户名称
    private String userName;//用户名

    private String opposite;//身份证正面(身份证正面+储蓄卡正面)
    private String positive;//身份证反面(身份证反面+信用卡正面)
    private String cardOpposite;//银行卡反面
    private String cardPositive;//银行卡正面
    private String meet;//手持(本人手持身份证+储蓄卡正面合照)

    public String getOpposite() {
        return opposite;
    }

    public void setOpposite(String opposite) {
        this.opposite = opposite;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getCardOpposite() {
        return cardOpposite;
    }

    public void setCardOpposite(String cardOpposite) {
        this.cardOpposite = cardOpposite;
    }

    public String getCardPositive() {
        return cardPositive;
    }

    public void setCardPositive(String cardPositive) {
        this.cardPositive = cardPositive;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
