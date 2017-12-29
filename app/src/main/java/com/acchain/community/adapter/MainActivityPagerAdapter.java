package com.acchain.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created on 2017/12/20
 * function : Viewpager适配器
 *
 * @author ACChain
 */

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> data;

    public MainActivityPagerAdapter(FragmentManager fm, List<? extends Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }
}
