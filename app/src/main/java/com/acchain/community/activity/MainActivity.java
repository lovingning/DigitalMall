package com.acchain.community.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.acchain.community.R;
import com.acchain.community.adapter.MainActivityPagerAdapter;
import com.acchain.community.arouter.ARouterConst;
import com.acchain.community.base.BaseActivity;
import com.acchain.community.base.BaseFragment;
import com.acchain.community.contract.MainContract;
import com.acchain.community.fragment.FriendsFragment;
import com.acchain.community.fragment.IndexFragment;
import com.acchain.community.fragment.PersonFragment;
import com.acchain.community.fragment.ShoppingCartFragment;
import com.acchain.community.fragment.WalletFragment;
import com.acchain.community.presenter.MainPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ARouterConst.Activity_MainActivity)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.bnve_navigation)
    BottomNavigationViewEx mBnveNavigation;
    @BindView(R.id.vp_fragments)
    ViewPager mVpFragments;
    @BindView(R.id.iv_statusBarPlace)
    ImageView mIvStatusBarPlace;

    /**
     * 上次点击back时间
     */
    private long lastPressBackTime;

    /**
     * 适配器
     */
    private MainActivityPagerAdapter pagerAdapter;


    /**
     * 碎片布局:
     * 首页;IndexFragment
     * 钱包;WalletFragment
     * 朋友;FriendsFragment
     * 购物车:ShoppingCartFragment
     * 我的;PersonFragment
     */
    private List<BaseFragment> fragments;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectSelf() {
        activityComponent.inject(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //为顶部状态栏进行填充
        mIvStatusBarPlace.getLayoutParams().height = BarUtils.getStatusBarHeight();

        //关闭所有的动画效果,让一次显示完全
        mBnveNavigation.enableAnimation(false);
        mBnveNavigation.enableShiftingMode(false);
        mBnveNavigation.enableItemShiftingMode(false);

        //创建碎片实例
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new WalletFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new PersonFragment());

        //初始化ViewPager
        pagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager(), fragments);

        mVpFragments.setAdapter(pagerAdapter);

        //绑定ViewPager和Navigation
        bindNavigationAndViewPager();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 添加到window时,并没有进行测量过程
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        //设置中间按钮不可点击
        //mBnveNavigation.getBottomNavigationItemView(2).setClickable(false);
    }

    /**
     * 绑定ViewPager和Navigation
     * <p>
     * 当viewpager滑动时改变navigation
     * 当navigation点击时改变viewpager
     */
    private void bindNavigationAndViewPager() {
        mBnveNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.main_navigation_home:
                        position = 0;
                        break;
                    case R.id.main_navigation_wallet:
                        position = 1;
                        break;
                    case R.id.main_navigation_friends:
                        position = 2;
                        break;
                    case R.id.main_navigation_shopping_cart:
                        position = 3;
                        break;
                    case R.id.main_navigation_person:
                        position = 4;
                        break;
                    default:
                        return false;
                }
                if (previousPosition != position) {
                    mVpFragments.setCurrentItem(position, false);
                    previousPosition = position;
                }

                return true;
            }
        });

        mVpFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBnveNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressBackTime > 1000) {
            showToast("再按一次退出程序");
            lastPressBackTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 获取一个view距离屏幕底部的高度
     * <p>
     * 这个方法执行的前提时view已经布局测量完成
     */
    private int getMarginBottomHeight(View view) {
        int marginTop = view.getTop() + view.getHeight();
        while (view.getParent() instanceof View) {
            view = (View) view.getParent();
            marginTop += view.getTop();
        }

        //屏幕高度-view距离顶部距离-view高度=view距离底部距离
        return ScreenUtils.getScreenHeight() - marginTop;
    }
}
