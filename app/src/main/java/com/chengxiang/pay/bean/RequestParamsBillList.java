package com.chengxiang.pay.bean;

/**
 * 账单列表请求
 * author duronggang on 2016/12/27.
 * email duronggang@buybal.com
 */

public class RequestParamsBillList extends BaseRequestParams {

    private String phoneNum;//用户注册手机号
    private String date;//账单筛选时间范围
    private String state;//账单状态（0 全部  1 成功）
    private String currentPage;//请求页码（从1开始）
    private String pageSize;//每页请求数据数

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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
}
