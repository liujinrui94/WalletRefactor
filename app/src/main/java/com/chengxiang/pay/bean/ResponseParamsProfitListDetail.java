package com.chengxiang.pay.bean;


import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/13 21:20
 * @description: 收益列表详情
 */


public class ResponseParamsProfitListDetail extends BaseResponseParams {

    private ArrayList<ProfitDetailBean> amtList;

    public ArrayList getAmtList() {
        return amtList;
    }

    public void setAmtList(ArrayList amtList) {
        this.amtList = amtList;
    }

}
