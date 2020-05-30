package com.panda.custom.view.research.mvp.prensenter

import android.content.Context
import com.panda.custom.view.research.mvp.view.RabbitViewActivityView
import com.panda.mvp.design.pattern.base.presenter.BaseRxPresenter
import javax.inject.Inject

class RabbitViewActivityPresenter @Inject constructor(context: Context) :
    BaseRxPresenter<RabbitViewActivityView>() {
    private val mContext: Context = context
}