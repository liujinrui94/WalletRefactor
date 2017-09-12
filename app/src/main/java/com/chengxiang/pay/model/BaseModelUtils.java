package com.chengxiang.pay.model;

import java.io.File;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/5 15:35
 * @description: Model层基类
 */


interface BaseModelUtils {

    /**
     * 用户登录
     */
    interface UserLogin {
        void postUserLogin(String loginJsonString, CallBackUtils.UserLoginCallBack callBack);
    }

    /**
     * 获取验证码
     */
    interface UserGetVerifiedCode {
        void postUserGetVerifiedCode(String verifiedCodeJsonString, CallBackUtils.UserGetVerifiedCodeCallBack callBack);
    }

    /**
     * 密码重置
     */
    interface UserResetPwd {

        void postUserResetPwd(String getUserResetPasswordJsonString, CallBackUtils.UserResetPwdCallBack callBack);

    }


    /**
     * 用户注册
     */
    interface UserRegister {
        void postUserRegister(String registerJsonString, CallBackUtils.UserRegisterCallBack callBack);
    }

    /**
     * 检测版本更新
     */
    interface Upgrade {
        void postCheckUpgrade(String requestString, CallBackUtils.UpgradeCallBack callBack);
    }
    /**
     * 下载新版本apk
     */
    interface DownloadApk {

        /**
         * @param downloadUrl 下载地址
         * @param fileAddress 下载后文件的保存地址
         * @param name        下载后文件的名字
         * @param callBack    返回
         */
        void postDownloadApk(String downloadUrl, String fileAddress, String name, CallBackUtils.DownloadApkCallBack callBack);
    }
    /**
     * 我的信息
     */
    interface MineInfo {


        void postMineInfo(String mineInfoJsonString, CallBackUtils.MineInfoCallBack callBack);
    }
    /**
     * 用户信息查询
     */
    interface UserInfo {

        void postUserInfo(String userInfoJsonString, CallBackUtils.UserInfoCallBack callBack);
    }
    /**
     * 完善用户照片信息
     */
    interface PerfectPhotoData {

        void postPerfectPhotoData(String perfectPhotoDataJsonString, CallBackUtils.PerfectPhotoDataCallBack callBack);
    }
    /**
     * 完善用户银行卡等基本信息
     */
    interface PerfectBankData {

        void postPerfectBankData(String perfectBankDataJsonString, CallBackUtils.PerfectBankDataCallBack callBack);
    }
    /**
     * 上传文件
     */
    interface UploadFile {

        void postUploadFile(String uploadFileJsonString, File file, CallBackUtils.UploadFileCallBack callBack);
    }
    /**
     * 银行总行信息
     */
    interface HeadOffice {

        void postHeadOffice(String headOfficeJsonString, CallBackUtils.HeadOfficeCallBack callBack);
    }
    /**
     * 总行对应省
     */
    interface Province {

        void postProvince(String provinceJsonString, CallBackUtils.ProvinceCallBack callBack);
    }
    /**
     * 总行对应省对应市
     */
    interface City {

        void postCity(String cityJsonString, CallBackUtils.CityCallBack callBack);
    }
    /**
     * 支行信息
     */
    interface Branch {

        void postBranch(String branchJsonString, CallBackUtils.BranchCallBack callBack);
    }
    /**
     * 获取我的银行卡相关信息
     */
    interface BankCard {

        void postBankCard(String bankCardJsonString, CallBackUtils.BankCardCallBack callBack);
    }

    /**
     * 删除银行卡
     */
    interface DeleteBankCard {

        void postDeleteBankCard(String deleteBankCardJsonString, CallBackUtils.DeleteBankCardCallBack callBack);
    }
    /**
     * 添加银行卡
     */
    interface AddBankCard {

        void postAddBankCard(String addBankCardJsonString, CallBackUtils.AddBankCardCallBack callBack);
    }
    /**
     * 用户升级条件
     */
    interface UpgradeCondition {
        void postUpgradeCondition(String requestString, CallBackUtils.UpgradeConditionCallBack callBack);
    }
    /**
     * 用户支付升级
     */
    interface PaymentUpgrade {
        void postPaymentUpgrade(String requestString, CallBackUtils.PaymentUpgradeCallBack callBack);
    }
    /**
     * 累计拓展人（9005）
     */
    interface TotalExpand {
        void postTotalExpand(String requestString, CallBackUtils.TotalExpandCallBack callBack);
    }
    /**
     * 账单列表
     */
    interface BillList {
        void postBillList(String requestString, CallBackUtils.BillListCallBack callBack);
    }
    /**
     * 二维码收款(微信、支付宝)
     */
    interface CodeReceive {
        void postCodeReceive(String requestString, CallBackUtils.CodeReceiveCallBack callBack);
    }
    /**
     * 固码
     */
    interface GuMaReceive {
        void postGuMaReceive(String requestString, CallBackUtils.GuMaReceiveCallBack callBack);
    }

    /**
     * 提现记录
     */
    interface WithdrawCashRecord {
        void postWithdrawCashRecord(String requestString, CallBackUtils.WithdrawCashRecordCallBack callBack);
    }

    /**
     * 本月收益
     */
    interface MonthProfit {
        void postMonthProfit(String requestString, CallBackUtils.MonthProfitCallBack callBack);
    }
    /**
     * 收益列表明细(9008)
     */
    interface ProfitListDetail {
        void postProfitListDetail(String requestString, CallBackUtils.ProfitListDetailCallBack callBack);
    }
    /**
     * 消息列表
     */
    interface MessageList {
        void postMessageList(String requestString, CallBackUtils.MessageListCallBack callBack);
    }
    /**
     * 消息详情（调用后消息标记为已读）
     */
    interface MessageDetail {
        void postMessageDetail(String requestString, CallBackUtils.MessageDetailCallBack callBack);
    }
    /**
     * 编辑消息（已读、删除）
     */
    interface MessageModify {
        void postMessageModify(String requestString, CallBackUtils.MessageModifyCallBack callBack);
    }

    /**
     * 首页轮播图
     */
    interface SlideShow {
        void postSlideShow(String requestString, CallBackUtils.SlideShowCallBack callBack);
    }
    /**
     * 收益界面
     */
    interface Profit {
        void profit(String requestString, CallBackUtils.ProfitCallBack callBack);
    }
    /**
     * 提现风控
     */
    interface WithdrawCashControl {
        void postWithdrawCashControl(String requestString, CallBackUtils.WithdrawCashControlCallBack callBack);
    }
    /**
     * 提现
     */
    interface WithdrawCash {
        void postWithdrawCash(String requestString, CallBackUtils.WithdrawCashCallBack callBack);
    }
    /**
     * 首页公共信息
     */
    interface HomePage {
        void postHomePage(String requestString, CallBackUtils.HomePageCallBack callBack);
    }

}
