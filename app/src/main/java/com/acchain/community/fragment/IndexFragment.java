package com.acchain.community.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.acchain.community.R;
import com.acchain.community.arouter.ARouterConst;
import com.acchain.community.base.BaseFragment;
import com.acchain.community.contract.IndexContract;
import com.acchain.community.presenter.IndexPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * function---- IndexFragment
 * <p>
 * Created(Gradle default create) by MNLIN on 2017/12/20 10:48:47 (+0000).
 */
@Route(path = ARouterConst.Fragment_IndexFragment)
public class IndexFragment extends BaseFragment<IndexPresenter> implements IndexContract.View, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.sv_search)
    SearchView mSvSearch;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //初始化toolbar,设置顶部padding值
        mToolbar.inflateMenu(R.menu.menu_fragment_index);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void injectSelf() {
        fragmentComponent.inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.action_message) {
            // TODO: 2017/12/21 跳转到消息界面
        }
        return true;
    }

    @OnClick({R.id.tv_location, R.id.sv_search})
    public void onViewClicked(View view) {
        Logger.e("view:" + view.getId());
        // TODO: 2017/12/21 处理顶部点击事件
        switch (view.getId()) {
            //地址切换
            case R.id.tv_location:
                break;
            //搜索界面
            case R.id.sv_search:
                break;
        }
    }
}