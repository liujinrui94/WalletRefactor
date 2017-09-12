package com.chengxiang.pay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.BillListAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.BillBean;
import com.chengxiang.pay.bean.RequestParamsBillList;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.SnackBarUtils;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.presenter.ReceivePresenter;
import com.chengxiang.pay.view.ReceiveView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/7 11:27
 * @description: 账单列表
 */

public class BillListActivity extends BaseActivity implements ReceiveView.BillListView, View.OnClickListener {

    private TextView dateTv;//时间
    private TextView stateTv;//状态
    private ListView billLv;//账单列表
    private TextView emptyTv;//账单为空
    private TextView totalAmountTv;//账单总额

    private String dateString;//时间
    private String state;//账单状态
    private String currentPage = "1";//当前页

    private ArrayList<String> stateList = new ArrayList<>();

    private TimePickerView timePickerView;//时间选择器
    private OptionsPickerView optionsPickerView;//条件选择器

    private ArrayList<BillBean> billLists;
    private BillListAdapter adapter;
    private SwipeRefreshLayout mSwipe;
    private ViewFlipper listFoot;

    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        initView();
        initData();
        getBillList();
    }

    private void getBillList() {
        if (!isAdd) {
            if (!mSwipe.isRefreshing()) {
                mSwipe.setRefreshing(true);
            }
        } else {
            listFoot.setDisplayedChild(0);
        }
        progressShow();
        new ReceivePresenter.BillList(this).PostBillList();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.mine_bill));
        LinearLayout dateLL = (LinearLayout) findViewById(R.id.bill_list_date_ll);
        dateTv = (TextView) findViewById(R.id.bill_list_date_tv);
        LinearLayout stateLL = (LinearLayout) findViewById(R.id.bill_list_state_ll);
        stateTv = (TextView) findViewById(R.id.bill_list_state_tv);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.bill_list_swipe);
        billLv = (ListView) findViewById(R.id.bill_list_lv);
        emptyTv = (TextView) findViewById(R.id.bill_list_empty_tv);
        totalAmountTv = (TextView) findViewById(R.id.bill_list_total_amount_tv);

        mSwipe.setColorSchemeColors(getResources().getColor(R.color.themeColorDark));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!billLists.isEmpty()) {
                    currentPage = "1";
                    isAdd = false;
                    billLists.clear();
                }
                getBillList();
            }
        });

        listFoot = (ViewFlipper) LayoutInflater.from(this).inflate(R.layout.item_list_foot, null);

        adapter = new BillListAdapter(this);
        billLv.addFooterView(listFoot);
        billLv.setAdapter(adapter);
        billLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int last = billLv.getLastVisiblePosition();
                if ((Integer.parseInt(currentPage) * 10) == (last + 1)) {
                    currentPage = Integer.parseInt(currentPage) + 1 + "";
                    isAdd = true;
                    getBillList();
                }
            }
        });
        billLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BillListActivity.this, BillDetailActivity.class);
                intent.putExtra("BillBean", billLists.get(position));
                startActivity(intent);
            }
        });


        dateLL.setOnClickListener(this);
        stateLL.setOnClickListener(this);
    }

    private void initData() {
        dateString = getTime(new Date());
        dateTv.setText(dateString);
        initTimePicker();

        stateList.add("全部");
        stateList.add("成功");
        state = stateList.get(0);
        stateTv.setText(state);
        initOptionPicker();

        billLists = new ArrayList<>();

    }

    /**
     * 条件选择器
     */
    private void initOptionPicker() {
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                state = stateList.get(options1);
                stateTv.setText(state);
                if (!billLists.isEmpty()) {
                    currentPage = "1";
                    isAdd = false;
                    billLists.clear();
                }
                getBillList();
            }
        })
                .setTitleText("账单状态")
                .setBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(getResources().getColor(R.color.grayTextColor))
                .setSubmitColor(getResources().getColor(R.color.themeColorDark))
                .setTextColorCenter(getResources().getColor(R.color.blackTextColor))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .setDividerColor(getResources().getColor(R.color.colorDividers))
                .setContentTextSize(21)
                .setSelectOptions(0)//默认选中项
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        optionsPickerView.setPicker(stateList);//一级选择器
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
                if (!billLists.isEmpty()) {
                    currentPage = "1";
                    isAdd = false;
                    billLists.clear();
                }
                getBillList();
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("", "", "", "", "", "")
                .setTitleText("选择要查看的账单月")
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
            case R.id.bill_list_date_ll:
                timePickerView.show();
                break;
            case R.id.bill_list_state_ll:
                optionsPickerView.show();
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
    public String getBillListJsonString() {
        RequestParamsBillList request = new RequestParamsBillList();
        RequestUtils.initRequestBean(request, encryptManager, "8002");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setCurrentPage(currentPage);
        request.setDate(dateString);
        if (TextUtils.equals(state, "全部")) {
            request.setState("0");
        } else if (TextUtils.equals(state, "成功")) {
            request.setState("1");
        }
        request.setPageSize("10");
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void billListResponse(String totalAmt, String pageTotal, ArrayList<BillBean> lists) {
        progressCancel();
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        totalAmountTv.setText("累计交易：" + totalAmt);
        billLists.addAll(lists);
        emptyTv.setVisibility(View.GONE);
        if (billLists == null || billLists.size() == 0) {
            adapter.notifyBankDataChanged(billLists);
            emptyTv.setVisibility(View.VISIBLE);
            emptyTv.setText("暂无交易");
        } else {
            adapter.notifyBankDataChanged(billLists);
        }
        if (Integer.parseInt(currentPage) == Integer.parseInt(pageTotal)) {
            billLv.removeFooterView(listFoot);
            SnackBarUtils.showShort(billLv, "数据加载完成");
        }
    }

}
