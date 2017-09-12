package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/5 15:12
 * @description: 我的信息相应参数
 */

public class ResponseParamsMyInfo extends BaseResponseParams {

    private String id;//id
    private String level;//用户当前等级
    private String processAduit;
    private String levelStr;//等级描述
    private String url;  //等级图
    private String tjUserPhone;//    推荐人id
    private String newsNum;//未读消息条数
    private String directNum;//推广用户数（5级）
    private String linkName;//联系客服名称
    private String wxFeeRate;//微信费率
    private String aliFeeRate;//支付宝费率
    private String unFeeRate;//快捷费率
    private String fixFeeRate;//固码费率

    public String getFixFeeRate() {
        return fixFeeRate;
    }

    public void setFixFeeRate(String fixFeeRate) {
        this.fixFeeRate = fixFeeRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTjUserPhone() {
        return tjUserPhone;
    }

    public void setTjUserPhone(String tjUserPhone) {
        this.tjUserPhone = tjUserPhone;
    }

    public String getNewsNum() {
        return newsNum;
    }

    public void setNewsNum(String newsNum) {
        this.newsNum = newsNum;
    }

    public String getDirectNum() {
        return directNum;
    }

    public void setDirectNum(String directNum) {
        this.directNum = directNum;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getWxFeeRate() {
        return wxFeeRate;
    }

    public void setWxFeeRate(String wxFeeRate) {
        this.wxFeeRate = wxFeeRate;
    }

    public String getAliFeeRate() {
        return aliFeeRate;
    }

    public void setAliFeeRate(String aliFeeRate) {
        this.aliFeeRate = aliFeeRate;
    }

    public String getUnFeeRate() {
        return unFeeRate;
    }

    public void setUnFeeRate(String unFeeRate) {
        this.unFeeRate = unFeeRate;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProcessAduit() {
        return processAduit;
    }

    public void setProcessAduit(String processAduit) {
        this.processAduit = processAduit;
    }
}
