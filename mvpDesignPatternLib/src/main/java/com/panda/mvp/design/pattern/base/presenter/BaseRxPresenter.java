package com.panda.mvp.design.pattern.base.presenter;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.panda.mvp.design.pattern.base.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public class BaseRxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected Logger mLogger;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(T view) {
        mView = view;
        mLogger = XLog
                .b()
                .st(2)
                .t()
                .tag(this.getClass().getSimpleName()).build();
    }

    @Override
    public void destroy() {
        mView = null;
        unSubscribe();
    }

    protected void unSubscribe() {
        mCompositeDisposable.clear();
    }

    public void addSubscribe(Disposable subscription) {
        mCompositeDisposable.add(subscription);
    }
}