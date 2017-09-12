package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 16:26
 * @description: 提现记录查询（9004）
 */


public class RequestParamsWithdrawCashRecord extends BaseRequestParams {
    private String phoneNum;//用户注册手机号
    private String dateType;//日期类型（年/月）0:月；1:年
    private String state;//状态
    private String currentPage;//请求页码（从1开始）
    private String pageSize;//每页请求数据数

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
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
