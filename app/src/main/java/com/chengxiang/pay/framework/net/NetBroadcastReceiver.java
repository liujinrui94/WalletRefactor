package com.chengxiang.pay.framework.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.chengxiang.pay.framework.utils.NetUtil;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/23 10:53
 * @description: 网络状态广播
 */


public class NetBroadcastReceiver extends BroadcastReceiver {
    private NetEventInterface netEvent;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //检查网络状态的类型
            int netWorkState = NetUtil.getNetWorkState(context);
            if (netEvent != null)
                // 接口回传网络状态的类型
                netEvent.onNetChange(netWorkState);
        }
    }

    public void setNetEvent(NetEventInterface netEvent) {
        this.netEvent = netEvent;
    }

}
