package com.chengxiang.pay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.MessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 9:58
 * @description: 消息列表
 */


public class MessageListAdapter extends BaseAdapter {
    private int mCurrentPos = -1;
    private List<MessageBean> messageBeanList;
    private Context context;

    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    public static boolean isVisibility = false;

    public MessageListAdapter(Context context) {
        this.context = context;
        messageBeanList = new ArrayList<>();
        isSelected = new HashMap<>();
        initDate();
    }

    public void notifyBankDataChanged(ArrayList<MessageBean> list0) {
        messageBeanList.clear();
        messageBeanList.addAll(list0);
        notifyDataSetChanged();
    }

    public void setCurrentPosition(int position) {
        this.mCurrentPos = position;
    }

    @Override
    public int getCount() {
        return messageBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message_bean, null);
            viewHolder = new ViewHolder();
            viewHolder.messageCheckBox = (CheckBox) convertView.findViewById(R.id.message_bean_checkbox);
            viewHolder.messageTitleTv = (TextView) convertView.findViewById(R.id.message_bean_title_tv);
            viewHolder.messageTimeTv = (TextView) convertView.findViewById(R.id.message_bean_time_tv);
            viewHolder.messageIsReadTv = (TextView) convertView.findViewById(R.id.message_bean_is_read_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (TextUtils.equals("0", messageBeanList.get(position).getIsRead())) {// 未读
            viewHolder.messageIsReadTv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.messageIsReadTv.setVisibility(View.GONE);
        }
        viewHolder.messageTitleTv.setText(messageBeanList.get(position).getTitle());
        viewHolder.messageTimeTv.setText(messageBeanList.get(position).getDateTime());


        if (!isVisibility) {
            viewHolder.messageCheckBox.setVisibility(View.GONE);
        } else {
            viewHolder.messageCheckBox.setVisibility(View.VISIBLE);
            // 根据isSelected来设置checkbox的选中状况
            viewHolder.messageCheckBox.setChecked(getIsSelected().get(position));
        }

        viewHolder.messageCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    getIsSelected().put(position, true);
                } else {
                    getIsSelected().put(position, false);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        CheckBox messageCheckBox;//是否选择
        TextView messageTitleTv;//消息标题
        TextView messageTimeTv;//消息时间
        TextView messageIsReadTv;//是否已读
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < messageBeanList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
}
