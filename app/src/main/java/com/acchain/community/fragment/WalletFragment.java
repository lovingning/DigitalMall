package com.acchain.community.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.acchain.community.R;
import com.acchain.community.arouter.ARouterConst;
import com.acchain.community.base.BaseFragment;
import com.acchain.community.contract.WalletContract;
import com.acchain.community.presenter.WalletPresenter;
import com.acchain.community.view.UnEnableSearchView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

/**
 * function---- WalletFragment
 * <p>
 * Created(Gradle default create) by MNLIN on 2017/12/20 10:48:55 (+0000).
 */
@Route(path = ARouterConst.Fragment_WalletFragment)
public class WalletFragment extends BaseFragment<WalletPresenter> implements WalletContract.View, Toolbar.OnMenuItemClickListener {

    /**
     * 搜索框
     */
    @BindView(R.id.sv_search)
    UnEnableSearchView mSvSearch;

    /**
     * toolbar工具栏
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * 主功能:包括转入,转出,燃料
     */
    @BindView(R.id.gv_mainFunction)
    GridView mGvMainFunction;

    /**
     * 数字资产类型数量
     */
    @BindView(R.id.tv_currencyAmount)
    TextView mTvCurrencyAmount;


    /**
     * 控制显示的关键布局
     */
    @BindView(R.id.ll_controlShow)
    LinearLayout mLlControlShow;


    /**
     * tabLayout控件,控制切换显示的数字资产
     */
    @BindView(R.id.tl_currency)
    TableLayout mTlCurrency;

    /**
     * 具体的资产列表
     */
    @BindView(R.id.xrv_currency)
    XRecyclerView mXrvCurrency;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //toolbar初始化
        mToolbar.inflateMenu(R.menu.menu_fragment_wallet);
        mToolbar.setOnMenuItemClickListener(this);

        // TODO 为controlShow设置上下的margin值,保证正常显示
        // TODO 设置toolbar高度,为底部的tablayout留白
    }

    @Override
    protected void injectSelf() {
        fragmentComponent.inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO: 2017/12/21 点击menu事件
        return false;
    }
}