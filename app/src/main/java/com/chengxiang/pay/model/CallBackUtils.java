package com.chengxiang.pay.model;

import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.bean.CashRegisterBean;
import com.chengxiang.pay.bean.ExpandUserBean;
import com.chengxiang.pay.bean.MessageBean;
import com.chengxiang.pay.bean.MonthProfitUserBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.bean.ProfitDetailBean;
import com.chengxiang.pay.bean.ResponseParamsBranchInfo;
import com.chengxiang.pay.bean.ResponseParamsCityInfo;
import com.chengxiang.pay.bean.ResponseParamsHeadOfficeInfo;
import com.chengxiang.pay.bean.ResponseParamsLogin;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.bean.ResponseParamsProvinceInfo;
import com.chengxiang.pay.bean.ResponseParamsUpgradeCondition;
import com.chengxiang.pay.bean.SlideImageBean;
import com.chengxiang.pay.framework.base.BaseCallBack;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/5 15:23
 * @description: 页面回调
 */


public interface CallBackUtils {

    /**
     * 用户登录回调
     */
    interface UserLoginCallBack extends BaseCallBack {
        void startActivity(ResponseParamsLogin info);
    }

    /**
     * 获取验证码
     */
    interface UserGetVerifiedCodeCallBack extends BaseCallBack {
        void CallVerifiedCode(String isUser);
    }

    /**
     * 重置密码回调
     */
    interface UserResetPwdCallBack extends BaseCallBack {
        void Response();
    }

    /**
     * 用户注册回调
     */
    interface UserRegisterCallBack extends BaseCallBack {
        void callRegisterResponse(String intentType);
    }

    /**
     * 应用检测更新
     */
    interface UpgradeCallBack extends BaseCallBack {
        void notUpgrade(String retMsg);

        /**
         * 调用接口返回
         *
         * @param isUpVersion 0需更新 ;1需强制更新
         * @param versionDesc 更新内容
         * @param downUrl     下载地址
         */
        void callResponse(String isUpVersion, String versionDesc, String downUrl);
    }

    /**
     * 下载新版本apk
     */
    interface DownloadApkCallBack {


        /**
         * 下载返回进度
         *
         * @param progress 进度
         */
        void callProgress(float progress);

        /**
         * 下载完成
         *
         * @param saveAddress 下载后地址
         */
        void callResponse(String saveAddress);

        /**
         * 网络错误回调
         */
        void OnNetError();

    }

    /**
     * 我的信息
     */
    interface MineInfoCallBack extends BaseCallBack {

        void mineInfoResponse();
    }

    /**
     * 查询用户信息返回
     */
    interface UserInfoCallBack extends BaseCallBack {

        void userInfoResponse();
    }

    /**
     * 完善用户图片信息
     */
    interface PerfectPhotoDataCallBack extends BaseCallBack {

        void perfectPhotoDataResponse();
    }

    /**
     * 完善用户银行卡等基本信息
     */
    interface PerfectBankDataCallBack extends BaseCallBack {

        void perfectBankDataResponse();
    }

    /**
     * 上传文件
     */
    interface UploadFileCallBack extends BaseCallBack {

        void uploadFileResponse(String fileName);
    }

    /**
     * 银行总行
     */
    interface HeadOfficeCallBack extends BaseCallBack {

        void headOfficeResponse(ArrayList<ResponseParamsHeadOfficeInfo.BankInfo> bankList);
    }

    /**
     * 总行对应省
     */
    interface ProvinceCallBack extends BaseCallBack {

        void provinceResponse(ArrayList<ResponseParamsProvinceInfo.ProvinceInfo> provList);
    }

    /**
     * 总行对应省对应市
     */
    interface CityCallBack extends BaseCallBack {

        void cityResponse(ArrayList<ResponseParamsCityInfo.CityInfo> cityList);
    }

    /**
     * 支行信息
     */
    interface BranchCallBack extends BaseCallBack {

