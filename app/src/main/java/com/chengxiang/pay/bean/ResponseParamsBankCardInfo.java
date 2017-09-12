package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/26 18:05
 * @description: 我的银行卡响应信息
 */


public class ResponseParamsBankCardInfo extends BaseResponseParams {
    private String phoneNum;//手机号
    private ArrayList<BankInfoBean> carList;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public ArrayList<BankInfoBean> getCarList() {
        return carList;
    }

    public void setCarList(ArrayList<BankInfoBean> carList) {
        this.carList = carList;
    }
}
