package com.chengxiang.pay.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPerfectPhoto;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.PermissionsUtil;
import com.chengxiang.pay.framework.utils.ImageUtil;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.view.MineView;
import com.google.gson.Gson;

import java.io.File;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/13 18:08
 * @description: 用户信息照片信息
 */

public class UserInfoPhotoDataFragment extends BaseFragment implements View.OnClickListener, MineView.UploadFileView, MineView.PerfectPhotoDataView {
    private Context context;
    private View inflate;

    private ImageView faceUploadIv;//身份证正面+银行卡
    private ImageView backUploadIv;//反面+信用卡
    private ImageView handUploadIv;//手持合照

    private int imageMark;//图片标记
    private File uploadFileImage;//上传图片文件

    private String faceFileName, backFileName, handFileName;//上传图片文件返回名称

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int RC_SETTING = 1;

    private final String[] needPermissions = new String[]{Manifest.permission.CAMERA};
    private final String[] needStoragePermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSIONS_REQUEST_STORAGE = 2;

    /**
     * 使用拍照功能获取的photoUri
     */
    private Uri photoUri;

    private static final int OPEN_CAMERA = 100;

    private Button uploadBtn;

    private EncryptManager encryptManager;

    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_user_info_photo_data, container, false);
            initView();
        }
        return inflate;
    }

    private void initView() {
        TextView hint1Tv = (TextView) inflate.findViewById(R.id.user_info_photo_hint1_tv);
        TextView hint2Tv = (TextView) inflate.findViewById(R.id.user_info_photo_hint2_tv);
        TextView hint3Tv = (TextView) inflate.findViewById(R.id.user_info_photo_hint3_tv);
        String str0 = getString(R.string.id_card_require);
        String str1 = getString(R.string.id_card_bank_require);
        String str2 = getString(R.string.take_photo_require);
        setTextColor(hint1Tv, str0, str0.length() - 8);
        setTextColor(hint2Tv, str1, str1.length() - 5);
        setTextColor(hint3Tv, str2, str2.length() - 15);

        faceUploadIv = (ImageView) inflate.findViewById(R.id.user_info_upload_face_iv);
        backUploadIv = (ImageView) inflate.findViewById(R.id.user_info_upload_back_iv);
        handUploadIv = (ImageView) inflate.findViewById(R.id.user_info_upload_hand_iv);

        uploadBtn = (Button) inflate.findViewById(R.id.mine_data_btn_upload);

        faceUploadIv.setOnClickListener(this);
        backUploadIv.setOnClickListener(this);
        handUploadIv.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);

        if (TextUtils.equals("3", BaseBean.getProcessAudit())
                || TextUtils.equals("7", BaseBean.getProcessAudit())) {
            GlideUtils.getInstance().loadNetImage(BaseBean.getPositive(), faceUploadIv);
            GlideUtils.getInstance().loadNetImage(BaseBean.getOpposite(), backUploadIv);
            GlideUtils.getInstance().loadNetImage(BaseBean.getMeet(), handUploadIv);
            setUnableClick();
        }
    }


    private void setUnableClick() {
        uploadBtn.setVisibility(View.INVISIBLE);
        faceUploadIv.setOnClickListener(null);
        backUploadIv.setOnClickListener(null);
        handUploadIv.setOnClickListener(null);
    }


    /**
     * 设置字体两种颜色显示
     */
    private void setTextColor(TextView textView, String str, int index0) {
        ForegroundColorSpan colorYellow = new ForegroundColorSpan(getResources().getColor(R.color.themeColor));
        SpannableString ss0 = new SpannableString(str);
        ss0.setSpan(colorYellow, index0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_upload_face_iv:
                imageMark = 1;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    checkStoragePermissions();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.user_info_upload_back_iv:
                imageMark = 2;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    checkStoragePermissions();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.user_info_upload_hand_iv:
                imageMark = 3;
                if (PermissionsUtil.hasPermissions(context, needPermissions)) {
                    checkStoragePermissions();
                } else {
                    PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_camera)
                            , PERMISSIONS_REQUEST_CAMERA, needPermissions);
                }
                break;
            case R.id.mine_data_btn_upload:
                perfectPhoto();
                break;
            default:
                break;
        }
    }

    //校验存储写入权限
    private void checkStoragePermissions() {
        if (PermissionsUtil.hasPermissions(context, needStoragePermissions)) {
            openCamera();
        } else {
            PermissionsUtil.requestPermissions(this, getString(R.string.request_permission_storage)
                    , PERMISSIONS_REQUEST_STORAGE, needStoragePermissions);
        }
    }

    private void perfectPhoto() {
        if (TextUtils.isEmpty(faceFileName)) {
            ToastUtils.showLongToast("请检查身份证正面和银行卡正面是否上传完成");
            return;
        }
        if (TextUtils.isEmpty(backFileName)) {
            ToastUtils.showLongToast("请检查身份证反面和信用卡正面是否上传完成");
            return;
        }
        if (TextUtils.isEmpty(handFileName)) {
            ToastUtils.showLongToast("请检查身份证与本人合照是否上传完成");
            return;
        }
        progressShow();
        new MinePresenter.PerfectPhotoData(this).PostPerfectPhotoData();
    }

    private void openCamera() {
        if (InterfaceUtil.sdState() && InterfaceUtil.hasCamera(context)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues();
            photoUri = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, OPEN_CAMERA);
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, context);
    }

    @Override
    public String getUploadFileJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "3002");
        request.setPhoneNum(BaseBean.getPhoneNum());
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public File getUploadFileImage() {
        return uploadFileImage;
    }

    @Override
    public void uploadFileResponse(String fileName) {
        progressCancel();
        if (imageMark == 1) {
            faceFileName = fileName;
            GlideUtils.getInstance().loadLocalImage(uploadFileImage, faceUploadIv);
        } else if (imageMark == 2) {
            backFileName = fileName;
            GlideUtils.getInstance().loadLocalImage(uploadFileImage, backUploadIv);
        } else {
            handFileName = fileName;
            GlideUtils.getInstance().loadLocalImage(uploadFileImage, handUploadIv);
        }
    }

    @Override
    public String getPerfectPhotoDataJsonString() {
        RequestParamsPerfectPhoto request = new RequestParamsPerfectPhoto();
        RequestUtils.initRequestBean(request, encryptManager, "3102");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setPositive(faceFileName);
        request.setOpposite(backFileName);
        request.setGroupPhoto(handFileName);
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void perfectPhotoDataResponse() {
        progressCancel();
        setUnableClick();
        if (BaseBean.getProcessAudit().equals("3")) {
            showDialog("perfectSuccess", null, context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    checkStoragePermissions();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
                    // This will display a dialog directing them to enable the permission in app settings.
                    if (PermissionsUtil.somePermissionPermanentlyDenied(this, needPermissions)) {
                        PermissionsUtil.goSettings2Permissions(this, getString(R.string.request_permission_camera_again)
                                , "去设置", RC_SETTING);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    openCamera();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            uploadFileImage = ImageUtil.getFileFromUri(context, photoUri);
            progressShow("优化上传中...");
            new MinePresenter.UploadFile(this).PostUploadFile();
        } else if (requestCode == RC_SETTING) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtils.showShortToast("从设置界面返回");
        }
    }
}
