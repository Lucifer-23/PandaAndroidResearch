package com.panda.mvp.design.pattern.base.module;

import android.app.Activity;

import com.panda.mvp.design.pattern.base.module.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }
}