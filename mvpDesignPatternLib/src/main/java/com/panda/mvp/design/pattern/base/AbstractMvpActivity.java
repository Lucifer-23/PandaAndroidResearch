package com.panda.mvp.design.pattern.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.panda.mvp.design.pattern.base.module.ActivityModule;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;
import com.panda.mvp.design.pattern.base.view.BaseView;

import javax.inject.Inject;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class AbstractMvpActivity<T extends BasePresenter> extends BaseActivity
        implements BaseView {

    @Inject
    protected T mPresenter;

    protected abstract void inject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
        super.onDestroy();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}