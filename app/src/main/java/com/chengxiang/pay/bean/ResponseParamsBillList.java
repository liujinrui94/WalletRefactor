package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/28 22:57
 * @description: 账单列表返回
 */


public class ResponseParamsBillList extends BaseResponseParams {
    private String totalAmt;
    private String pageTotal;
    private String currentPage;
    private ArrayList<BillBean> payForList;

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<BillBean> getPayForList() {
        return payForList;
    }

    public void setPayForList(ArrayList<BillBean> payForList) {
        this.payForList = payForList;
    }
}
