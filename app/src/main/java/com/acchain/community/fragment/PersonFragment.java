package com.acchain.community.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.acchain.community.R;
import com.acchain.community.arouter.ARouterConst;
import com.acchain.community.base.BaseFragment;
import com.acchain.community.contract.PersonContract;
import com.acchain.community.presenter.PersonPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;

/**
 * function---- PersonFragment
 * <p>
 * Created(Gradle default create) by MNLIN on 2017/12/20 10:49:35 (+0000).
 */
@Route(path = ARouterConst.Fragment_PersonFragment)
public class PersonFragment extends BaseFragment<PersonPresenter> implements PersonContract.View,Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_personInfo)
    GridView mRvPersonInfo;
    @BindView(R.id.gv_orders)
    GridView mGvOrders;
    @BindView(R.id.gv_ottServices)
    GridView mGvOttServices;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //toolbar初始化
        mToolbar.inflateMenu(R.menu.menu_fragment_person);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void injectSelf() {
        fragmentComponent.inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO: 2017/12/21
        return true;
    }
}