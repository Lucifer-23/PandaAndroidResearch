package com.panda.touch.event.research.mvp.base;

import com.panda.mvp.design.pattern.base.AbstractMvpActivity;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;
import com.panda.touch.event.research.R;
import com.panda.touch.event.research.TouchEventResearchApplication;
import com.panda.touch.event.research.mvp.componet.ActivityComponent;
import com.panda.touch.event.research.mvp.componet.DaggerActivityComponent;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends AbstractMvpActivity<T> {

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(TouchEventResearchApplication.Companion.getApplication().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected void initToolbar(String title) {
        mTitle = findViewById(R.id.id_touch_event_research_title_view);
        setTitle(title);
    }
}