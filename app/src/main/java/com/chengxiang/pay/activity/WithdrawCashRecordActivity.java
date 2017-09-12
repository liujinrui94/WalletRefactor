package com.chengxiang.pay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.pickerview.TimePickerView;
import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.CashRegisterAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.CashRegisterBean;
import com.chengxiang.pay.bean.RequestParamsWithdrawCashRecord;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.ProfitPresenter;
import com.chengxiang.pay.view.ProfitView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/7 18:29
 * @description: 提现记录
 */

public class WithdrawCashRecordActivity extends BaseActivity implements View.OnClickListener, ProfitView.WithdrawCashRecordView {

    private TextView dateTv;//时间
    private SwipeRefreshLayout mSwipe;
    private ListView cashRegisterLv;//提现列表
    private TextView emptyTv;//提现记录为空
    private ViewFlipper listFoot;
    private ArrayList<CashRegisterBean> cashLists;//提现记录数据

    private CashRegisterAdapter adapter;

    private TimePickerView timePickerView;//时间选择器

    private String dateString;//时间
    private String currentPage = "1";//当前页

    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash_record);
        initView();
        initData();
        getCashRegister();
    }

    private void getCashRegister() {
        if (!isAdd) {
            if (!mSwipe.isRefreshing()) {
                mSwipe.setRefreshing(true);
            }
        } else {
            listFoot.setDisplayedChild(0);
        }
        progressShow();
        new ProfitPresenter.WithdrawCashRecord(this).PostWithdrawCashRecord();
    }

    private void initView() {
        setTitle("提现记录");
        LinearLayout dateLL = (LinearLayout) findViewById(R.id.cash_register_date_ll);
        dateTv = (TextView) findViewById(R.id.cash_register_date_tv);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.cash_register_swipe);
        cashRegisterLv = (ListView) findViewById(R.id.cash_register_lv);
        emptyTv = (TextView) findViewById(R.id.cash_register_empty_tv);

        mSwipe.setColorSchemeColors(getResources().getColor(R.color.themeColorDark));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!cashLists.isEmpty()) {
                    currentPage = "1";
                    isAdd = false;
                    cashLists.clear();
                }
                getCashRegister();
            }
        });

        listFoot = (ViewFlipper) LayoutInflater.from(this).inflate(R.layout.item_list_foot, null);

        adapter = new CashRegisterAdapter(this);
        cashRegisterLv.addFooterView(listFoot);
        cashRegisterLv.setAdapter(adapter);
        cashRegisterLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int last = cashRegisterLv.getLastVisiblePosition();
                if ((Integer.parseInt(currentPage) * 10) == (last + 1)) {
                    currentPage = Integer.parseInt(currentPage) + 1 + "";
                    isAdd = true;
                    getCashRegister();
                }
            }
        });
        dateLL.setOnClickListener(this);
    }

    private void initData() {
        dateString = getTime(new Date());
        dateTv.setText(dateString);
        initTimePicker();
        cashLists = new ArrayList<>();
    }

    /**
     * 时间选择器
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 23);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2100, 11, 28);
        //时间选择器
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                dateString = getTime(date);
                dateTv.setText(dateString);
                if (!cashLists.isEmpty()) {
                    currentPage = "1";
                    isAdd = false;
                    cashLists.clear();
                }
                getCashRegister();
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("", "", "", "", "", "")
                .setTitleText("选择要查看年月")
                .setBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(getResources().getColor(R.color.grayTextColor))
                .setSubmitColor(getResources().getColor(R.color.themeColorDark))
                .setTextColorCenter(getResources().getColor(R.color.blackTextColor))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .setDividerColor(getResources().getColor(R.color.colorDividers))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cash_register_date_ll:
                timePickerView.show();
                break;
        }
    }

    @Override
    public void showCordError(String msg) {
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getWithdrawCashRecordJsonString() {
        RequestParamsWithdrawCashRecord request = new RequestParamsWithdrawCashRecord();
        RequestUtils.initRequestBean(request, encryptManager, "9004");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setCurrentPage(currentPage);
        request.setDateType(dateString);
        request.setState("0");//全部提现记录
        request.setPageSize("10");
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void withdrawCashRecordResponse(String pageTotal, ArrayList<CashRegisterBean> lists) {
        progressCancel();
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        cashLists.addAll(lists);
        emptyTv.setVisibility(View.GONE);
        if (cashLists == null || cashLists.size() == 0) {
            adapter.notifyBankDataChanged(cashLists);
            emptyTv.setVisibility(View.VISIBLE);
            emptyTv.setText("暂无提现记录");
        } else {
            adapter.notifyBankDataChanged(cashLists);
        }
        if (Integer.parseInt(currentPage) == Integer.parseInt(pageTotal)) {
            cashRegisterLv.removeFooterView(listFoot);
            SnackBarUtils.showShort(cashRegisterLv,"数据加载完成");
        }
    }
}
