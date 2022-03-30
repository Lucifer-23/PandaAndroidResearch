package com.panda.aes.encrypt.research.mvp.base;

import com.panda.aes.encrypt.research.AesEncryptResearchApplication;
import com.panda.aes.encrypt.research.mvp.componet.DaggerFragmentComponent;
import com.panda.aes.encrypt.research.mvp.componet.FragmentComponent;
import com.panda.mvp.design.pattern.base.AbstractMvpFragment;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;

/**
 * @author Pearce
 * @date 08/13/2019
 * @description
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends AbstractMvpFragment<T> {
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent
                .builder()
                .appComponent(AesEncryptResearchApplication.Companion.getApplication().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }
}