package com.panda.aes.encrypt.research

import android.content.Context
import com.panda.aes.encrypt.research.mvp.componet.AppComponent
import com.panda.aes.encrypt.research.mvp.componet.DaggerAppComponent
import com.panda.mvp.design.pattern.base.module.AppModule

class AesEncryptResearchApplication {
    companion object {
        private var mApplication: AesEncryptResearchApplication? = null;

        fun getApplication(): AesEncryptResearchApplication? {
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