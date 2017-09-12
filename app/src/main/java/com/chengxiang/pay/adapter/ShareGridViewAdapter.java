package com.chengxiang.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.ImageTextGridViewBean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/30 9:58
 * @description: 分享GridViewAdapter
 */


public class ShareGridViewAdapter extends BaseAdapter {
    private ArrayList<ImageTextGridViewBean> imageTextGridViewBeanList;
    private Context context;

    public ShareGridViewAdapter(Context context, ArrayList<ImageTextGridViewBean> imageTextGridViewBeanList) {
        this.context = context;
        this.imageTextGridViewBeanList = imageTextGridViewBeanList;
    }

    @Override
    public int getCount() {
        return imageTextGridViewBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageTextGridViewBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_share, null);
            viewHolder = new ViewHolder();
            viewHolder.gridItemTv = (TextView) convertView.findViewById(R.id.item_grid_share_tv);
            viewHolder.gridItemIv = (ImageView) convertView.findViewById(R.id.item_grid_share_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.gridItemTv.setText(imageTextGridViewBeanList.get(position).getDescribe());
        viewHolder.gridItemIv.setImageResource(imageTextGridViewBeanList.get(position).getImageId());
        return convertView;
    }

    private class ViewHolder {
        TextView gridItemTv;//GridView中文字
        ImageView gridItemIv;//GridView中图片
    }
}
