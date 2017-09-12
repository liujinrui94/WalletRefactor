package com.chengxiang.pay.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.bean.RequestParamsCodeReceive;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.custom.MarqueeTextView;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.DisplayUtil;
import com.chengxiang.pay.framework.utils.InterfaceUtil;
import com.chengxiang.pay.framework.utils.QRCodeUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.presenter.ReceivePresenter;
import com.chengxiang.pay.view.ReceiveView;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.text.TextUtils.TruncateAt.MARQUEE;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/8 19:11
 * @description: 收款
 */

public class ReceiveActivity extends BaseActivity implements View.OnClickListener, ReceiveView.GuMaReceiveView, ReceiveView.CodeReceiveView {
    private Context context;

    private LinearLayout wechatSelectLL;//微信
    private LinearLayout aliPaySelectLL;//支付宝
    private LinearLayout guMaSelectLL;//固码
    private LinearLayout unionSelectLL;//银联快捷
    private ImageView selectArrowPointIv;//指示图标
    private float currentLocation = 0;// 当前小图标的位置
    private float toLocation;// 当前小图标要去的位置
    private float offsetPosition;//固定偏移值
    private MarqueeTextView quickCollectionTv;//快速收款
    private String quickRequire;//要求
    private TextView ordinaryCollectionTv;//普通收款
    private LinearLayout quickCollectionLL;//快速收款
    private LinearLayout ordinaryCollectionLL;//普通收款

    private TextView amountTv;//显示金额
    private StringBuffer inBuffer;//金额
    private LinearLayout receiveKeyboardLL;//键盘页面
    private LinearLayout receiveGuMaLL;//固码页面

    private ArrayList<PaymentMethodBean> paymentMethodBeanArrayList;

