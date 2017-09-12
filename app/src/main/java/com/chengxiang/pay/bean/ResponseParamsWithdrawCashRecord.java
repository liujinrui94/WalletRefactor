package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 16:34
 * @description: 提现记录响应(9004)
 */


public class ResponseParamsWithdrawCashRecord extends BaseResponseParams {
    private String pageTotal;
    private String currentPage;
    private ArrayList<CashRegisterBean> liqList;

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

    public ArrayList<CashRegisterBean> getLiqList() {
        return liqList;
    }

    public void setLiqList(ArrayList<CashRegisterBean> liqList) {
        this.liqList = liqList;
    }


}
