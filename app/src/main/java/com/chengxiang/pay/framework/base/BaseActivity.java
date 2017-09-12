package com.chengxiang.pay.framework.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.LoginActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.net.NetBroadcastReceiver;
import com.chengxiang.pay.framework.net.NetEventInterface;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.BaseProgressDialog;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity implements NetEventInterface {
    private BaseProgressDialog progressDialog;
    protected BaseApplication baseApplication;
    protected EncryptManager encryptManager;

    private int netMobile;//网络状态
    private NetBroadcastReceiver netBroadcastReceiver;//监控网络的广播
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = this;
        BaseApplication.getInstance().addActivity(this);

        Logger.i(InterfaceUtil.getRunningActivityName(mContext));
        baseApplication = (BaseApplication) this.getApplication();
        //初始化加密管理者
        encryptManager = EncryptManager.getEncryptManager();
        try {
            encryptManager.initEncrypt();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("加密管理初始化异常~~~");
        }
    }

    public void btnFinish(View v) {
        finish();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            netBroadcastReceiver.setNetEvent(this);//设置网络监听
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        BaseApplication.getInstance().finishActivity(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netBroadcastReceiver != null) {
            //注销广播
            unregisterReceiver(netBroadcastReceiver);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * actionbar中标题
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        TextView textTitle = (TextView) findViewById(R.id.action_bar_title_tv);
        textTitle.setText(title);
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow(String dialogDetail) {
        if (progressDialog != null)
            progressDialog.cancel();
        progressDialog = new BaseProgressDialog(this);
        progressDialog.setDialogDetail(dialogDetail);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow() {
        return progressShow("");
    }

    /**
     * 取消加载对话框
     */
    protected void progressCancel() {
        if (progressDialog != null)
            progressDialog.cancel();
    }

    protected void checkCordError(String msg) {
        if (msg.equals("登录超时,请重新登录")) {
            BaseBean.saveIsLogin(false);
            toLogin();
        } else {
            ToastUtils.showLongToast(msg);
        }
    }

    private void toLogin() {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setDetail("登录超时,请重新登录");
        commonDialog.setDetailCenter(true);
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    /**
     * 以下为点击区域外隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v     view
     * @param event 点击事件
     * @return 是否隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token IBinder
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    private void isNetConnect() {
        if (snackbar == null) {
            snackbar = SnackBarUtils.indefiniteSnackBar(BaseActivity.this.findViewById(android.R.id.content)
                    , "无网络连接，请检查网络设置");
        }
        switch (netMobile) {
            case 1://wifi
                if (snackbar.isShown()) {
                    snackbar.dismiss();
                }
                break;
            case 0://移动数据
                if (snackbar.isShown()) {
                    snackbar.dismiss();
                }
                break;
            case -1://没有网络
                snackbar.setAction("去设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
                break;
        }
    }
}

