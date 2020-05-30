package com.panda.custom.view.research.mvp.base;

import com.panda.mvp.design.pattern.base.presenter.BasePresenter;

/**
 * @author Pearce
 * @date 09/25/2019
 * @description
 */
public abstract class BaseEventBusMvpActivity<T extends BasePresenter> extends BaseMvpActivity<T> {
    @Override
    protected void doRegisterEvent() {
        registerEvent();
    }

    @Override
    protected void onDestroy() {
        unregisterEvent();
        super.onDestroy();
    }
}