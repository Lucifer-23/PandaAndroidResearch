package com.panda.custom.view.research.mvp.prensenter

import android.content.Context
import com.panda.basement.mvp.presenter.TopicListPresenter
import com.panda.custom.view.research.mvp.view.CustomViewResearchEntranceActivityView
import javax.inject.Inject

class CustomViewResearchEntranceActivityPresenter @Inject constructor(context: Context) :
    TopicListPresenter<CustomViewResearchEntranceActivityView>(context)