package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:05
 * @description: 银行对应省信息（1005）
 */

public class ResponseParamsProvinceInfo extends BaseResponseParams {
    private ArrayList<ProvinceInfo> provList;//省列表

    public ArrayList<ProvinceInfo> getProvList() {
        return provList;
    }

    public void setProvList(ArrayList<ProvinceInfo> provList) {
        this.provList = provList;
    }

    public class ProvinceInfo {
        private String provId;//省代码
        private String provName;//省名称

        private ProvinceInfo() {
        }

        public String getProvId() {
            return provId;
        }

        public void setProvId(String provId) {
            this.provId = provId;
        }

        public String getProvName() {
            return provName;
        }

        public void setProvName(String provName) {
            this.provName = provName;
        }
    }
}
