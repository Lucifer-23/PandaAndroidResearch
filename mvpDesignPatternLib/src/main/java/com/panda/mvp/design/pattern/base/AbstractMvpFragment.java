package com.panda.mvp.design.pattern.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.panda.mvp.design.pattern.base.module.FragmentModule;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;
import com.panda.mvp.design.pattern.base.view.BaseView;

import javax.inject.Inject;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class AbstractMvpFragment<T extends BasePresenter> extends BaseFragment
        implements BaseView {

    @Inject
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }
}