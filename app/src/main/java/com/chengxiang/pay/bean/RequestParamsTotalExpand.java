package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 18:50
 * @description: 累计拓展人请求（9005）
 */


public class RequestParamsTotalExpand extends BaseRequestParams {
    private String phoneNum;//手机号
    private String type;//类型0:全部，1:直接，2:间接,3:隔代，4:四级以后
    private String currentPage;//请求页码（从1开始）
    private String pageSize;//每页请求数据数

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
