package com.chengxiang.pay.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.utils.ToastUtils;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/4 16:09
 * @description: 更多服务
 */

public class MoreServiceFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private View inflate;
    private ImageView messageNoReadShowIv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_more_service, container, false);
            initView();
        }
        return inflate;

    }

    private void initView() {
        TextView title = (TextView) inflate.findViewById(R.id.action_bar_title_tv);
        title.setText(getResources().getString(R.string.more_service));
        inflate.findViewById(R.id.action_bar_left_iv).setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseBean.getUnReadNum() > 0) {
            messageNoReadShowIv.setVisibility(View.VISIBLE);
        } else {
            messageNoReadShowIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                ToastUtils.showShortToast("按键错误，请重试！！！");
        }
    }
}
