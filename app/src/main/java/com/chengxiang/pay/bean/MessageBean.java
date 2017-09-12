package com.chengxiang.pay.bean;

import java.io.Serializable;

public class MessageBean implements Serializable {
    private String content;//消息详细
    private String id;//消息id
    private String title;//消息标题
    private String dateTime;//消息时间
    private String unreadMsgCount;//未读消息数
    private String isRead;//是否读（0 已读  1未读）
    private String isTop;//是否置顶（0 不置顶  1 置顶）
    private String ISTOP;//额外字段


    public String getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(String unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public String getISTOP() {
        return ISTOP;
    }

    public void setISTOPr(String ISTOP) {
        this.ISTOP = ISTOP;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
