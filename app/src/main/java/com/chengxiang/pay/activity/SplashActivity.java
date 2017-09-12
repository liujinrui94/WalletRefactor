package com.chengxiang.pay.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsCheckUpdate;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Common;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.PermissionsUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.BaseDialog;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.chengxiang.pay.presenter.UpgradePresenter;
import com.chengxiang.pay.view.UpgradeView;
import com.google.gson.Gson;

import java.io.File;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 17:59
 * @description: 欢迎页
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SplashActivity extends BaseActivity implements UpgradeView.CheckUpgradeView, UpgradeView.DownloadApkView {
    private Context context;
    private ProgressBar downloadProgressBar;
    private TextView downloadTextView;
    private BaseDialog downloadDialog;
    private View view;
    private String downUrl;//安装包下载地址

    private final String[] needPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE};
    private static final int PERMISSIONS_REQUEST_APPLY = 1;
    private final String[] needStoragePermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSIONS_REQUEST_STORAGE = 2;

    private static final int RC_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_splash, null);
        context = this;
        setContentView(view);
        applyPermission();
    }

    //集中申请权限
    private void applyPermission() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isApply = sharedPreferences.getBoolean(Common.PERMISSION, false);
        if (isApply) {
            into();
        } else {
            if (PermissionsUtil.hasPermissions(this, needPermissions)) {
                into();
            } else {
                PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_storage)
                        , PERMISSIONS_REQUEST_APPLY, needPermissions);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Common.PERMISSION, true);
            editor.apply();
        }
    }

    // 进入主程序的方法
    private void into() {
        // 如果网络可用则判断是否第一次进入，如果是第一次则进入欢迎界面
        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);//透明度从0~1
        alphaAnimation.setDuration(2000);//持续时间
        // 给view设置动画效果
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            // 这里监听动画结束的动作，在动画结束的时候开启一个线程，这个线程中绑定一个Handler,并
            // 在这个Handler中调用goHome方法，而通过postDelayed方法使这个方法延迟500毫秒执行，达到
            // 达到持续显示第一屏500毫秒的效果
            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressShow();
                        new UpgradePresenter.Upgrade((UpgradeView.CheckUpgradeView) context).PostCheckUpgrade();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void DownloadProgress(final float progressNum) {
        downloadProgressBar.post(new Runnable() {
            @Override
            public void run() {
                int progress = (int) (progressNum * 100);
                downloadProgressBar.setProgress(progress);
                downloadTextView.setText("已为您下载了：" + progress + "%");
                if (100 == progress) {
                    downloadDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showNetError(String netMsg) {
        ToastUtils.showShortToast(netMsg);
        notUpgrade(netMsg);
        if (downloadDialog.isShowing()) {
            downloadDialog.dismiss();
        }
    }

    /**
     * 下载完成启动安装
     *
     * @param saveAddress 下载完成，完成地址
     */
    @Override
    public void downloadComplete(String apkFile) {
        if (!TextUtils.isEmpty(apkFile)) {
            if (downloadDialog.isShowing()) {
                downloadDialog.dismiss();
            }
            if (apkFile.endsWith(".apk")) {
                if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                    File file = new File(apkFile);
                    Uri apkUri = FileProvider.getUriForFile(context, "com.chengxiang.pay.provider", file);//在AndroidManifest中的android:authorities值
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    context.startActivity(install);
                    finish();
                } else {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(Uri.fromFile(new File(apkFile)), "application/vnd.android.package-archive");
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);
                    finish();
                }
            }
        }
    }

    @Override
    public String getCheckUpgradeString() {
        if (PermissionsUtil.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
            BaseBean.saveImei(InterfaceUtil.getIMEI(context));
            BaseBean.saveImsi(InterfaceUtil.getIMSI(context));
        }
        RequestParamsCheckUpdate request = new RequestParamsCheckUpdate();
        RequestUtils.initRequestBean(request, encryptManager, "2001");
        request.setOsType("0");
        request.setUserType("0");
        request.setAppName(getResources().getString(R.string.appname));
        String[] signs = {"appVersion", "osType", "userType"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    /**
     * 直接进入登录页
     */
    @Override
    public void notUpgrade(String retMsg) {
        progressCancel();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 下载升级弹窗
     *
     * @param isUpVersion 0需更新 ;1 需强制更新
     * @param versionDesc 更新内容
     * @param downUrl     下载地址
     */
    @Override
    public void setUpgradeDate(String isUpVersion, String versionDesc, String downUrl) {
        progressCancel();
        this.downUrl = downUrl;
        final CommonDialog commonDialog = new CommonDialog(SplashActivity.this);
        commonDialog.setTitle("检测到新版本");
        commonDialog.setDetail(versionDesc);
        commonDialog.setPositive("立即升级");
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpgradeApplication();
                commonDialog.dismiss();
            }
        });
        if (isUpVersion.equals("0")) {
            commonDialog.setNegate("直接进入");
            commonDialog.setOnNegateListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                    notUpgrade("直接进入");
                }
            });
        }
        commonDialog.show();
    }

    /**
     * 下载升级应用
     */
    public void UpgradeApplication() {
        if (PermissionsUtil.hasPermissions(context, needStoragePermissions)) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(SplashActivity.this).inflate(
                    R.layout.dialog_download_apk, null);
            if (ll != null) {
                downloadProgressBar = (ProgressBar) ll.findViewById(R.id.download_apk_progressBar);
                downloadTextView = (TextView) ll.findViewById(R.id.download_apk_tv);
            }
            downloadDialog = new BaseDialog(SplashActivity.this, ll);
            downloadDialog.show();
            String fileAddress = StringUtil.getFilePath("apk");
            String name = "ChengXiangWallet.apk";
            new UpgradePresenter.DownloadApk((UpgradeView.DownloadApkView) context, downUrl, fileAddress, name).PostDownloadApk();
        } else {
            PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_storage)
                    , PERMISSIONS_REQUEST_STORAGE, needStoragePermissions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_APPLY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    into();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    into();
                }
                break;
            case PERMISSIONS_REQUEST_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    UpgradeApplication();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
                    // This will display a dialog directing them to enable the permission in app settings.
                    if (PermissionsUtil.somePermissionPermanentlyDenied(this, needStoragePermissions)) {
                        PermissionsUtil.goSettings2Permissions(this, getString(R.string.request_permission_storage_again)
                                , "去设置", RC_SETTING);
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTING) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtils.showShortToast("从设置界面返回");
            UpgradeApplication();
        }
    }


}
