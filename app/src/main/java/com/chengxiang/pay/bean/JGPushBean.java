package com.chengxiang.pay.bean;

import java.io.Serializable;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/15 15:21
 * @description: 极光推送
 */

public class JGPushBean implements Serializable {

    private String type;//类型
    private String amt;//金额
    private String orderTime;//时间
    private String state;//状态

    private String msgId;//消息ID
    private String unreadCount;//未读数

    public String getType() {
        return type;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "JPModel [type=" + type + ", amt=" + amt + ", orderTime="
                + orderTime + ", state=" + state + "]";
    }

}
