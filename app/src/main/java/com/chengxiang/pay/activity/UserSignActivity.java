package com.chengxiang.pay.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.chengxiang.pay.R;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.custom.signature.DrawView;
import com.chengxiang.pay.framework.utils.PermissionsUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;

import java.io.File;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 14:05
 * @description: 用户签名页面
 */

public class UserSignActivity extends BaseActivity implements View.OnClickListener {
    private DrawView drawView;

    private final String[] needStoragePermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;

    private static final int RC_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);
        initView();
    }

    private void initView() {
        setTitle("用户签名");
        drawView = (DrawView) findViewById(R.id.user_sign_dv);
        Button clearBtn = (Button) findViewById(R.id.user_sign_clear_signature_btn);
        Button saveBtn = (Button) findViewById(R.id.user_sign_save_signature_btn);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_sign_clear_signature_btn:
                drawView.clearView();
                break;
            case R.id.user_sign_save_signature_btn:
                if (PermissionsUtil.hasPermissions(UserSignActivity.this, needStoragePermissions)) {
                    saveSignImage();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_storage)
                            , PERMISSIONS_REQUEST_STORAGE, needStoragePermissions);
                }
                break;
        }
    }

    /**
     * 保存图片
     */
    private void saveSignImage() {
        File signFile;
        try {
            signFile = drawView.saveSignature();
            if (signFile == null) {
                ToastUtils.showShortToast("请签名");
                return;
            }
        } catch (Exception e) {
            signFile = null;
        }
        if (signFile == null) {
            drawView.clearView();
            ToastUtils.showShortToast("请重新签名");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("fileName", Uri.fromFile(signFile).toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    saveSignImage();
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
            saveSignImage();
        }
    }
}
