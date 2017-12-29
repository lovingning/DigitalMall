package com.acchain.community.base;

/**
 * 功能----基础中间键,mvp模式
 * <p>
 * Created by ACChain on 2017/9/22.
 */

public abstract class BasePresenter<T extends BaseView> {
   protected T mView;
}
