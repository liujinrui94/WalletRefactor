package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:17
 * @description: 银行支行信息
 */

public class ResponseParamsBranchInfo extends BaseResponseParams {
    private ArrayList<PmsBankInfo> pmsBankList;//支行列表

    public ArrayList<PmsBankInfo> getPmsBankList() {
        return pmsBankList;
    }

    public void setPmsBankList(ArrayList<PmsBankInfo> pmsBankList) {
        this.pmsBankList = pmsBankList;
    }

    public PmsBankInfo getPmsBankInfo() {
        return new PmsBankInfo();
    }

    public class PmsBankInfo {
        private String interBankNo;//联行号
        private String pmsBankNm;//支行名称

        private PmsBankInfo() {
        }

        public String getInterBankNo() {
            return interBankNo;
        }

        public void setInterBankNo(String interBankNo) {
            this.interBankNo = interBankNo;
        }

        public String getPmsBankNm() {
            return pmsBankNm;
        }

        public void setPmsBankNm(String pmsBankNm) {
            this.pmsBankNm = pmsBankNm;
        }
    }
}
