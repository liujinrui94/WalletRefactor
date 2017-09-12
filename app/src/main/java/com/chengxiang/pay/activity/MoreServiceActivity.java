package com.chengxiang.pay.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 17:42
 * @description: 更多服务网页版
 */

public class MoreServiceActivity extends BaseActivity {
    private WebView webview;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_service);
        setTitle(getIntent().getStringExtra("title"));
        webview = (WebView) findViewById(R.id.convenience_payment_wv);
        progressBar = (ProgressBar) findViewById(R.id.convenience_payment_progressbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(getIntent().getStringExtra("url"));

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果不做任何处理，浏览网页，点击系统“Back”键，整个Browser会调用finish()而结束自身，
        // 如果希望浏览的网 页回退而不是推出浏览器，需要在当前Activity中处理并消费掉该Back事件。
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
