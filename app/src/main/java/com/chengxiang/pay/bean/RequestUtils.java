package com.chengxiang.pay.bean;

import android.text.TextUtils;

import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.utils.InterfaceUtil;

public class RequestUtils {

    /**
     * 发起请求，生成流水号
     *
     * @param reqBean 获取公共数据
     */
    public static void initRequestBean(BaseRequestParams reqBean, EncryptManager enManger, String funCode) {
        reqBean.setAppType(BaseBean.getAppType());
        reqBean.setAppVersion(BaseBean.getAppVersion());
        reqBean.setAppOs(BaseBean.getAppOs());
        reqBean.setDeviceSN(BaseBean.getDeviceSN());
        reqBean.setIMEI(BaseBean.getImei());
        reqBean.setIMSI(BaseBean.getImsi());
        reqBean.setDeviceSN(BaseBean.getDeviceSN());
        reqBean.setDeviceType(BaseBean.getDeviceType());
        reqBean.setMAC(BaseBean.getMac());
        reqBean.setIP(BaseBean.getIp());
        reqBean.setOrgId(BaseBean.getOrgId());
        // 生成业务流水号
        String seq = InterfaceUtil.getSeq();
        reqBean.setSeq(seq);
        if (!TextUtils.isEmpty(BaseBean.getAccountId())) {
            reqBean.setTaccountId(enManger.getEncryptDES(BaseBean.getAccountId()));
        }
        reqBean.setMobKey(enManger.getMobKey());
        reqBean.setFunCode(funCode);
    }
}
