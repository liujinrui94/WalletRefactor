package com.chengxiang.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.fragment.MineFragment;
import com.chengxiang.pay.fragment.MoreServiceFragment;
import com.chengxiang.pay.fragment.ProfitFragment;
import com.chengxiang.pay.fragment.WalletFragment;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.base.BaseApplication;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.chengxiang.pay.framework.widget.CommonDialog;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/4 17:09
 * @description: 主页
 */


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView mWalletTv;
    private TextView mProfitTv;
    private TextView mMoreServiceTv;
    private TextView mMineTv;
    private ImageView mWalletIv;
    private ImageView mProfitIv;
    private ImageView mMoreServiceIv;
    private ImageView mMineIv;
    private WalletFragment walletFragment;//诚享钱包
    private ProfitFragment profitFragment;//收益
    private MoreServiceFragment moreServiceFragment;//更多服务
    private MineFragment mineFragment;//我的
    private FragmentManager fManager;

    private long exitTime = 0;//标记退出时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
    }

    /**
     * UI组件初始化与事件绑定
     */
    private void bindViews() {
        mWalletTv = (TextView) findViewById(R.id.tab_wallet_tv);
        mProfitTv = (TextView) findViewById(R.id.tab_profit_tv);
        mMoreServiceTv = (TextView) findViewById(R.id.tab_more_service_tv);
        mMineTv = (TextView) findViewById(R.id.tab_mine_tv);
        mWalletIv = (ImageView) findViewById(R.id.tab_wallet_iv);
        mProfitIv = (ImageView) findViewById(R.id.tab_profit_iv);
        mMoreServiceIv = (ImageView) findViewById(R.id.tab_more_service_iv);
        mMineIv = (ImageView) findViewById(R.id.tab_mine_iv);
        LinearLayout mWalletLL = (LinearLayout) findViewById(R.id.tab_wallet_ll);
        LinearLayout mProfitLL = (LinearLayout) findViewById(R.id.tab_profit_ll);
        LinearLayout mMoreServiceLL = (LinearLayout) findViewById(R.id.tab_more_service_ll);
        LinearLayout mMineLL = (LinearLayout) findViewById(R.id.tab_mine_ll);
        mWalletLL.setOnClickListener(this);
        mProfitLL.setOnClickListener(this);
        mMoreServiceLL.setOnClickListener(this);
        mMineLL.setOnClickListener(this);
        mWalletLL.performClick(); //模拟一次点击，既进去后选择第一项
    }

    //重置所有文本的选中状态
    private void clearSelected() {
        mWalletTv.setSelected(false);
        mWalletIv.setSelected(false);
        mProfitTv.setSelected(false);
        mProfitIv.setSelected(false);
        mMoreServiceTv.setSelected(false);
        mMoreServiceIv.setSelected(false);
        mMineTv.setSelected(false);
        mMineIv.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (walletFragment != null) {
            fragmentTransaction.hide(walletFragment);
        }

        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }

        if (profitFragment != null) {
            fragmentTransaction.hide(profitFragment);
        }

        if (moreServiceFragment != null) {
            fragmentTransaction.remove(moreServiceFragment);
            moreServiceFragment = null;
        }

    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(BaseBean.getOpenId()) && TextUtils.isEmpty(BaseBean.getPhoneNum())
                && v.getId() != R.id.tab_wallet_ll) {
            wechatBindPhoneNum();
        } else {
            FragmentTransaction fTransaction = fManager.beginTransaction();
            hideAllFragment(fTransaction);
            switch (v.getId()) {
                case R.id.tab_wallet_ll:
                    clearSelected();
                    mWalletTv.setSelected(true);
                    mWalletIv.setSelected(true);
                    if (walletFragment == null) {
                        walletFragment = new WalletFragment();
                        fTransaction.add(R.id.main_frame_layout, walletFragment);
                    } else {
                        fTransaction.show(walletFragment);
                    }
                    walletFragment.initEncryptManager(encryptManager);
                    break;
                case R.id.tab_profit_ll:
                    clearSelected();
                    mProfitTv.setSelected(true);
                    mProfitIv.setSelected(true);
                    if (profitFragment == null) {
                        profitFragment = new ProfitFragment();
                        fTransaction.add(R.id.main_frame_layout, profitFragment);
                    } else {
                        fTransaction.show(profitFragment);
                    }
                    profitFragment.initEncryptManager(encryptManager);
                    break;
                case R.id.tab_more_service_ll:
                    clearSelected();
                    mMoreServiceTv.setSelected(true);
                    mMoreServiceIv.setSelected(true);
                    if (moreServiceFragment == null) {
                        moreServiceFragment = new MoreServiceFragment();
                        fTransaction.add(R.id.main_frame_layout, moreServiceFragment);
                    } else {
                        fTransaction.show(moreServiceFragment);
                    }
                    break;
                case R.id.tab_mine_ll:
                    clearSelected();
                    mMineTv.setSelected(true);
                    mMineIv.setSelected(true);
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                        fTransaction.add(R.id.main_frame_layout, mineFragment);
                    } else {
                        fTransaction.show(mineFragment);
                    }
                    mineFragment.initEncryptManager(encryptManager);
                    break;
            }
            fTransaction.commit();
        }
    }

    /**
     * 微信绑定手机号
     */

    public void wechatBindPhoneNum() {
        final CommonDialog commonDialog = new CommonDialog(this);
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
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    //双击退出方法
    public void exitBy2Click() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showCenterToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            BaseApplication.getInstance().AppExit();
        }
    }
}
