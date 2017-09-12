package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 17:03
 * @description: 提现风控（9002）
 */


public class ResponseParamsWithdrawCashControl extends BaseResponseParams {

    private String fee;//手续费
    private String drawmaxamt;//每笔最高限额
    private String drawminamt;//每笔最低限额
    private String accountTime;//到帐时间

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDrawmaxamt() {
        return drawmaxamt;
    }

    public void setDrawmaxamt(String drawmaxamt) {
        this.drawmaxamt = drawmaxamt;
    }

    public String getDrawminamt() {
        return drawminamt;
    }

    public void setDrawminamt(String drawminamt) {
        this.drawminamt = drawminamt;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }
}
