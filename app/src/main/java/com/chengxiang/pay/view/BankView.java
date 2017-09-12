package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.bean.ResponseParamsBranchInfo;
import com.chengxiang.pay.bean.ResponseParamsCityInfo;
import com.chengxiang.pay.bean.ResponseParamsHeadOfficeInfo;
import com.chengxiang.pay.bean.ResponseParamsProvinceInfo;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:19
 * @description: 银行相关请求
 */


public interface BankView {

    /**
     * 总行
     */
    interface HeadOfficeView extends BaseView {

        String getHeadOfficeJsonString();

        void headOfficeResponse(ArrayList<ResponseParamsHeadOfficeInfo.BankInfo> bankList);
    }

    /**
     * 银行所在省
     */
    interface ProvinceView extends BaseView {

        String getProvinceJsonString();

        void provinceResponse(ArrayList<ResponseParamsProvinceInfo.ProvinceInfo> provList);
    }

    /**
     * 银行所在市
     */
    interface CityView extends BaseView {

        String getCityJsonString();

        void cityResponse(ArrayList<ResponseParamsCityInfo.CityInfo> cityList);
    }

    /**
     * 银行对应支行信息
     */
    interface BranchView extends BaseView {

        String getBranchJsonString();

        void branchResponse(ArrayList<ResponseParamsBranchInfo.PmsBankInfo> pmsBankList);
    }

    /**
     * 获取我的银行卡相关信息
     */
    interface BankCardView extends BaseView {

        String getBankCardJsonString();

        void bankCardResponse(ArrayList<BankInfoBean> bankList);
    }

    /**
     * 删除银行卡
     */
    interface DeleteBankCardView extends BaseView {

        String getDeleteBankCardJsonString();

        void deleteBankCardResponse();
    }

    /**
     * 添加信用卡
     */
    interface AddBankCardView extends BaseView {

        String getAddBankCardJsonString();

        void addBankCardResponse();
    }


}