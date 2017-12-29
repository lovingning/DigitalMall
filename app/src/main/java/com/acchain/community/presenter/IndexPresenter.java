package com.acchain.community.presenter;


import com.acchain.community.base.BasePresenter;
import com.acchain.community.contract.IndexContract;
import com.acchain.community.fragment.IndexFragment;

import javax.inject.Inject;

/**
 * function---- IndexPresenter
 * <p>
 * Created(Gradle default create) by MNLIN on 2017/12/20 10:48:47 (+0000).
 */
public class IndexPresenter extends BasePresenter<IndexFragment> implements IndexContract.Presenter{
    @Inject
    public IndexPresenter() {}

}