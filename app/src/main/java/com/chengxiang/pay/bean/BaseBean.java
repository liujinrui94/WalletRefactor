package com.chengxiang.pay.bean;

import android.content.Context;
import android.content.SharedPreferences;

import com.chengxiang.pay.framework.base.BaseApplication;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 11:59
 * @description: 基础数据
 */


public class BaseBean {
    private static Context context = BaseApplication.applicationContext;
    private static final String PREFERENCE_NAME = "BaseBean";//默认的sp保存的名字
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private static SharedPreferences getSp() {
        if (sp == null)
            sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp;
    }

    private static SharedPreferences.Editor getEd() {
        if (editor == null)
            editor = getSp().edit();
        return editor;
    }

    /**
     * 清除整个存储
     */
    public static void clear() {
        getEd().clear().apply();
    }

    private static void saveBoolean(String key, boolean value) {
        getEd().putBoolean(key, value).apply();
    }

    private static boolean getBoolean(String key) {
        return getSp().getBoolean(key, false);
    }

    private static void saveString(String key, String value) {
        getEd().putString(key, value).apply();
    }

    private static String getString(String key) {
        return getSp().getString(key, "");
    }

    private static void saveInt(String key, int value) {
        getEd().putInt(key, value).apply();
    }

    private static int getInt(String key) {
        return getSp().getInt(key, 0);
    }

    /**
     * 程序类型 1、大众版
     */
    public static void saveAppType(String appType) {
        saveString("appType", appType);
    }

    static String getAppType() {
        return getString("appType");
    }

    /**
     * 应用系统信息 Android:7.1.1
     */
    public static void saveAppOs(String appOs) {
        saveString("appOs", appOs);
    }

    static String getAppOs() {
        return getString("appOs");
    }

    /**
     * 程序版本
     */
    public static void saveAppVersion(String appVersion) {
        saveString("appVersion", appVersion);
    }

    public static String getAppVersion() {
        return getString("appVersion");
    }


    /**
     * IMEI
     */
    public static void saveImei(String imei) {
        saveString("imei", imei);
    }

    static String getImei() {
        return getString("imei");
    }

    /**
     * IMSI
     */
    public static void saveImsi(String imsi) {
        saveString("imsi", imsi);
    }

    static String getImsi() {
        return getString("imsi");
    }

    /**
     * 设备序列号
     */
    public static void saveDeviceSN(String deviceSN) {
        saveString("deviceSN", deviceSN);
    }

    static String getDeviceSN() {
        return getString("deviceSN");
    }


    /**
     * 设备类型(meizu_mx5)
     */
    public static void saveDeviceType(String deviceType) {
        saveString("deviceType", deviceType);
    }

    static String getDeviceType() {
        return getString("deviceType");
    }


    /**
     * 网卡MAC地址
     */
    public static void saveMac(String mac) {
        saveString("mac", mac);
    }

    static String getMac() {
        return getString("mac");
    }


    /**
     * ip地址
     */
    public static void saveIp(String ip) {
        saveString("ip", ip);
    }

    static String getIp() {
        return getString("ip");
    }

    /**
     * cookie, 保存SessionID
     */
    public static void saveCookie(String cookie) {
        saveString("cookie", cookie);
    }

    public String getCookie() {
        return getString("cookie");
    }

    /**
     * 业务区分，目前没用
     */
    public static void saveOrgId(String orgId) {
        saveString("orgId", orgId);
    }

    public static String getOrgId() {
        return getString("orgId");
    }

    /**
     * 主机地址
     */
    public static void saveBaseUrl(String baseUrl) {
        saveString("baseUrl", baseUrl);
    }

    public String getBaseUrl() {
        return getString("baseUrl");
    }

    /**
     * 是否登录
     */
    public static void saveIsLogin(boolean isLogin) {
        saveBoolean("isLogin", isLogin);
    }

    public static boolean getIsLogin() {
        return getBoolean("isLogin");
    }

    /**
     * 用户会话Token标记
     */
    public static void saveToken(String token) {
        saveString("token", token);
    }

    public String getToken() {
        return getString("token");
    }

    /**
     * Tuser表对应的id
     */
    public static void saveUserId(String userId) {
        saveString("userId", userId);
    }

    public String getUserId() {
        return getString("userId");
    }

    /**
     * 用户真名
     */
    public static void saveRealName(String realName) {
        saveString("realName", realName);
    }

    public static String getRealName() {
        return getString("realName");
    }

    /**
     * 商户名称
     */
    public static void saveMerchantName(String merchantName) {
        saveString("merchantName", merchantName);
    }

    public static String getMerchantName() {
        return getString("merchantName");
    }

    /**
     * 身份证号
     */
    public static void saveCertCode(String certCode) {
        saveString("certCode", certCode);
    }

    public static String getCertCode() {
        return getString("certCode");
    }

