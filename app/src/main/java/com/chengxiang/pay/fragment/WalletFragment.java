package com.chengxiang.pay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chengxiang.pay.R;
import com.chengxiang.pay.activity.BillListActivity;
import com.chengxiang.pay.activity.CreditApplyActivity;
import com.chengxiang.pay.activity.DevelopingActivity;
import com.chengxiang.pay.activity.MessageActivity;
import com.chengxiang.pay.activity.MoreServiceActivity;
import com.chengxiang.pay.activity.ReceiveActivity;
import com.chengxiang.pay.activity.RegisterActivity;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.ImageTextGridViewBean;
import com.chengxiang.pay.bean.PaymentMethodBean;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestParamsSlideShow;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.bean.SlideImageBean;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.base.BaseRecyclerAdapter;
import com.chengxiang.pay.framework.base.SmartViewHolder;
import com.chengxiang.pay.framework.constant.Common;
import com.chengxiang.pay.framework.custom.MessageBarView;
import com.chengxiang.pay.framework.encrypt.EncryptManager;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.glide.GlideUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.CommonDialog;
import com.chengxiang.pay.presenter.CommonPresenter;
import com.chengxiang.pay.presenter.ReceivePresenter;
import com.chengxiang.pay.view.CommonView;
import com.chengxiang.pay.view.ReceiveView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/4 16:11
 * @description: 诚享钱包-首页
 */


public class WalletFragment extends BaseFragment implements View.OnClickListener, CommonView.SlideShowView, ReceiveView.HomePageView {
    private Context context;
    private View inflate;

    // 引导图片资源
    private ArrayList<String> imageUrls;//图片地址
    private final int AUTO = 0;


    // 图片封装为一个数组
    private int[] icon = {R.mipmap.ic_wallet_bmjf, R.mipmap.ic_wallet_xyksq, R.mipmap.ic_wallet_wdkd
            , R.mipmap.ic_wallet_sszz, R.mipmap.ic_wallet_hykb, R.mipmap.ic_wallet_xykhk
            , R.mipmap.ic_wallet_cxxd, R.mipmap.ic_wallet_cxlc, R.mipmap.ic_wallet_jfdh};
    private String[] iconName = {"便民缴费", "信用卡申请", "我的快递"
            , "实时转账", "会员卡包", "信用卡还款"
            , "诚享小贷", "诚享理财", "积分兑换"};
    private ArrayList<ImageTextGridViewBean> imageTextGridViewBeanList;

    private ArrayList<PaymentMethodBean> paymentMethodBeanArrayList;