    private String payType;//支付类型
    private ImageView confirmTypeIv;
    private TextView confirmTypeTv;
    private String guMaUrl;//固码链接
    private ImageView guMaIv;
    private double limitAmount = 0;//限制金额
    private String amount;//收款金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        context = this;
        initView();
        initData();
    }

    private void initView() {
        setTitle("收款");
        wechatSelectLL = (LinearLayout) findViewById(R.id.receive_select_wechat_ll);
        aliPaySelectLL = (LinearLayout) findViewById(R.id.receive_select_ali_pay_ll);
        guMaSelectLL = (LinearLayout) findViewById(R.id.receive_select_gu_ma_ll);
        unionSelectLL = (LinearLayout) findViewById(R.id.receive_select_union_ll);
        selectArrowPointIv = (ImageView) findViewById(R.id.receive_select_arrow_point_iv);
        quickCollectionLL = (LinearLayout) findViewById(R.id.receive_quick_collection_ll);
        ordinaryCollectionLL = (LinearLayout) findViewById(R.id.receive_ordinary_collection_ll);
        quickCollectionTv = (MarqueeTextView) findViewById(R.id.receive_quick_collection_tv);
        ordinaryCollectionTv = (TextView) findViewById(R.id.receive_ordinary_collection_tv);

        receiveKeyboardLL = (LinearLayout) findViewById(R.id.receive_keyboard_ll);
        receiveGuMaLL = (LinearLayout) findViewById(R.id.receive_gu_ma_ll);
        guMaIv = (ImageView) findViewById(R.id.receive_gu_ma_iv);

        //keyboard
        amountTv = (TextView) findViewById(R.id.receive_keyboard_amount_tv);
        TextView oneTv = (TextView) findViewById(R.id.keyboard_one_tv);
        TextView twoTv = (TextView) findViewById(R.id.keyboard_two_tv);
        TextView threeTv = (TextView) findViewById(R.id.keyboard_three_tv);
        TextView fourTv = (TextView) findViewById(R.id.keyboard_four_tv);
        TextView fiveTv = (TextView) findViewById(R.id.keyboard_five_tv);
        TextView sixTv = (TextView) findViewById(R.id.keyboard_six_tv);
        TextView sevenTv = (TextView) findViewById(R.id.keyboard_seven_tv);
        TextView eightTv = (TextView) findViewById(R.id.keyboard_eight_tv);
        TextView nineTv = (TextView) findViewById(R.id.keyboard_nine_tv);
        TextView zeroTv = (TextView) findViewById(R.id.keyboard_zero_tv);
        TextView doubleZeroTv = (TextView) findViewById(R.id.keyboard_double_zero_tv);
        TextView dotTv = (TextView) findViewById(R.id.keyboard_dot_tv);
        ImageView deleteIv = (ImageView) findViewById(R.id.keyboard_delete_iv);
        LinearLayout confirmLL = (LinearLayout) findViewById(R.id.keyboard_confirm_ll);
        confirmTypeIv = (ImageView) findViewById(R.id.keyboard_confirm_type_iv);
        confirmTypeTv = (TextView) findViewById(R.id.keyboard_confirm_type_tv);

        oneTv.setOnClickListener(this);
        twoTv.setOnClickListener(this);
        threeTv.setOnClickListener(this);
        fourTv.setOnClickListener(this);
        fiveTv.setOnClickListener(this);
        sixTv.setOnClickListener(this);
        sevenTv.setOnClickListener(this);
        eightTv.setOnClickListener(this);
        nineTv.setOnClickListener(this);
        zeroTv.setOnClickListener(this);
        doubleZeroTv.setOnClickListener(this);
        dotTv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        confirmLL.setOnClickListener(this);

        ordinaryCollectionLL.setOnClickListener(this);//普通收款
        wechatSelectLL.setOnClickListener(this);
        aliPaySelectLL.setOnClickListener(this);
        guMaSelectLL.setOnClickListener(this);
        unionSelectLL.setOnClickListener(this);
    }
    private void initData() {
        paymentMethodBeanArrayList = (ArrayList<PaymentMethodBean>) getIntent().getSerializableExtra("paymentList");

        getQuickRequire();
        offsetPosition = DisplayUtil.getWindowWidth(context) / 8 - DisplayUtil.dpToPx(context, 10) / 2;
        wechatSelectLL.performClick();//模拟一次点击，即进去后选择第一项
        setCollectionBackground("1");
        inBuffer = new StringBuffer();
    }

    @Override
    public void onClick(View v) {
        quickCollectionTv.setText(quickRequire + "手续费：" + BaseBean.getCurrentFee() + "%");
        ordinaryCollectionTv.setText("下一工作日到账");
        marqueeText();
        switch (v.getId()) {
            case R.id.receive_ordinary_collection_ll:
                ToastUtils.showShortToast("即将开放");
                break;
            case R.id.receive_select_wechat_ll:
                for (PaymentMethodBean paymentMethodBean : paymentMethodBeanArrayList) {
                    if (TextUtils.equals("0", paymentMethodBean.getPayType())) {
                        limitAmount = Double.parseDouble(paymentMethodBean.getCardLimit());
                    }
                }
                toLocation = wechatSelectLL.getLeft() + offsetPosition;
                arrowPointAnimator();
                payType = "0";
                setShowLayout(R.mipmap.ic_user_upgrade_wechat, "微信");
                break;
            case R.id.receive_select_ali_pay_ll:
                for (PaymentMethodBean paymentMethodBean : paymentMethodBeanArrayList) {
                    if (TextUtils.equals("1", paymentMethodBean.getPayType())) {
                        limitAmount = Double.parseDouble(paymentMethodBean.getCardLimit());
                    }
                }
                toLocation = aliPaySelectLL.getLeft() + offsetPosition;
                arrowPointAnimator();
                payType = "1";
                setShowLayout(R.mipmap.ic_user_upgrade_ali_pay, "支付宝");
                break;
            case R.id.receive_select_gu_ma_ll:
                toLocation = guMaSelectLL.getLeft() + offsetPosition;
                arrowPointAnimator();
                payType = "2";
                setShowLayout(0, null);
                break;
            case R.id.receive_select_union_ll:
                for (PaymentMethodBean paymentMethodBean : paymentMethodBeanArrayList) {
                    if (TextUtils.equals("3", paymentMethodBean.getPayType())) {
                        limitAmount = Double.parseDouble(paymentMethodBean.getCardLimit());
                    }
                }
                toLocation = unionSelectLL.getLeft() + offsetPosition;
                arrowPointAnimator();
                quickCollectionTv.setText(quickRequire + "手续费：" + BaseBean.getCurrentFee() + "%附加0.5元/笔代付费");
                payType = "3";
                setShowLayout(R.mipmap.ic_bill_union, "快捷");
                break;
            case R.id.keyboard_one_tv:
                inputNum("1");
                break;
            case R.id.keyboard_two_tv:
                inputNum("2");
                break;
            case R.id.keyboard_three_tv:
                inputNum("3");
                break;
            case R.id.keyboard_four_tv:
                inputNum("4");
                break;
            case R.id.keyboard_five_tv:
                inputNum("5");
                break;
            case R.id.keyboard_six_tv:
                inputNum("6");
                break;
            case R.id.keyboard_seven_tv:
                inputNum("7");
                break;
            case R.id.keyboard_eight_tv:
                inputNum("8");
                break;
            case R.id.keyboard_nine_tv:
                inputNum("9");
                break;
            case R.id.keyboard_zero_tv:
                inputNum("0");
                break;
            case R.id.keyboard_double_zero_tv:
                inputNum("00");
                break;
            case R.id.keyboard_delete_iv:
                inputNum("delete");
                break;
            case R.id.keyboard_dot_tv:
                inputNum("point");
                break;
            case R.id.keyboard_confirm_ll://提交
                double receiveAmt = Double.parseDouble(amountTv.getText().toString()
                        .substring(1));
                if (receiveAmt <= 0) {
                    ToastUtils.showShortToast("请输入大于0的金额");
                    return;
                }
                if (limitAmount < receiveAmt) {
                    ToastUtils.showLongToast("不能超过单卡单笔限额" + limitAmount + "元");
                    return;
                }
                amount = receiveAmt + "";
                if (TextUtils.equals(payType, "3")) {
                    Intent intentUnion = new Intent(context, UnionPayActivity.class);
                    intentUnion.putExtra("amt", amount);
                    startActivity(intentUnion);
                    return;
                }
                getPayCode();
                break;
        }
    }

    //获取二维码
    private void getPayCode() {
        progressShow();
        new ReceivePresenter.CodeReceive(this).PostCodeReceive();
    }

    private void setShowLayout(int resId, String pay) {
        if (payType.equals("2")) {
            receiveGuMaLL.setVisibility(View.VISIBLE);
            receiveKeyboardLL.setVisibility(View.GONE);
            if (TextUtils.isEmpty(guMaUrl)) {
                new ReceivePresenter.GuMaReceive(this).PostGuMaReceive();
            } else {
                Bitmap guMaBitmap = QRCodeUtil.generateBitmap(guMaUrl, 400, 400);
                guMaIv.setImageBitmap(guMaBitmap);
            }
        } else {
            receiveGuMaLL.setVisibility(View.GONE);
            receiveKeyboardLL.setVisibility(View.VISIBLE);
            confirmTypeIv.setImageResource(resId);
            confirmTypeTv.setText("确定" + pay + "支付");
        }
    }

    //填入金额
    public void inputNum(String num) {
        if (TextUtils.equals("point", num)) {
            if (!inBuffer.toString().contains(".")) {
                inBuffer.append(".");
            }
        } else if (TextUtils.equals("delete", num)) {
            if (inBuffer.toString().length() == 0) {
                amountTv.setText("¥" + "0.00");
            } else {
                inBuffer.deleteCharAt(inBuffer.toString().length() - 1);
            }
        } else {
            if (!inBuffer.toString().contains(".")) {
                inBuffer.append(num);
            } else if (inBuffer.substring(inBuffer.toString().indexOf(".") + 1)
                    .length() < 2) {
                inBuffer.append(num);
                if (num.length() == 2) {//小数点后有一位的情况下，输入00
                    inBuffer.deleteCharAt(inBuffer.toString().length() - 1);
                }
            }
        }
        if (!inBuffer.toString().contains(".")) { // 输入整数
            if (inBuffer.toString().length() == 0) { // 清零
                amountTv.setText("¥" + "0.00");
            } else {
                amountTv.setText("¥" + inBuffer.toString() + ".00");
            }
        } else if (inBuffer.substring(inBuffer.toString().indexOf(".") + 1).length() == 0) {
            amountTv.setText("¥" + inBuffer.toString() + "00");

        } else if (inBuffer.substring(inBuffer.toString().indexOf(".") + 1).length() == 1) {
            amountTv.setText("¥" + inBuffer.toString() + "0");

        } else if (inBuffer.substring(inBuffer.toString().indexOf(".") + 1).length() == 2) {
            amountTv.setText("¥" + inBuffer.toString());
        }
    }

    //文字跑马灯
    private void marqueeText() {
        quickCollectionTv.setEllipsize(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                quickCollectionTv.setEllipsize(MARQUEE);
            }
        }, 1500);
    }

    //图标移动
    private void arrowPointAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(selectArrowPointIv, "translationX", currentLocation, toLocation);
        animator.setDuration(500);
        animator.start();
        currentLocation = toLocation;
    }

    //快速收款文字提示
    public String getQuickRequire() {
        if (DisplayUtil.getWindowWidth(context) < 1080) {  //分辨率小于1080
            quickRequire = "                当日到账                 ";
        } else {
            String os = InterfaceUtil.getAppOs();
            if (os.contains("m1note")) {  //手机为魅蓝note时
                quickRequire = "           当日到账              ";
            } else {
                quickRequire = "             当日到账              ";
            }
        }
        return quickRequire;
    }

    /**
     * 快速收款、普通收款背景切换
     *
     * @param type
     */
    public void setCollectionBackground(String type) {
        if (TextUtils.equals("1", type)) {
            quickCollectionLL.setBackgroundResource(R.mipmap.ic_quick_collection_on);
            ordinaryCollectionLL.setBackgroundResource(R.mipmap.ic_ordinary_collection_off);
        } else {
            quickCollectionLL.setBackgroundResource(R.mipmap.ic_quick_collection_off);
            ordinaryCollectionLL.setBackgroundResource(R.mipmap.ic_ordinary_collection_on);
        }
    }

    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getGuMaReceiveJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "3008");
        request.setPhoneNum(BaseBean.getPhoneNum());
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void guMaReceiveResponse(String url) {
        guMaUrl = url;
        Bitmap guMaBitmap = QRCodeUtil.generateBitmap(guMaUrl, 400, 400);
        guMaIv.setImageBitmap(guMaBitmap);
    }


    @Override
    public String getCodeReceiveJsonString() {
        RequestParamsCodeReceive request = new RequestParamsCodeReceive();
        RequestUtils.initRequestBean(request, encryptManager, "5001");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setAmt(encryptManager.getEncryptDES(amount));
        request.setSource(encryptManager.getEncryptDES(payType));
        String[] sign = {"source", "amt", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, sign));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void codeReceiveResponse(String url) {
        progressCancel();
        Intent intent = new Intent();
        intent.setClass(context, PaymentCodeActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("amt", amount);
        intent.putExtra("type", payType);
        intent.putExtra("paymentType", "1");
        startActivity(intent);
    }
}