        void branchResponse(ArrayList<ResponseParamsBranchInfo.PmsBankInfo> pmsBankList);
    }

    /**
     * 获取我的银行卡相关信息
     */
    interface BankCardCallBack extends BaseCallBack {

        void bankCardResponse(ArrayList<BankInfoBean> carList);
    }

    /**
     * 删除银行卡
     */
    interface DeleteBankCardCallBack extends BaseCallBack {

        void deleteBankCardResponse();
    }

    /**
     * 添加银行卡
     */
    interface AddBankCardCallBack extends BaseCallBack {

        void addBankCardResponse();
    }

    /**
     * 用户升级条件
     */
    interface UpgradeConditionCallBack extends BaseCallBack {
        void upgradeConditionResponse(ResponseParamsUpgradeCondition responseParamsUpgradeCondition);
    }

    /**
     * 用户支付升级
     */
    interface PaymentUpgradeCallBack extends BaseCallBack {
        void paymentUpgradeResponse(String url);
    }

    /**
     * 累计拓展人（9005）
     */
    interface TotalExpandCallBack extends BaseCallBack {
        void totalExpandResponse(String totalPage, ArrayList<ExpandUserBean> expandUserBeanArrayList);
    }

    /**
     * 获取订单列表
     */
    interface BillListCallBack extends BaseCallBack {
        void billListResponse(String totalAmt, String pageTotal, ArrayList<BillBean> billLists);
    }

    /**
     * 二维码收款(微信、支付宝)
     */
    interface CodeReceiveCallBack extends BaseCallBack {
        void codeReceiveResponse(String url);
    }

    /**
     * 固码
     */
    interface GuMaReceiveCallBack extends BaseCallBack {
        void guMaReceiveResponse(String url);
    }

    /**
     * 提现记录
     */
    interface WithdrawCashRecordCallBack extends BaseCallBack {
        void withdrawCashRecordResponse(String pageTotal, ArrayList<CashRegisterBean> cashLists);
    }

    /**
     * 本月收益
     */
    interface MonthProfitCallBack extends BaseCallBack {
        void monthProfitResponse(String pageTotal, ArrayList<MonthProfitUserBean> amtList);
    }

    /**
     * 收益列表明细(9008)
     */
    interface ProfitListDetailCallBack extends BaseCallBack {
        void profitListDetailResponse(ArrayList<ProfitDetailBean> amtList);
    }

    /**
     * 获取消息列表
     */
    interface MessageListCallBack extends BaseCallBack {
        void messageListResponse(ArrayList<MessageBean> messageList);
    }

    /**
     * 消息详情（调用后消息标记为已读）
     */
    interface MessageDetailCallBack extends BaseCallBack {
        void messageDetailResponse();
    }

    /**
     * 编辑消息（已读、删除）
     */
    interface MessageModifyCallBack extends BaseCallBack {
        void messageModifyResponse();
    }

    /**
     * 轮播图
     */
    interface SlideShowCallBack extends BaseCallBack {
        void slideShowResponse(ArrayList<SlideImageBean> newList);
    }

    /**
     * 收益界面
     */
    interface ProfitCallBack extends BaseCallBack {
        void profitResponse(ResponseParamsProfitInfo mResponseParamsProfitInfo);
    }

    /**
     * 提现风控
     */
    interface WithdrawCashControlCallBack extends BaseCallBack {
        /**
         * @param fee         手续费
         * @param minAmount   最低金额
         * @param maxAmount   最高金额
         * @param accountTime 预计到账时间
         */
        void withdrawCashControlResponse(String fee, String minAmount, String maxAmount, String accountTime);
    }

    /**
     * 提现
     */
    interface WithdrawCashCallBack extends BaseCallBack {
        void withdrawCashResponse();
    }

    /**
     * 首页公共信息
     */
    interface HomePageCallBack extends BaseCallBack {
        void homePageResponse(ArrayList<PaymentMethodBean> paymentMethodBeanArrayList);
    }

}
