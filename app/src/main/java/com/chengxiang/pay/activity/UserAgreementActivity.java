package com.chengxiang.pay.activity;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/4 17:25
 * @description: 用户协议
 */

public class UserAgreementActivity extends BaseActivity {
    private WebView webview;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        setTitle("用户协议");
        webview = (WebView) findViewById(R.id.user_agreement_wv);
        progressBar = (ProgressBar) findViewById(R.id.user_agreement_progressbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(Constant.MOBILE_HOST+"/fvp-qp-business/html/FWXY.html");

        webview.setWebViewClient(client);
        webview.setWebChromeClient(chromeClient);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (i == 100) {
                progressCancel();
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
            } else {
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(i);//设置进度值
            }
            super.onProgressChanged(webView, i);
        }
    };

    @Override
    protected void onDestroy() {
        webview.stopLoading();
        webview.destroy();
        super.onDestroy();
    }
}
