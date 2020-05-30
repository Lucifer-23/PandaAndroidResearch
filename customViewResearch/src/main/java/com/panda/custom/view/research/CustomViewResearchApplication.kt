package com.panda.custom.view.research

import android.content.Context
import com.panda.custom.view.research.mvp.componet.AppComponent
import com.panda.custom.view.research.mvp.componet.DaggerAppComponent
import com.panda.mvp.design.pattern.base.module.AppModule

class CustomViewResearchApplication {
    companion object {
        private var mApplication: CustomViewResearchApplication? = null;

        fun getApplication(): CustomViewResearchApplication? {
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