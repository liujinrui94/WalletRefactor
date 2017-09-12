package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/29 13:56
 * @description: 默认返回参数基数
 */

public class BaseResponseParams {
	private String seq;//流水号
	private String funCode;//业务功能码
	private String retCode;//错误码：0000成功；其他失败
	private String retMsg;//错误描述
	private String appType;//客户端类型：0 : android 1 : IOS 2 : WP
	private String appVersion;//客户端程序版本
	private String appOs;//客户端操作系统
	private String IMEI;//IMEI号
	private String IMSI;//IMSI号
	private String deviceSN;//设备序列号
	private String deviceType;//设备型号
	private String MAC;//设备网卡MAC地址
	private String IP;//IP地址

	private String workey;

	private String mobKey;//密钥串
	private String sign;//数字签名
	
	private String taccounId;


	public String getTaccounId() {
		return taccounId;
	}

	public void setTaccounId(String taccounId) {
		this.taccounId = taccounId;
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

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
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

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getWorkey() {
		return workey;
	}

	public void setWorkey(String workey) {
		this.workey = workey;
	}

	public String getMobKey() {
		return mobKey;
	}

	public void setMobKey(String mobKey) {
		this.mobKey = mobKey;
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
