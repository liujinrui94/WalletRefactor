package com.chengxiang.pay.framework.base;

/**
 * 所有回调层基类
 * 2016/10/25.
 */

public interface BaseCallBack {

    /**
     * 网络错误回调
     */
    void OnNetError();

    /**
     * @param msg 参数错误
     */
    void CodeError(String msg);


}
