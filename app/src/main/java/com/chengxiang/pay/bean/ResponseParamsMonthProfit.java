package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 19:24
 * @description: 本月收益返回数据（9007）
 */


public class ResponseParamsMonthProfit extends BaseResponseParams {

    private ArrayList<MonthProfitUserBean> amtList;//本月收益list
    private String pageTotal;//总页数
    private String currentPage;//当前页码

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

    public ArrayList<MonthProfitUserBean> getAmtList() {
        return amtList;
    }

    public void setAmtList(ArrayList<MonthProfitUserBean> amtList) {
        this.amtList = amtList;
    }


}
