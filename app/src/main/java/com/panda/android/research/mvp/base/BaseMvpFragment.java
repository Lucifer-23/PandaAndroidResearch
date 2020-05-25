package com.panda.android.research.mvp.base;

import com.panda.android.research.MyApplication;
import com.panda.android.research.mvp.componet.DaggerFragmentComponent;
import com.panda.android.research.mvp.componet.FragmentComponent;
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
                .appComponent(MyApplication.Companion.getApplication().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }
}