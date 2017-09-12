package com.chengxiang.pay.bean;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/28 17:54
 * @description: 默认请求参数基数
 */

public class BaseRequestParams {
    private String seq;//流水号
    private String funCode;//业务功能码
    private String appType;//客户端类型：1、大众版
    private String appVersion;//客户端程序版本
    private String appOs;//客户端操作系统Android:7.1.1、iOS:10.3.1
    private String IMEI;//IMEI号
    private String IMSI;//IMSI号
    private String deviceSN;//设备序列号
    private String deviceType;//设备类型(meizu_mx5)
    private String MAC;//设备网卡MAC地址
    private String IP;//IP地址
    private String sign;//数字签名
    private String mobKey;//密钥串
    private String taccountId;
    private String orgId;
    private String workey;

    public BaseRequestParams() {
    }


    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppOs() {
        return appOs;
    }

    public void setAppOs(String appOs) {
        this.appOs = appOs;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getTaccountId() {
        return taccountId;
    }

    public void setTaccountId(String taccountId) {
        this.taccountId = taccountId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getWorkey() {
        return workey;
    }

    public void setWorkey(String workey) {
        this.workey = workey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMobKey() {
        return mobKey;
    }

    public void setMobKey(String mobKey) {
        this.mobKey = mobKey;
    }
}
