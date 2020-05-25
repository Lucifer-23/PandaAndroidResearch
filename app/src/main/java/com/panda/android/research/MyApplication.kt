package com.panda.android.research

import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.panda.android.research.mvp.componet.AppComponent
import com.panda.android.research.mvp.componet.DaggerAppComponent
import com.panda.mvp.design.pattern.base.AbstractMvpApplication
import com.panda.mvp.design.pattern.base.module.AppModule
import com.panda.touch.event.research.TouchEventResearchApplication

class MyApplication : AbstractMvpApplication() {
    companion object {
        private var mApplication: MyApplication? = null;

        fun getApplication(): MyApplication? {
            return mApplication
        }
    }

    private var mAppComponent: AppComponent? = null

    override fun init() {
        // 初始化Log库
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.INFO)

        mAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        // Initialized modules
        TouchEventResearchApplication().init(this)
    }

    override fun initInMainProcess() {
        mApplication = this
    }

    fun getAppComponent(): AppComponent? {
        return mAppComponent
    }
}