package com.panda.android.research.mvp.prensenter

import android.content.Context
import com.panda.android.research.mvp.view.MainActivityView
import com.panda.basement.mvp.presenter.TopicListPresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(context: Context) :
    TopicListPresenter<MainActivityView>(context)