package com.chengxiang.pay.bean;
/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/23 19:02
 * @description: 注册获取短信验证码返回
 */


public class ResponseParamsVerifiedCode extends BaseResponseParams {
    private String isUser;

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }
}
