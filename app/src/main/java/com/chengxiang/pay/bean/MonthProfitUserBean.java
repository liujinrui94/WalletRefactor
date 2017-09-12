package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 19:22
 * @description: 本月收益单列用户信息
 */


public class MonthProfitUserBean {
    private String phoneNum;//用户注册手机号
    private String img;//图像
    private String levelImg;//等级图像
    private String shareAmt;//分润额
    private String dateTime;//日期
    // TODO: 2017/8/10 以下两个参数判断
    private String shareMeno;
    private String isZhijie;

    public String getIsZhijie() {
        return isZhijie;
    }

    public void setIsZhijie(String isZhijie) {
        this.isZhijie = isZhijie;
    }

    public String getShareMeno() {
        return shareMeno;
    }

    public void setShareMeno(String shareMeno) {
        this.shareMeno = shareMeno;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLevelImg() {
        return levelImg;
    }

    public void setLevelImg(String levelImg) {
        this.levelImg = levelImg;
    }

    public String getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(String shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
