package com.panda.touch.event.research.mvp.prensenter

import android.content.Context
import com.panda.mvp.design.pattern.base.presenter.BaseRxPresenter
import com.panda.touch.event.research.mvp.view.TouchEventResearchEntranceActivityView
import javax.inject.Inject

class TouchEventResearchEntranceActivityPresenter @Inject constructor(context: Context) :
    BaseRxPresenter<TouchEventResearchEntranceActivityView>() {
    private val mContext: Context = context
}