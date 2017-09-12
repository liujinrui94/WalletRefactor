package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/9 19:26
 * @description: 收益汇总
 */


public class ProfitSummaryBean {

    private String wxTotal;//微信总额
    private String aliTotal;//支付宝总额
    private String fixTotal;//固码总额
    private String unTotal;//银联总额
    private String inType;//以上各个展示类型（年/月/周）

    public String getWxTotal() {
        return wxTotal;
    }

    public void setWxTotal(String wxTotal) {
        this.wxTotal = wxTotal;
    }

    public String getAliTotal() {
        return aliTotal;
    }

    public void setAliTotal(String aliTotal) {
        this.aliTotal = aliTotal;
    }

    public String getFixTotal() {
        return fixTotal;
    }

    public void setFixTotal(String fixTotal) {
        this.fixTotal = fixTotal;
    }

    public String getUnTotal() {
        return unTotal;
    }

    public void setUnTotal(String unTotal) {
        this.unTotal = unTotal;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }
}