    private EncryptManager encryptManager;
    private MessageBarView rightMbv;

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;
    private SnapHelper snapHelper;
    private int index = 0;
    private RecyclerView gr_rlv;
    private RadioGroup wallet_slideshow_viewGroup;

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
            inflate = inflater.inflate(R.layout.fragment_wallet, container, false);
            initView();
            initData();
        }
        return inflate;
    }


    private void initData() {
        progressShow();
        new CommonPresenter.SlideShow(this).postSlideShow();
        new ReceivePresenter.HomePage(this).postHomePage();
    }

    private void initView() {
        wallet_slideshow_viewGroup = (RadioGroup) inflate.findViewById(R.id.wallet_slideshow_viewGroup);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.wallet_rlv);
        ImageView leftIv = (ImageView) inflate.findViewById(R.id.action_bar_left_iv);
        leftIv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_bar_order));
        leftIv.setOnClickListener(this);
        rightMbv = (MessageBarView) inflate.findViewById(R.id.action_bar_right_mbv);
        rightMbv.setVisibility(View.VISIBLE);
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
        rightMbv.setOnClickListener(this);
        LinearLayout receiveLL = (LinearLayout) inflate.findViewById(R.id.wallet_receive_ll);
        receiveLL.setOnClickListener(this);
        initGridData();
    }

    private void initGridData() {
        gr_rlv = (RecyclerView) inflate.findViewById(R.id.wallet_gr_rlv);
        gr_rlv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        gr_rlv.setItemAnimator(new DefaultItemAnimator());
        imageTextGridViewBeanList = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            ImageTextGridViewBean gridBean = new ImageTextGridViewBean();
            gridBean.setImageId(icon[i]);
            gridBean.setDescribe(iconName[i]);
            imageTextGridViewBeanList.add(gridBean);
        }
        gr_rlv.setAdapter(new BaseRecyclerAdapter<ImageTextGridViewBean>(imageTextGridViewBeanList, R.layout.item_grid_service) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, ImageTextGridViewBean model, final int position) {
                holder.text(R.id.item_grid_service_tv, model.getDescribe());
                holder.image(R.id.item_grid_service_iv, model.getImageId());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(BaseBean.getOpenId()) && TextUtils.isEmpty(BaseBean.getPhoneNum())) {
                            wechatBindPhoneNum();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("title", iconName[position]);
                            switch (imageTextGridViewBeanList.get(position).getImageId()) {
                                case R.mipmap.ic_wallet_bmjf:
                                    //便民缴费
                                    intent.setClass(context, MoreServiceActivity.class);
                                    intent.putExtra("url", Common.PAY_THE_FEES);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_xyksq:
                                    //信用卡申请
                                    intent.setClass(context, CreditApplyActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_wdkd:
                                    //我的快递
                                    intent.setClass(context, MoreServiceActivity.class);
                                    intent.putExtra("url", Common.EXPRESS_CHECK);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_sszz:
                                    //实时转账
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_hykb:
                                    //会员卡包
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_xykhk:
                                    //信用卡还款
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_cxxd:
                                    //诚享小贷
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_cxlc:
                                    //诚享理财
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                                case R.mipmap.ic_wallet_jfdh:
                                    //积分兑换
                                    intent.setClass(context, DevelopingActivity.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(AUTO);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(BaseBean.getOpenId()) && TextUtils.isEmpty(BaseBean.getPhoneNum())) {
            wechatBindPhoneNum();
        } else {
            switch (v.getId()) {
                case R.id.action_bar_left_iv:
                    Intent intentOrderList = new Intent(context, BillListActivity.class);
                    startActivity(intentOrderList);
                    break;
                case R.id.action_bar_right_mbv:
                    Intent intentMessage = new Intent(context, MessageActivity.class);
                    startActivity(intentMessage);
                    break;
                case R.id.wallet_receive_ll:
                    if (TextUtils.equals("3", BaseBean.getProcessAudit())) {
                        Intent intentReceive = new Intent(context, ReceiveActivity.class);
                        intentReceive.putExtra("paymentList", paymentMethodBeanArrayList);
                        startActivity(intentReceive);
                    } else {
                        checkLevel(context);
                    }
                    break;
                default:
                    ToastUtils.showShortToast("按键错误，请重试！！！");
            }
        }
    }


    @Override
    public void showCordError(String msg) {
        progressCancel();
        checkCordError(msg, context);
    }

    @Override
    public String getSlideShowJsonString() {
        RequestParamsSlideShow request = new RequestParamsSlideShow();
        RequestUtils.initRequestBean(request, encryptManager, "8003");
        request.setType("2");
        request.setSign(SignUtil.signNature(encryptManager, request, null));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void slideShowResponse(ArrayList<SlideImageBean> newList) {
        progressCancel();
        imageUrls = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            imageUrls.add(newList.get(i).getImgUrl() + newList.get(i).getImg());
        }
        initSlideShowData();
    }

    private void initSlideShowData() {
        for (int i = 0; i < imageUrls.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(R.drawable.slideshow_background);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setPadding(5, 5, 5, 5);
            wallet_slideshow_viewGroup.addView(radioButton);
        }
        mHandler.sendEmptyMessageDelayed(AUTO, 0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<String>(imageUrls, R.layout.item_wallet_slideshow) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
                GlideUtils.getInstance().loadNetImageNoDefaultImg(imageUrls.get(position), (ImageView) holder.itemView.findViewById(R.id.item_wallet_slideshow_iv));
            }
        };
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstVisibleItemPosition = snapHelper.findTargetSnapPosition(manager, velocityX, velocityY);
                if (firstVisibleItemPosition >= imageUrls.size()) {
                    firstVisibleItemPosition = imageUrls.size() - 1;
                }
                if (firstVisibleItemPosition < 0) {
                    firstVisibleItemPosition = 0;
                }
                index = firstVisibleItemPosition;
                mRecyclerView.smoothScrollToPosition(index);
                wallet_slideshow_viewGroup.check(index + 1);
                return false;
            }
        });


    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case AUTO:
                    if (index >= mRecyclerView.getChildCount()) {
                        index--;
                    } else {
                        index++;
                    }
                    wallet_slideshow_viewGroup.check(index + 1);
                    mRecyclerView.smoothScrollToPosition(index);
                    mHandler.sendEmptyMessageDelayed(AUTO, 3 * 1000);
                    break;
                default:
                    break;
            }
        }


    };


    @Override
    public String getHomePageJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "8001");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void homePageResponse(ArrayList<PaymentMethodBean> paymentMethodBeanArrayList) {
        this.paymentMethodBeanArrayList = paymentMethodBeanArrayList;
        rightMbv.setMessageCount(BaseBean.getUnReadNum());
    }

    /**
     * 微信绑定手机号
     */
    private void wechatBindPhoneNum() {
        final CommonDialog commonDialog = new CommonDialog(context);
        commonDialog.setDetail("用户尚未绑定手机号，请绑定后重试");
        commonDialog.setDetailCenter(true);
        commonDialog.setOnNegateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        commonDialog.setPositive("去绑定");
        commonDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

}
