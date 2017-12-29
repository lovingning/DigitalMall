package com.acchain.community.dagger.component;

import com.acchain.community.activity.MainActivity;
import com.acchain.community.activity.SelectFunctionActivity;
import com.acchain.community.dagger.module.ActivityModule;
import com.acchain.community.dagger.scope.PerActivity;

import dagger.Component;

/**
 * 功能----activity组件,提供清单文件
 * <p>
 * Created by ACChain on 2017/9/22.
 */
@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(SelectFunctionActivity activity);

    void inject(MainActivity mainActivity);
}
