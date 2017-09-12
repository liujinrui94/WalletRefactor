package com.chengxiang.pay.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.BillListActivity;
import com.chengxiang.pay.activity.ChangeBankCardActivity;
import com.chengxiang.pay.activity.DescriptionActivity;
import com.chengxiang.pay.activity.ExtensionActivity;
import com.chengxiang.pay.activity.MessageActivity;
import com.chengxiang.pay.activity.ProfitTabActivity;
import com.chengxiang.pay.activity.SettingActivity;
import com.chengxiang.pay.activity.UserInfoActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.ResponseParamsProfitInfo;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.custom.MessageBarView;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.BaseDialog;
import com.chengxiang.pay.presenter.MinePresenter;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.MineView;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import static com.chengxiang.pay.R.id.mine_change_bank_card_ll;
import static com.chengxiang.pay.R.id.mine_extension_code_ll;
import static com.chengxiang.pay.R.id.mine_extension_user_ll;
import static com.chengxiang.pay.R.id.mine_real_name_ll;
import static com.chengxiang.pay.R.id.mine_safe_exit_btn;
import static com.chengxiang.pay.R.id.mine_up_grade_ll;
import static com.chengxiang.pay.bean.BaseBean.getProcessAudit;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/4 16:11
 * @description: 我的
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, MineView.MineInfoView,ProfitView.ProfitInfoView {
    private Context context;
    private View inflate;

    private ImageView headIv;//头像
    private TextView userIdTv;// 用户ID
    private TextView levelNameTv;//等级
    private TextView refereeTv;// 推荐人
    private TextView wechatFeeTv;//微信费率
    private TextView aliFeeTv;//支付宝费率
    private TextView unionFeeTv;//快捷支付费率
    private TextView solidCodeTv;//固码费率
    private TextView processAduitStateTv;//审核状态

    private ProgressBar downloadProgressBar;
    private TextView downloadTextView;
    private BaseDialog downloadDialog;

    private EncryptManager encryptManager;

    private MessageBarView rightMbv;

    /**
     * 在activity中进行注册
     *
     * @param encryptManager
     */
    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_mine, container, false);
            initView();
        }
        return inflate;
    }

    private void initView() {
        TextView title = (TextView) inflate.findViewById(R.id.action_bar_title_tv);
        title.setText(getResources().getString(R.string.mine));


        rightMbv = (MessageBarView) inflate.findViewById(R.id.action_bar_right_mbv);
        rightMbv.setVisibility(View.VISIBLE);
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
        rightMbv.setOnClickListener(this);
        ImageView leftIv = (ImageView) inflate.findViewById(R.id.action_bar_left_iv);
        leftIv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_bar_order));
        leftIv.setOnClickListener(this);


        headIv = (ImageView) inflate.findViewById(R.id.mine_my_head_iv);
        userIdTv = (TextView) inflate.findViewById(R.id.mine_user_id_tv);
        levelNameTv = (TextView) inflate.findViewById(R.id.mine_level_name_tv);
        refereeTv = (TextView) inflate.findViewById(R.id.mine_referee_tv);
        wechatFeeTv = (TextView) inflate.findViewById(R.id.mine_wechat_fee_tv);
        aliFeeTv = (TextView) inflate.findViewById(R.id.mine_ali_fee_tv);
        unionFeeTv = (TextView) inflate.findViewById(R.id.mine_union_fee_tv);
        solidCodeTv = (TextView) inflate.findViewById(R.id.mine_solid_code_fee_tv);
        processAduitStateTv = (TextView) inflate.findViewById(R.id.mine_process_aduit_state_tv);

        inflate.findViewById(R.id.mine_real_name_ll).setOnClickListener(this);//实名认证
        inflate.findViewById(R.id.mine_extension_code_ll).setOnClickListener(this);//我要推广
        inflate.findViewById(R.id.mine_extension_user_ll).setOnClickListener(this);//我的客户
        inflate.findViewById(R.id.mine_up_grade_ll).setOnClickListener(this);//提升等级
        inflate.findViewById(R.id.mine_change_bank_card_ll).setOnClickListener(this);//更换银行卡
        inflate.findViewById(R.id.mine_setting_ll).setOnClickListener(this);//设置
        inflate.findViewById(R.id.mine_description_of_products_ll).setOnClickListener(this);//产品说明
        inflate.findViewById(R.id.mine_safe_exit_btn).setOnClickListener(this);//安全退出
    }

    private void initData() {
        progressShow();
        new MinePresenter.MineInfo(this).PostMineInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
        initData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case mine_real_name_ll:
                intent.setClass(context, UserInfoActivity.class);
                startActivity(intent);
                break;
            case mine_extension_code_ll:
                intent.setClass(context, ExtensionActivity.class);
                startActivity(intent);
                break;
            case mine_extension_user_ll:
                if (TextUtils.equals("3", BaseBean.getProcessAudit())) {
                    goToProfitTab(1);
                } else {
                    checkLevel(context);
                }
                break;
            case mine_up_grade_ll:
                if (TextUtils.equals("3", BaseBean.getProcessAudit())) {
                    goToProfitTab(3);
                } else {
                    checkLevel(context);
                }
                break;
            case mine_change_bank_card_ll:
                if (TextUtils.equals("3", BaseBean.getProcessAudit())) {
                    intent.setClass(context, ChangeBankCardActivity.class);
                    startActivity(intent);
                } else {
                    checkLevel(context);
                }
                break;
            case mine_safe_exit_btn:
                showDialog("exit", null, context);
                break;
            case R.id.mine_description_of_products_ll:
                intent.setClass(context, DescriptionActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting_ll:
                intent.setClass(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_bar_right_mbv:
                Intent mIntent = new Intent(context, MessageActivity.class);
                startActivity(mIntent);
                break;
            case R.id.action_bar_left_iv:
                Intent intentOrderList = new Intent(context, BillListActivity.class);
                startActivity(intentOrderList);
                break;
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }
    }

    private void goToProfitTab(int i) {
        if (TextUtils.isEmpty(BaseBean.getTotalProfitAmt())&&BaseBean.getDirectNum()==0) {
            progressShow();
            new ProfitPresenter.ProfitInfo(this).PostProfitInfo();
        }else {
            Intent intent = new Intent(context, ProfitTabActivity.class);
            intent.putExtra("typeClass", i);
            startActivity(intent);
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, context);
    }

    @Override
    public String getMineInfoJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "0003");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void mineInfoResponse() {
        progressCancel();
        GlideUtils.getInstance().loadNetCircleDefaultImage(BaseBean.getHeadImgUrl(), headIv);

        if (TextUtils.isEmpty(BaseBean.getPhoneNum())) {
            userIdTv.setText(BaseBean.getNickName());
        } else {
            userIdTv.setText(StringUtil.encryptPhoneNum(BaseBean.getPhoneNum()));
        }
        levelNameTv.setText(BaseBean.getLevelStr());//等级
        if (!TextUtils.isEmpty(BaseBean.getTjUserPhone())) {
            if (encryptManager != null) {
                String tjUserPhone = encryptManager.
                        getDecryptDES(BaseBean.getTjUserPhone(), encryptManager.getPingKey());
                refereeTv.setText("推荐人：" + StringUtil.encryptPhoneNum(tjUserPhone));
            }
        } else {
            refereeTv.setText("推荐人：" + StringUtil.encryptPhoneNum("13888888888"));
        }
        wechatFeeTv.setText(Html.fromHtml("<font color='#ffffff'>微信</font><br/><font color='#fd6e4c'>" + BaseBean.getWechatFee() + "%</font>"));//微信费率
        aliFeeTv.setText(Html.fromHtml("<font color='#ffffff'>支付宝</font><br/><font color='#fd6e4c'>" + BaseBean.getAliFee() + "%</font>"));//支付宝费率
        unionFeeTv.setText(Html.fromHtml("<font color='#ffffff'>快捷支付</font><br/><font color='#fd6e4c'>" + BaseBean.getUnionFee() + "%</font>"));//快捷支付费率
        solidCodeTv.setText(Html.fromHtml("<font color='#ffffff'>固定码</font><br/><font color='#fd6e4c'>" + BaseBean.getSolidCodeFee() + "%</font>"));//固码费率
        processAduitStateTv.setText(perfect());//审核状态
    }

    /**
     * 审核进度0 待完善 1 待审核 2 鉴权通过 3 审核通过
     * 4 鉴权不通过 5 审核不通过 6基本信息已完善 7 照片已完善
     */
    private String perfect() {
        if (TextUtils.equals("0", getProcessAudit())) {
            return "未完善";
        } else if (TextUtils.equals("3", getProcessAudit())) {
            return "审核通过";
        } else if (TextUtils.equals("6", getProcessAudit())) {
            return "照片信息未完善";
        } else if (TextUtils.equals("7", getProcessAudit())) {
            return "银行卡信息未完善";
        } else {
            return "审核未通过";
        }
    }

    @Override
    public String getProfitInfo() {
        RequestParamsPhoneNumber requestParamsPhoneNumber = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(requestParamsPhoneNumber, encryptManager, "9001");
        requestParamsPhoneNumber.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] sign = {"phoneNum"};
        requestParamsPhoneNumber.setSign(SignUtil.signNature(encryptManager, requestParamsPhoneNumber, sign));
        String requestString = new Gson().toJson(requestParamsPhoneNumber);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void profitInfoResponse(ResponseParamsProfitInfo responseParamsProfitInfo) {
        progressCancel();
        BaseBean.saveBalance(encryptManager.getDecryptDES(responseParamsProfitInfo.getBalance()));
        BaseBean.saveOkAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getAmt()));
        BaseBean.saveNoAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getZaituAmt()));
        BaseBean.saveTotalProfitAmt(encryptManager.getDecryptDES(responseParamsProfitInfo.getAllShareAmt()));
        BaseBean.saveDirectNum(responseParamsProfitInfo.getDirectNum());
        BaseBean.savePerNum1(responseParamsProfitInfo.getpNum1());
        BaseBean.savePerNum2(responseParamsProfitInfo.getpNum2());
        BaseBean.savePerNum3(responseParamsProfitInfo.getpNum3());
        BaseBean.savePerNum4(responseParamsProfitInfo.getpNum4());
    }
}
