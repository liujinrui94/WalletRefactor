package com.chengxiang.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.ProfitTabFragmentAdapter;
import com.chengxiang.pay.framework.base.BaseFragment;
import com.chengxiang.pay.framework.encrypt.EncryptManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 9:02
 * @description: 本月收益
 */
public class TheseMonthProfitFragment extends BaseFragment {
    private EncryptManager encryptManager;
    private View inflate;
    private Context mContext;
    private Unbinder unbinder;
    public List<Fragment> fragments_month= new ArrayList<>();

    @BindView(R.id.fragment_these_month_profit_rgs)
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_these_month_profit, container, false);
            unbinder = ButterKnife.bind(this, inflate);
            initView();
        }
        return inflate;

    }
    private void initView() {
        fragments_month.add(TheseTradeMonthProfitFragment.newInstance("1"));
        fragments_month.add( TheseTradeMonthProfitFragment.newInstance("2"));
        fragments_month.add( TheseTradeMonthProfitFragment.newInstance("3"));
        ProfitTabFragmentAdapter mProfitTabFragmentAdapter= new ProfitTabFragmentAdapter(getActivity(), fragments_month, R.id.fragment_these_month_profit_fl, radioGroup, 0);
        mProfitTabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new ProfitTabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
            }
        });
    }

    public void initEncryptManager(EncryptManager encryptManager) {
        this.encryptManager = encryptManager;
    }
}
