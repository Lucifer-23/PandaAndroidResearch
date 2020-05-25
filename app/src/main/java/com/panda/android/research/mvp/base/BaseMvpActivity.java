package com.panda.android.research.mvp.base;

import com.panda.android.research.MyApplication;
import com.panda.android.research.R;
import com.panda.android.research.mvp.componet.ActivityComponent;
import com.panda.android.research.mvp.componet.DaggerActivityComponent;
import com.panda.mvp.design.pattern.base.AbstractMvpActivity;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends AbstractMvpActivity<T> {

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.Companion.getApplication().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected void initToolbar(String title) {
        mTitle = findViewById(R.id.id_panda_android_research_title_view);
        setTitle(title);
    }
}