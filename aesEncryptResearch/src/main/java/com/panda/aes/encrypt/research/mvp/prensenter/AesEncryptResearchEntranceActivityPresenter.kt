package com.panda.aes.encrypt.research.mvp.prensenter

import android.content.Context
import com.panda.aes.encrypt.research.mvp.view.AesEncryptResearchEntranceActivityView
import com.panda.basement.mvp.presenter.TopicListPresenter
import javax.inject.Inject

class AesEncryptResearchEntranceActivityPresenter @Inject constructor(context: Context) :
    TopicListPresenter<AesEncryptResearchEntranceActivityView>(context)