    /**
     * 银行卡号
     */
    public static void saveCardNo(String cardNo) {
        saveString("cardNo", cardNo);
    }

    public static String getCardNo() {
        return getString("cardNo");
    }

    /**
     * 支行名称
     */
    public static void saveOpenBank(String openBank) {
        saveString("openBank", openBank);
    }

    public static String getOpenBank() {
        return getString("openBank");
    }

    /**
     * 身份证正面加银行卡正面
     */
    public static void savePositive(String positive) {
        saveString("positive", positive);
    }

    public static String getPositive() {
        return getString("positive");
    }

    /**
     * 身份证反面加信用卡正面
     */
    public static void saveOpposite(String opposite) {
        saveString("opposite", opposite);
    }

    public static String getOpposite() {
        return getString("opposite");
    }

    /**
     * 合照
     */
    public static void saveMeet(String meet) {
        saveString("meet", meet);
    }

    public static String getMeet() {
        return getString("meet");
    }


    /**
     * 推荐人
     */
    public static void saveTjUserPhone(String tjUserPhone) {
        saveString("tjUserPhone", tjUserPhone);
    }

    public static String getTjUserPhone() {
        return getString("tjUserPhone");
    }

    /**
     * 主账户ID account n.账，账目;存款;记述，报告;理由
     * vi.
     * 解释;导致;报账
     * vt.
     * 认为;把…视作
     */
    public static void saveAccountId(String accountId) {
        saveString("accountId", accountId);
    }

    static String getAccountId() {
        return getString("accountId");
    }

    /**
     * 后台配置版本号（对比设置审核时显示内容）
     */
    public static void saveVersion(String version) {
        saveString("version", version);
    }

    public static String getVersion() {
        return getString("version");
    }


    /**
     * 商品描述
     */
    public static void saveCodeDescrption(String codeDescrption) {
        saveString("codeDescrption", codeDescrption);
    }

    public String getCodeDescrption() {
        return getString("codeDescrption");
    }

    /**
     * 微信登录临时票据
     */
    public static void saveWxCode(String wxCode) {
        saveString("wxCode", wxCode);
    }

    public static String getWxCode() {
        return getString("wxCode");
    }

    /**
     * 头像地址
     */
    public static void saveHeadImgUrl(String headImgUrl) {
        saveString("headImgUrl", headImgUrl);
    }

    public static String getHeadImgUrl() {
        return getString("headImgUrl");
    }

    /**
     * 微信昵称
     */
    public static void saveNickName(String nickName) {
        saveString("nickName", nickName);
    }

    public static String getNickName() {
        return getString("nickName");
    }

    /**
     * 是否绑定手机号
     */
    public static void saveIsBinder(String isBinder) {
        saveString("isBinder", isBinder);
    }

    public String getIsBinder() {
        return getString("isBinder");
    }

    /**
     * 累计收益
     */
    public static void saveTotalProfitAmt(String totalProfitAmt) {
        saveString("totalProfitAmt", totalProfitAmt);
    }

    public static String getTotalProfitAmt() {
        return getString("totalProfitAmt");
    }

    /**
     * 账户余额
     */
    public static void saveBalance(String balance) {
        saveString("balance", balance);
    }

    public static String getBalance() {
        return getString("balance");
    }

    /**
     * 可提现金额
     */
    public static void saveOkAmt(String okAmt) {
        saveString("okAmt", okAmt);
    }

    public static String getOkAmt() {
        return getString("okAmt");
    }

    /**
     * 不可提现金额
     */
    public static void saveNoAmt(String noAmt) {
        saveString("noAmt", noAmt);
    }

    public static String getNoAmt() {
        return getString("noAmt");
    }

    /**
     * 累计拓展总数（5级）
     */
    public static void saveDirectNum(int directNum) {
        saveInt("directNum", directNum);
    }

    public static int getDirectNum() {
        return getInt("directNum");
    }


    /**
     * 直接推荐人数
     */
    public static void savePerNum1(int pNum1) {
        saveInt("pNum1", pNum1);
    }

    public static int getPerNum1() {
        return getInt("pNum1");
    }


    /**
     * 间接人数
     */
    public static void savePerNum2(int pNum2) {
        saveInt("pNum2", pNum2);
    }

    public static int getPerNum2() {
        return getInt("pNum2");
    }

    /**
     * 隔代人数
     */
    public static void savePerNum3(int pNum3) {
        saveInt("pNum3", pNum3);
    }

    public static int getPerNum3() {
        return getInt("pNum3");
    }

    /**
     * 四级以后人数
     */
    public static void savePerNum4(int pNum4) {
        saveInt("pNum4", pNum4);
    }

    public static int getPerNum4() {
        return getInt("pNum4");
    }

