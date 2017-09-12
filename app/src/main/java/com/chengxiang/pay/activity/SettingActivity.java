package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsCheckUpdate;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Common;
import com.chengxiang.pay.framework.custom.SwitchButton;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.BaseDialog;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.chengxiang.pay.presenter.UpgradePresenter;
import com.chengxiang.pay.view.UpgradeView;
import com.google.gson.Gson;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/14 17:55
 * @description: 设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, UpgradeView.CheckUpgradeView, UpgradeView.DownloadApkView {

    private Context mContext;
    private BaseDialog downloadDialog;
    private ProgressBar downloadProgressBar;
    private TextView downloadTextView;

    @BindView(R.id.setting_customer_service_ll)//联系客服
            LinearLayout contact_customer_service_ll;
    @BindView(R.id.setting_version_update_ll)
    LinearLayout setting_version_update_ll;//检查更新
    @BindView(R.id.setting_switchButton)
    SwitchButton mSwitchButton;
    @BindView(R.id.tv_mine_setting_versions_info)
    TextView versions_info;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        mContext = this;
        initView();
        initEvent();
    }

    private void initEvent() {
        mSwitchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitched(boolean isSwitchOn) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isSwitchOn) {
                    editor.putBoolean(Common.VOICE, true);
                    ToastUtils.showShortToast("到账语音播报已打开");
                } else {
                    editor.putBoolean(Common.VOICE, false);
                    ToastUtils.showShortToast("到账语音播报已关闭");
                }
                editor.apply();
            }
        });

    }


    private void initView() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        setTitle(getResources().getString(R.string.mine_setting));
        versions_info.setText("V" + BaseBean.getAppVersion());
        mSwitchButton.updateSwitchState(sharedPreferences.getBoolean(Common.VOICE, true));
    }

    @OnClick({R.id.setting_customer_service_ll, R.id.setting_version_update_ll})
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.setting_customer_service_ll:
                mIntent = new Intent(mContext, ContactCustomerServiceActivity.class);
                startActivity(mIntent);
                break;
            case R.id.setting_version_update_ll:
                progressShow();
                new UpgradePresenter.Upgrade(this).PostCheckUpgrade();
                break;
        }

    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
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
        progressCancel();
        ToastUtils.showShortToast(netMsg);
        if (downloadDialog.isShowing()) {
            downloadDialog.dismiss();
        }
    }

    @Override
    public void downloadComplete(String apkFile) {
        if (!TextUtils.isEmpty(apkFile)) {
            if (downloadDialog.isShowing()) {
                downloadDialog.dismiss();
            }
            if (apkFile.endsWith(".apk")) {
                if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                    File file = new File(apkFile);
                    Uri apkUri = FileProvider.getUriForFile(mContext, "com.chengxiang.pay.provider", file);//在AndroidManifest中的android:authorities值
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    mContext.startActivity(install);
                    finish();
                } else {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(Uri.fromFile(new File(apkFile)), "application/vnd.android.package-archive");
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(install);
                    finish();
                }
            }
        }
    }


    @Override
    public String getCheckUpgradeString() {
        RequestParamsCheckUpdate request = new RequestParamsCheckUpdate();
        RequestUtils.initRequestBean(request, encryptManager, "2001");
        request.setFunCode("2001");
        request.setOsType("0");
        request.setUserType("0");
        request.setAppName(getResources().getString(R.string.appname));
        String[] signs = {"appVersion", "osType", "userType"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void setUpgradeDate(String isUpVersion, String versionDesc, final String downUrl) {
        progressCancel();
        final CommonDialog commonDialog = new CommonDialog(mContext);
        commonDialog.setTitle("检测到新版本");
        commonDialog.setDetail(versionDesc);
        commonDialog.setPositive("立即升级");
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpgradeApplication(downUrl);
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

    @Override
    public void notUpgrade(String retMsg) {
        progressCancel();
        ToastUtils.showShortToast(retMsg);
    }

    /**
     * 下载升级应用
     *
     * @param downUrl 下载地址
     */
    public void UpgradeApplication(String downUrl) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.dialog_download_apk, null);
        if (ll != null) {
            downloadProgressBar = (ProgressBar) ll.findViewById(R.id.download_apk_progressBar);
            downloadTextView = (TextView) ll.findViewById(R.id.download_apk_tv);
        }
        downloadDialog = new BaseDialog(mContext, ll);
        downloadDialog.show();
        String fileAddress = StringUtil.getFilePath("apk");
        String name = "ChengXiangWallet.apk";
        new UpgradePresenter.DownloadApk(this, downUrl, fileAddress, name).PostDownloadApk();
    }
}
