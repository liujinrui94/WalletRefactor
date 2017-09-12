package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.MessageListAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.MessageBean;
import com.chengxiang.pay.bean.RequestParamsMessageDetail;
import com.chengxiang.pay.bean.RequestParamsPhoneNumber;
import com.chengxiang.pay.bean.RequestParamsModifyMessage;
import com.chengxiang.pay.bean.RequestUtils;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.encrypt.SignUtil;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.MessageDetailDialog;
import com.chengxiang.pay.presenter.MessagePresenter;
import com.chengxiang.pay.view.MessageView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/6 14:26
 * @description: 我的消息
 */

public class MessageActivity extends BaseActivity implements MessageView.MessageListView, MessageView.MessageModifyView, MessageView.MessageDetailView, View.OnClickListener {

    private SwipeRefreshLayout mSwipe;
    private TextView messageEmptyTv;

    private ArrayList<MessageBean> messageList;
    private MessageListAdapter adapter;
    private String messageId;
    private int messagePosition;//列表中位置
    private String modifyType;// 1 删除 2 标记为已读
    private String modifyList;//消息id消息唯一标识多个用竖线“|”分隔

    private LinearLayout bottomLL;//底部布局
    private TextView actionBarRightTv;
    public boolean isModify = false;// 是否处于编辑状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        getMessage();
    }

    private void initView() {
        setTitle("消息(" + BaseBean.getUnReadNum() + ")");

        actionBarRightTv = (TextView) findViewById(R.id.action_bar_right_tv);
        actionBarRightTv.setText("编辑");
        actionBarRightTv.setVisibility(View.VISIBLE);
        actionBarRightTv.setOnClickListener(this);

        mSwipe = (SwipeRefreshLayout) findViewById(R.id.message_swipe);
        ListView messageLv = (ListView) findViewById(R.id.message_lv);
        messageEmptyTv = (TextView) findViewById(R.id.message_empty_tv);

        mSwipe.setColorSchemeColors(getResources().getColor(R.color.themeColorDark));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessage();
            }
        });
        adapter = new MessageListAdapter(this);
        messageLv.setAdapter(adapter);
        messageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.equals("0", messageList.get(position).getIsRead())) {
                    messagePosition = position;
                    messageId = messageList.get(position).getId();
                    getMessageDetail();
                } else {
                    adapter.setCurrentPosition(position);
                    adapter.notifyDataSetChanged();
                }
                shoeMessageDetail(messageList.get(position).getContent()
                        , messageList.get(position).getDateTime());
            }
        });

        bottomLL = (LinearLayout) findViewById(R.id.message_bottom_ll);
        TextView selectAllTv = (TextView) findViewById(R.id.message_select_all_tv);//全选
        TextView asReadTv = (TextView) findViewById(R.id.message_as_read_tv);//标记为已读
        TextView deleteTv = (TextView) findViewById(R.id.message_delete_tv);//删除
        selectAllTv.setOnClickListener(this);
        asReadTv.setOnClickListener(this);
        deleteTv.setOnClickListener(this);
    }

    /**
     * 显示消息详细
     *
     * @param detail   详情
     * @param dateTime 时间
     */
    private void shoeMessageDetail(String detail, String dateTime) {
        final MessageDetailDialog messageDetailDialog = new MessageDetailDialog(this);
        messageDetailDialog.setDetail(detail);
        String[] arr=dateTime.split("\\s+");
        messageDetailDialog.setDate(arr[0]);
        messageDetailDialog.setTime(arr[1]);
        messageDetailDialog.setOnCloseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDetailDialog.dismiss();
            }
        });
        messageDetailDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_right_tv:
                if (actionBarRightTv.getText().equals("编辑")) {//编辑
                    isModify = true;
                    MessageListAdapter.isVisibility = true;
                    actionBarRightTv.setText("取消");
                    bottomLL.setVisibility(View.VISIBLE);
                    initCheckBox(false);
                } else {//取消
                    isModify = false;
                    MessageListAdapter.isVisibility = false;
                    actionBarRightTv.setText("编辑");
                    bottomLL.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.message_select_all_tv:
                initCheckBox(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.message_as_read_tv://标记为已读
                modifyType = "2";
                modifyMessage();
                break;
            case R.id.message_delete_tv://删除
                modifyType = "1";
                modifyMessage();
                break;

        }

    }

    //初始化消息选择
    private void initCheckBox(boolean b) {
        for (int i = 0; i < messageList.size(); i++) {
            MessageListAdapter.getIsSelected().put(i, b);
        }
    }

    //获取消息列表
    private void getMessage() {
        progressShow();
        new MessagePresenter.MessageList(this).PostMessageList();
    }

    //获取消息详情
    private void getMessageDetail() {
        new MessagePresenter.MessageDetail(this).PostMessageDetail();
    }

    //修改消息状态
    private void modifyMessage() {
        if (!TextUtils.isEmpty(modifyList)) {
            modifyList = null;
        }
        modifyList = getModifyList();
        progressShow();
        new MessagePresenter.MessageModify(this).PostMessageModify();
    }

    private String getModifyList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < MessageListAdapter.getIsSelected().size(); i++) {
            if (MessageListAdapter.getIsSelected().get(i)) {
                stringBuilder.append(messageList.get(i).getId()).append("|");
            }
        }
        return stringBuilder.toString();
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
    public String getMessageListJsonString() {
        RequestParamsPhoneNumber request = new RequestParamsPhoneNumber();
        RequestUtils.initRequestBean(request, encryptManager, "1002");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        String[] signs = {"phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void messageListResponse(ArrayList<MessageBean> list) {
        this.messageList = list;
        progressCancel();

        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
        if (list.size() > 0) {
            BaseBean.saveUnReadNum(Integer.parseInt(list.get(0).getUnreadMsgCount()));
            setTitle("消息(" + BaseBean.getUnReadNum() + ")");
        }
        messageEmptyTv.setVisibility(View.GONE);
        actionBarRightTv.setVisibility(View.VISIBLE);
        if (messageList.isEmpty()) {
            messageEmptyTv.setVisibility(View.VISIBLE);
            actionBarRightTv.setVisibility(View.GONE);
        } else {
            adapter.notifyBankDataChanged(list);
        }
    }

    @Override
    public String getMessageModifyJsonString() {
        RequestParamsModifyMessage request = new RequestParamsModifyMessage();
        RequestUtils.initRequestBean(request, encryptManager, "1004");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setIdlist(modifyList);
        request.setType(modifyType);
        String[] signs = {"idlist", "type", "phoneNum"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void messageModifyResponse() {
        progressCancel();
        if (modifyType.equals("1")) {
            ToastUtils.showLongToast("删除成功");
        } else {
            ToastUtils.showLongToast("操作成功");
        }
        isModify = false;
        MessageListAdapter.isVisibility = false;
        actionBarRightTv.setText("编辑");
        bottomLL.setVisibility(View.GONE);
        getMessage();

    }

    @Override
    public String getMessageDetailJsonString() {
        RequestParamsMessageDetail request = new RequestParamsMessageDetail();
        RequestUtils.initRequestBean(request, encryptManager, "1003");
        request.setPhoneNum(encryptManager.getEncryptDES(BaseBean.getPhoneNum()));
        request.setId(messageId);
        String[] signs = {"id"};
        request.setSign(SignUtil.signNature(encryptManager, request, signs));
        String requestString = new Gson().toJson(request);
        return StringUtil.getStringToUtf(requestString);
    }

    @Override
    public void messageDetailResponse() {
        messageList.get(messagePosition).setIsRead("1");
        adapter.setCurrentPosition(messagePosition);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        initCheckBox(false);
        MessageListAdapter.isVisibility = false;
        isModify = false;
        if (!messageList.isEmpty()) {
            adapter.notifyDataSetChanged();
        }
        super.finish();
    }
}