    /**
     * 本月收益
     */
    public static void saveMonthProfit(String monthProfit) {
        saveString("monthProfit", monthProfit);
    }

    public static String getMonthProfit() {
        return getString("monthProfit");
    }

    /**
     * 累计拓展用户数
     */
    public static void saveExpandUser(String expandUser) {
        saveString("expandUser", expandUser);
    }

    public String getExpandUser() {
        return getString("expandUser");
    }

    /**
     * 升级收益
     */
    public static void saveUpProfitStr(String upProfitStr) {
        saveString("upProfitStr", upProfitStr);
    }

    public String getUpProfitStr() {
        return getString("upProfitStr");
    }

    /**
     * 提升收益是否显示  0 否 1是
     */
    public static void saveIsDisplay(String isDisplay) {
        saveString("isDisplay", isDisplay);
    }

    public static String getIsDisplay() {
        return getString("isDisplay");
    }

    /**
     * 当前费率
     */
    public static void saveCurrentFee(String currentFee) {
        saveString("currentFee", currentFee);
    }

    public static String getCurrentFee() {
        return getString("currentFee");
    }

    /**
     * 微信费率
     */
    public static void saveWechatFee(String weChatFee) {
        saveString("weChatFee", weChatFee);
    }

    public static String getWechatFee() {
        return getString("weChatFee");
    }

    /**
     * 支付宝
     */
    public static void saveAliFee(String aliFee) {
        saveString("aliFee", aliFee);
    }

    public static String getAliFee() {
        return getString("aliFee");
    }

    /**
     * 银联费率
     */
    public static void saveUnionFee(String unionFee) {
        saveString("unionFee", unionFee);
    }

    public static String getUnionFee() {
        return getString("unionFee");
    }

    /**
     * 固码费率
     */
    public static void saveSolidCodeFee(String solidCodeFee) {
        saveString("solidCodeFee", solidCodeFee);
    }

    public static String getSolidCodeFee() {
        return getString("solidCodeFee");
    }

    /**
     * 微信登录成功返回的 openid 授权用户唯一标识
     * 此处的openId保存的是加密字段，如要使用记得
     */
    public static void saveOpenId(String openId) {
        saveString("openId", openId);
    }

    public static String getOpenId() {
        return getString("openId");
    }

    /**
     * 当前用户等级
     */
    public static void saveLevel(String level) {
        saveString("level", level);
    }

    public String getLevel() {
        return getString("level");
    }

    /**
     * 当前用户等级描述
     */
    public static void saveLevelStr(String levelStr) {
        saveString("levelStr", levelStr);
    }

    public static String getLevelStr() {
        return getString("levelStr");
    }


    /**
     * 消息未读数
     */
    public static void saveUnReadNum(int unReadNum) {
        saveInt("unReadNum", unReadNum);
    }

    public static int getUnReadNum() {
        return getInt("unReadNum");
    }

    /**
     * 终端工作密钥
     */
    public static void saveWorKey(String worKey) {
        saveString("worKey", worKey);
    }

    public String getWorKey() {
        return getString("worKey");
    }

    /**
     * 用户类型
     */
    public static void saveMerType(String merType) {
        saveString("merType", merType);
    }

    public String getMerType() {
        return getString("merType");
    }

    /**
     * 审核进度0 待完善 1 待审核 2 鉴权通过 3 审核通过
     * 4 鉴权不通过 5 审核不通过 6基本信息已完善 7 照片已完善
     */
    public static void saveProcessAudit(String processAudit) {
        saveString("processAudit", processAudit);
    }

    public static String getProcessAudit() {
        return getString("processAudit");
    }


    /**
     * 是否是登录后完善 1是 0 不是
     */
    public static void saveIsContinueSubmit(String isContinueSubmit) {
        saveString("isContinueSubmit", isContinueSubmit);
    }

    public String getIsContinueSubmit() {
        return getString("isContinueSubmit");
    }


    /**
     * 注册手机号
     */
    public static void savePhoneNum(String phoneNum) {
        saveString("phoneNum", phoneNum);
    }

    public static String getPhoneNum() {
        return getString("phoneNum");
    }


    /**
     * 订单号
     */
    public static void saveOrderId(String orderId) {
        saveString("orderId", orderId);
    }

    public String getOrderId() {
        return getString("orderId");
    }


    /**
     * 城市名字
     */
    public static void saveCityName(String cityName) {
        saveString("cityName", cityName);
    }

    public String getCityName() {
        return getString("cityName");
    }


    /**
     * 城市Id
     */
    public static void saveCityId(String cityId) {
        saveString("cityId", cityId);
    }

    public String getCityId() {
        return getString("cityId");
    }

    /**
     * 推送channelId
     */
    public static void saveChannelId(String channelId) {
        saveString("channelId", channelId);
    }

    public String getChannelId() {
        return getString("channelId");
    }

}