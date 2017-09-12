package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/29 13:56
 * @description: 登录返回参数
 */
public class ResponseParamsLogin extends BaseResponseParams {

    private String level;//当前等级
    private String levelStr;//等级描述

    private String processAduit;//审核状态
    private String processAduitStr;//审核状态描述

    private String nickName;//微信昵称
    private String headImg;//头像
    private String id;//用户ID
    private String isBind;//是否绑定手机号
    private String openId;//微信登录成功返回的 openid

    private String version;//后台配置版本号（对比设置审核时显示内容）


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsBind() {
        return isBind;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getProcessAduitStr() {
        return processAduitStr;
    }

    public void setProcessAduitStr(String processAduitStr) {
        this.processAduitStr = processAduitStr;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public String getProcessAduit() {
        return processAduit;
    }

    public void setProcessAduit(String processAduit) {
        this.processAduit = processAduit;
    }

}
