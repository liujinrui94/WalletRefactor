
package com.chengxiang.pay.framework.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 16:13
 * @description: BaseBean获取常用数据
 */

public class InterfaceUtil {
    private static int seq = 0;

    /**
     * 私有构造器
     */
    private InterfaceUtil() {
    }

    /**
     * 得到流水号
     *
     * @return 流水号
     */
    public static synchronized String getSeq() {
        String dateStr;
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        dateStr = sdf.format(rightNow.getTime());
        seq = seq + 1;
        return dateStr + String.format("%06d", seq);
    }

    /**
     * @return 应用类型 1、大众版
     */
    public static String getAppType() {
        return "1";
    }

    /**
     * @return 应用系统信息 Android:7.1.1
     */
    public static String getAppOs() {
        return "Android:" + Build.VERSION.RELEASE;
    }

    /**
     * @param context 上下文
     * @return 设备软件版本
     */
    @SuppressWarnings("unused")
    public static String getSoftVersion(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String softVersion = tm.getDeviceSoftwareVersion();
        return softVersion == null ? null : softVersion;
    }

    /**
     * @param context 上下文
     * @return 当前应用版本
     */
    public static String getAppVersion(Context context) {
        String appVersion = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appVersion = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.d("获取应用版本信息异常" + e);
        }
        return appVersion == null ? null : appVersion;
    }

    /**
     * @param context 上下文
     * @return 设备IMSI
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        return IMSI == null ? null : IMSI;
    }

    /**
     * @param context 上下文
     * @return 设备IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = tm.getDeviceId();
        return IMEI == null ? null : IMEI;
    }

    /**
     * @param context 上下文
     * @return 设备序列号
     */
    public static String getDeviceSN(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * @return 设备类型(meizu_mx5)
     */
    public static String getDeviceType() {
        return Build.PRODUCT;
    }

    /**
     * @param context 上下文
     * @return 网卡mac
     */
    public static String getMAC(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * @return 应用的v4 IP地址
     */
    public static String getLocalIpAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 判断SD卡是否存在
     */
    public static boolean sdState() {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            ToastUtils.showLongToast("内存卡不存在,请插入内存卡");
            return false;
        }
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * @return 正在运行activity名称
     */
    public static String getRunningActivityName(Context mContext) {
        String contextString = mContext.toString();
        return contextString.substring(0, contextString.indexOf("@"));
    }

    /**
     * @return 设置背景显示亮度
     */
    public static void setBackgroundColor(Activity activity, float fl) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = fl;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 应用是否运行
     */
    public static boolean isAppRunning(Context context) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName())
                    && info.baseActivity.getPackageName().equals(
                    context.getPackageName())) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 应用是否已安装
     */
    public static boolean isAvailable(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
}
