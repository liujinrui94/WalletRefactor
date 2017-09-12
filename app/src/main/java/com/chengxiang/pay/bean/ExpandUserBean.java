package com.chengxiang.pay.bean;

import java.io.Serializable;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 18:35
 * @description: 拓展的用户详细
 */


public class ExpandUserBean implements Serializable {

    private String phoneNum;//用户注册手机号
    private String imgUrl;//图像
    private String levelImgUrl;//级别图像
    private String regTime;//注册时间
    private String regState;//注册状态
    private String directNum;//直接拓展人数
    private String isZhijie;//是否为直接拓展人

    public String getIsZhijie() {
        return isZhijie;
    }

    public void setIsZhijie(String isZhijie) {
        this.isZhijie = isZhijie;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLevelImgUrl() {
        return levelImgUrl;
    }

    public void setLevelImgUrl(String levelImgUrl) {
        this.levelImgUrl = levelImgUrl;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getRegState() {
        return regState;
    }

    public void setRegState(String regState) {
        this.regState = regState;
    }

    public String getDirectNum() {
        return directNum;
    }

    public void setDirectNum(String directNum) {
        this.directNum = directNum;
    }
}
