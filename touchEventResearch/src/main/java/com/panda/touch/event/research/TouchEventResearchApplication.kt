package com.panda.touch.event.research

import android.content.Context
import com.panda.mvp.design.pattern.base.module.AppModule
import com.panda.touch.event.research.mvp.componet.AppComponent
import com.panda.touch.event.research.mvp.componet.DaggerAppComponent

class TouchEventResearchApplication {
    companion object {
        private var mApplication: TouchEventResearchApplication? = null;

        fun getApplication(): TouchEventResearchApplication? {
            return mApplication
        }
    }

    private var mAppComponent: AppComponent? = null

    fun init(context: Context) {

        mApplication = this
        mAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }

    fun getAppComponent(): AppComponent? {
        return mAppComponent
    }
}