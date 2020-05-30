package com.panda.custom.view.research.mvp.componet;

import android.app.Activity;

import com.panda.custom.view.research.activity.CustomViewResearchEntranceActivity;
import com.panda.custom.view.research.activity.RabbitViewActivity;
import com.panda.mvp.design.pattern.base.module.ActivityModule;
import com.panda.mvp.design.pattern.base.module.scope.ActivityScope;

import dagger.Component;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(CustomViewResearchEntranceActivity customViewResearchEntranceActivity);

    void inject(RabbitViewActivity rabbitViewActivity);
}