package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/3 15:40
 * @description: 注册请求参数
 */

public class RequestParamsRegister extends BaseRequestParams {

    private String code;//短信验证码
    private String phoneNum;//手机号码
    private String pwd;//密码
    private String tjUserPhone;//推荐人手机号
    private String signImg;//签名图片地址
    private String openId;//微信登录成功返回id

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTjUserPhone() {
        return tjUserPhone;
    }

    public void setTjUserPhone(String tjUserPhone) {
        this.tjUserPhone = tjUserPhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
