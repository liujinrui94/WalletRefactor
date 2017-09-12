package com.chengxiang.pay.framework.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.LoginActivity;
import com.chengxiang.pay.activity.UserInfoActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.BaseProgressDialog;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.orhanobut.logger.Logger;

import static com.chengxiang.pay.framework.base.BaseApplication.safeExit;

public class BaseFragment extends Fragment {
    private BaseProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void checkLevel(Context context) {
        switch (BaseBean.getProcessAudit()) {
            case "0":
                showDialog("perfect", null, context);
                break;
            case "5":
                showDialog("perfectAgain", null, context);
                break;
            case "6":
                showDialog("perfectPhoto", null, context);
                break;
            case "7":
                showDialog("perfectBankInfo", null, context);
                break;
            default:
                showDialog("reviewing", null, context);
        }
    }

    // 用户审核状态 0 待完善 1 待审核 2 鉴权通过 3 审核通过 4 鉴权不通过 5 审核不通过 6基本信息已完善 7 照片已完善
    protected void showDialog(final String type, String detail, Context context) {
        final CommonDialog commonDialog = new CommonDialog(context);

        switch (type) {
            case "perfect"://没有完善过
                commonDialog.setTitle("完善信息");
                commonDialog.setDetail("你还没有完善信息，立即去完善？");
                commonDialog.setPositive("去完善");
                commonDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUserInfoActivity();
                        commonDialog.dismiss();
                    }
                });
                break;
            case "perfectAgain"://抱歉，您的信息未审核通过，请重新完善信息
                commonDialog.setTitle("完善信息");
                commonDialog.setDetail("抱歉，您的信息未审核通过，重新完善信息？");
                commonDialog.setPositive("重新完善");
                commonDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUserInfoActivity();
                        commonDialog.dismiss();
                    }
                });
                break;
            case "perfectPhoto"://照片信息完善成功,请及时完善基本信息
                commonDialog.setTitle("完善信息");
                commonDialog.setDetail("照片信息完善成功,请及时完善基本信息");
                commonDialog.setPositive("去完善");
                commonDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUserInfoActivity();
                        commonDialog.dismiss();
                    }
                });
                break;
            case "perfectBankInfo"://基本信息完善成功,请及时完善照片信息
                commonDialog.setTitle("完善信息");
                commonDialog.setDetail("基本信息完善成功,请及时完善照片信息");
                commonDialog.setPositive("去完善");
                commonDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUserInfoActivity();
                        commonDialog.dismiss();
                    }
                });
                break;
            case "reviewing"://您的信息还在审核中，请耐心等待
                commonDialog.setTitle("完善信息");
                commonDialog.setDetail("您的信息还在审核中，请耐心等待");
                commonDialog.setNegate(getString(R.string.success));
                break;
            case "perfectSuccess"://完善成功
                commonDialog.setTitle("温馨提示");
                commonDialog.setDetail("用户基本信息完善成功，请尽情享用诚享钱包");
                commonDialog.setNegate(getString(R.string.success));
                break;
            case "exit"://安全退出
                commonDialog.setTitle("安全退出");
                commonDialog.setDetail("安全退出会清空已保存的登录信息，确定要退出么？");
                commonDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        safeExit();
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        commonDialog.dismiss();
                    }
                });
                break;
            case "show"://显示输出信息
                commonDialog.setTitle("温馨提示");
                commonDialog.setDetail(detail);
                commonDialog.setNegate(getString(R.string.success));
                break;
        }
        commonDialog.setOnNegateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("perfectSuccess")) {
                    getActivity().finish();
                }
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    private void gotoUserInfoActivity() {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow(String dialogDetail) {
        if (progressDialog != null)
            progressDialog.cancel();
        progressDialog = new BaseProgressDialog(getActivity());
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

    protected void checkCordError(String msg, Context context) {
        if (msg.equals("登录超时,请重新登录")) {
            BaseBean.saveIsLogin(false);
            toLogin(context);
        } else {
            ToastUtils.showLongToast(msg);
        }
    }

    private void toLogin(final Context context) {
        final CommonDialog commonDialog = new CommonDialog(context);
        commonDialog.setDetail("登录超时,请重新登录");
        commonDialog.setDetailCenter(true);
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.i(getClass().getSimpleName() + " onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }



}
