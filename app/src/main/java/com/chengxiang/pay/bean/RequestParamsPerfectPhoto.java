package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/10 18:47
 * @description: 完善照片信息
 */

public class RequestParamsPerfectPhoto extends BaseRequestParams {

    private String phoneNum;//注册用户号
    private String positive;//身份证正面+储蓄卡正面
    private String opposite;//身份证反面+信用卡正面
    private String groupPhoto;//本人手持身份证+储蓄卡正面合照

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getOpposite() {
        return opposite;
    }

    public void setOpposite(String opposite) {
        this.opposite = opposite;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }
}
