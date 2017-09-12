package com.chengxiang.pay.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chengxiang.pay.activity.GatheringResultActivity;
import com.chengxiang.pay.activity.LoginActivity;
import com.chengxiang.pay.activity.MessageActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.JGPushBean;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/15 10:42
 * @description: 极光推送接收服务
 */


public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;
    private JGPushBean jgPushBean;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + StringUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");
            if (InterfaceUtil.isAppRunning(context)) {//程序在运行
                if (BaseBean.getIsLogin()) {//用户已登录
                    openNotification(context, bundle);
                } else {//用户未登录
                    Intent intentLogin = new Intent(context, LoginActivity.class);
                    context.startActivity(intentLogin);
                }
            } else {
                //打开app
                Intent intentOpen = context.getPackageManager()
                        .getLaunchIntentForPackage(context.getPackageName());
                context.startActivity(intent);
            }
        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Logger.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Logger.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Logger.d(TAG, "extras : " + extras);
        if (InterfaceUtil.isAppRunning(context) && BaseBean.getIsLogin()) {//程序在运行并且已登录
            if (null != extras) {
                jgPushBean = new Gson().fromJson(extras, JGPushBean.class);
                String type = jgPushBean.getType();
                switch (type) {
                    case "1"://用户升级
                        break;
                    case "2"://收款结果
                        Intent intent = new Intent(context, GatheringResultActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("jgPush", jgPushBean);
                        intent.putExtra("isSpeech", true);
                        context.startActivity(intent);
                        break;
                    case "3"://推荐用户通知
                        break;
                }
            }
        }
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (extras != null) {
            jgPushBean = new Gson().fromJson(extras, JGPushBean.class);
        }
        String type = jgPushBean.getType();
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("jgPush", jgPushBean);
        intent.putExtra("isSpeech", false);
        switch (type) {
            case "1"://用户升级
                intent.setClass(context, MessageActivity.class);
                break;
            case "2"://收款结果
                intent.setClass(context, GatheringResultActivity.class);
                break;
            case "3"://推荐用户通知
                intent.setClass(context, MessageActivity.class);
                break;
        }
        context.startActivity(intent);
    }


}
