package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/20 12:08
 * @description: 银行省对应的市
 */

public class ResponseParamsCityInfo extends BaseResponseParams {
    private ArrayList<CityInfo> cityList;//市列表

    public ArrayList<CityInfo> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityInfo> cityList) {
        this.cityList = cityList;
    }

    public class CityInfo {
        private String cityId;//市代码
        private String cityName;//市名称

        public CityInfo() {
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
