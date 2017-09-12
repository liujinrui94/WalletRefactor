
package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:00
 * @description: 银行总行信息（1007）
 */

public class ResponseParamsHeadOfficeInfo extends BaseResponseParams {
    private ArrayList<BankInfo> bankList;//银行裂变

    public ArrayList<BankInfo> getBankList() {
        return bankList;
    }

    public void setBankList(ArrayList<BankInfo> bankList) {
        this.bankList = bankList;
    }

    public class BankInfo {
        private String bankId;//银行代码
        private String bankName;//银行名称

        private BankInfo() {
        }

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
