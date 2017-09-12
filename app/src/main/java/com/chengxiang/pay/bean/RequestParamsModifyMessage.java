package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/2 19:35
 * @description: 编辑消息请求
 */

public class RequestParamsModifyMessage extends BaseRequestParams {

    private String idlist;//消息id消息唯一标识多个用竖线“|”分隔
    private String type;//操作类型 1 删除 2 标记为已读
    private String phoneNum;//手机号码

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getIdlist() {
        return idlist;
    }

    public void setIdlist(String idlist) {
        this.idlist = idlist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
