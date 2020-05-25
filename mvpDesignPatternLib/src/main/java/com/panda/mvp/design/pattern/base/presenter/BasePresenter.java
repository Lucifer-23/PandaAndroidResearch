package com.panda.mvp.design.pattern.base.presenter;

import com.panda.mvp.design.pattern.base.view.BaseView;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void destroy();
}