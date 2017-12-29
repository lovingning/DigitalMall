package com.acchain.community.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.acchain.community.R;
import com.acchain.community.arouter.ARouterConst;
import com.acchain.community.base.BaseFragment;
import com.acchain.community.contract.FriendsContract;
import com.acchain.community.presenter.FriendsPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;

/**
 * function---- FriendsFragment
 * <p>
 * Created(Gradle default create) by MNLIN on 2017/12/20 10:49:19 (+0000).
 */
@Route(path = ARouterConst.Fragment_FriendsFragment)
public class FriendsFragment extends BaseFragment<FriendsPresenter> implements FriendsContract.View, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.xrv_friends)
//    XRecyclerView mXrvFriends;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_friends;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initData(Bundle savedInstanceState) {
        //初始化toolbar,设置顶部padding值,设置menu
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_add_white_24dp));
        mToolbar.inflateMenu(R.menu.menu_fragment_friends);
        ((MenuBuilder) mToolbar.getMenu()).setOptionalIconsVisible(true);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void injectSelf() {
        fragmentComponent.inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO: 2017/12/21 处理menu事件
        return true;
    }

}