package com.chengxiang.pay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.ProfitTabFragmentAdapter;
import com.chengxiang.pay.fragment.AccountBalanceFragment;
import com.chengxiang.pay.fragment.ExpandUserFragment;
import com.chengxiang.pay.fragment.TheseMonthProfitFragment;
import com.chengxiang.pay.fragment.UserUpgradeFragment;
import com.chengxiang.pay.framework.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/10 15:26
 * @description: 收益选择页
 */
public class ProfitTabActivity extends BaseActivity {
    public List<Fragment> fragments = new ArrayList<>();
    private AccountBalanceFragment mAccountBalanceFragment = new AccountBalanceFragment();
    private ExpandUserFragment mExpandUserFragment = new ExpandUserFragment();
    private TheseMonthProfitFragment mTheseMonthProfitFragment = new TheseMonthProfitFragment();
    private UserUpgradeFragment mUserUpgradeFragment = new UserUpgradeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_tab);
        int currentOption = getIntent().getIntExtra("typeClass", 0);
        showTitle(currentOption);
        mAccountBalanceFragment.initEncryptManager(encryptManager);
        mTheseMonthProfitFragment.initEncryptManager(encryptManager);
        mUserUpgradeFragment.initEncryptManager(encryptManager);
        fragments.add(mAccountBalanceFragment);
        fragments.add(mExpandUserFragment);
        fragments.add(mTheseMonthProfitFragment);
        fragments.add(mUserUpgradeFragment);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.main_radio);
        radioGroup.check(radioGroup.getChildAt(currentOption).getId());
        ProfitTabFragmentAdapter tabAdapter = new ProfitTabFragmentAdapter(ProfitTabActivity.this, fragments, R.id.tab_content, radioGroup, currentOption);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new ProfitTabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                showTitle(index);
            }
        });
    }

    private void showTitle(int currentOption) {
        switch (currentOption) {
            case 0:
                setTitle("账户余额");
                break;
            case 1:
                setTitle("我的客户");
                break;
            case 2:
                setTitle("本月收益");
                break;
            case 3:
                setTitle("提升收益");
                break;
        }
    }
}
