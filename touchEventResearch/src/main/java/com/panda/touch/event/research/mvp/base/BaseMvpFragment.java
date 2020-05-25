package com.panda.touch.event.research.mvp.base;

import com.panda.mvp.design.pattern.base.AbstractMvpFragment;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;
import com.panda.touch.event.research.TouchEventResearchApplication;
import com.panda.touch.event.research.mvp.componet.DaggerFragmentComponent;
import com.panda.touch.event.research.mvp.componet.FragmentComponent;

/**
 * @author Pearce
 * @date 08/13/2019
 * @description
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends AbstractMvpFragment<T> {
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent
                .builder()
                .appComponent(TouchEventResearchApplication.Companion.getApplication().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }
}