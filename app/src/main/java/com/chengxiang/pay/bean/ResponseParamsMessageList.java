package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/1 9:19
 * @description: 消息响应参数
 */

public class ResponseParamsMessageList extends BaseResponseParams {


    private String totalnews;//消息总数
    private ArrayList<MessageBean> list;//消息列表

    public ArrayList<MessageBean> getList() {
        return list;
    }

    public void setList(ArrayList<MessageBean> list) {
        this.list = list;
    }

    public String getTotalnews() {
        return totalnews;
    }

    public void setTotalnews(String totalnews) {
        this.totalnews = totalnews;
    }
}
