package com.chengxiang.pay.framework.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.chengxiang.pay.activity.LoginActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.iflytek.cloud.SpeechUtility;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {

    public static Context applicationContext;

    public static EncryptManager encryptManager;

    private static BaseApplication applicationInstance;

    public Stack<BaseActivity> allActivity = new Stack<>();

    public static BaseApplication getInstance() {
        return applicationInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (applicationInstance == null) {
            applicationInstance = this;
        }
        applicationContext = this;
        //初始化加密
        initEncryptManager();
        //初始化数据
        initBaseBean();
        //初始化logger
        initLogger();
        //初始化腾讯X5
        initTBS();

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //讯飞语音
        SpeechUtility.createUtility(BaseApplication.this, Constant.XF_ID);


    }

    private void initEncryptManager() {
        encryptManager = EncryptManager.getEncryptManager();
        try {
            encryptManager.initEncrypt();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("加密管理初始化异常~~~");
        }
    }

    private void initTBS() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.d(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否显示线程信息。默认为true
                .methodCount(5)         // 要显示多少个方法行。默认值为2
                .methodOffset(0)        //  隐藏内部方法调用以抵消。默认值为5
                .tag("ChengXiangWallet")   //每个日志的全局标记。默认pretty_logger
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void initBaseBean() {
        // 初始化全局变量
        BaseBean.saveAppType(InterfaceUtil.getAppType());
        BaseBean.saveAppOs(InterfaceUtil.getAppOs());
        BaseBean.saveAppVersion(InterfaceUtil.getAppVersion(this));
        BaseBean.saveDeviceSN(InterfaceUtil.getDeviceSN(this));
        BaseBean.saveDeviceType(InterfaceUtil.getDeviceType());
        BaseBean.saveMac(InterfaceUtil.getMAC(this));
        BaseBean.saveIp(InterfaceUtil.getLocalIpAddress(this));
        BaseBean.saveOrgId(Constant.orgId);
        BaseBean.saveBaseUrl(Constant.MOBILE_HOST);
    }


    /**
     * 程序终止
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        BaseBean.clear();//清除所有保存的个人数据
        finishAllActivity();
    }

    /**
     * 安全退出
     */
    public static void safeExit() {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("AccountPassword", Context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();//清空所有保存的登录状态
        getInstance().finishActivities();
        BaseBean.saveIsLogin(false);

    }

    //单例模式添加activity
    public void addActivity(BaseActivity activity) {
        if (allActivity == null) {
            allActivity = new Stack<>();
        }
        allActivity.add(activity);
    }

    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            allActivity.remove(activity);
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
    }

    public void finishActivities() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (allActivity.size() == i) {
                break;
            }
            if (null != allActivity.get(i) && !(allActivity.get(i) instanceof LoginActivity)) {
                allActivity.get(i).finish();
            }

        }
        allActivity.clear();
    }

    public void AppExit() {
        try {
            finishAllActivity();
            BaseBean.saveIsLogin(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
