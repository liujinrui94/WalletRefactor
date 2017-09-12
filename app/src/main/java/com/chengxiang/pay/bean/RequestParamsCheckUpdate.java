package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/29 12:50
 * @description: 检查版本更新请求
 */

public class RequestParamsCheckUpdate extends BaseRequestParams {

    private String osType;//设备类型 0 : android 1 : IOS 2 : WP
    private String userType;//应用类型 1 ： 商户版 2 ： 大众版
    private String appName;//应用名称

    public RequestParamsCheckUpdate() {
        super();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
