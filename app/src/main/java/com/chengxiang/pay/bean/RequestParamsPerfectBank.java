package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/10 18:47
 * @description: 完善银行卡信息
 */

public class RequestParamsPerfectBank extends BaseRequestParams {

    private String bankId;//银行id
    private String bankCard;//银行卡号
    private String merName;//商户名称
    private String phoneNum;//注册用户号
    private String card_branch_bank;//支行代码
    private String bank_city_code;//银行所属市代码
    private String bank_prov_code;//银行所属省份代码
    private String card_branch_name;//支行名称
    private String idCard;//身份证号
    private String userName;//用户真实姓名
    private String bankMobile;//银行 预留手机号
    private String creditCard;// 信用卡卡号

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCard_branch_bank() {
        return card_branch_bank;
    }

    public void setCard_branch_bank(String card_branch_bank) {
        this.card_branch_bank = card_branch_bank;
    }

    public String getBank_city_code() {
        return bank_city_code;
    }

    public void setBank_city_code(String bank_city_code) {
        this.bank_city_code = bank_city_code;
    }

    public String getBank_prov_code() {
        return bank_prov_code;
    }

    public void setBank_prov_code(String bank_prov_code) {
        this.bank_prov_code = bank_prov_code;
    }

    public String getCard_branch_name() {
        return card_branch_name;
    }

    public void setCard_branch_name(String card_branch_name) {
        this.card_branch_name = card_branch_name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
