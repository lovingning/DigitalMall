package com.acchain.community.dagger.component;

import com.acchain.community.dagger.scope.PerFragment;
import com.acchain.community.dagger.module.FragmentModule;
import com.acchain.community.fragment.FriendsFragment;
import com.acchain.community.fragment.IndexFragment;
import com.acchain.community.fragment.PersonFragment;
import com.acchain.community.fragment.ShoppingCartFragment;
import com.acchain.community.fragment.WalletFragment;

import dagger.Component;

/**
 * 功能----碎片组件,用于注入dagger
 * <p>
 * Created by ACChain on 2017/9/23.
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    void inject(FriendsFragment friendsFragment);

    void inject(IndexFragment indexFragment);

    void inject(PersonFragment personFragment);

    void inject(WalletFragment walletFragment);

    void inject(ShoppingCartFragment shoppingCartFragment);
}
