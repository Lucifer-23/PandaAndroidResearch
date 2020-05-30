package com.panda.custom.view.research.activity

import android.os.Bundle
import com.panda.custom.view.research.R
import com.panda.custom.view.research.mvp.base.BaseMvpActivity
import com.panda.custom.view.research.mvp.prensenter.RabbitViewActivityPresenter
import com.panda.custom.view.research.mvp.view.RabbitViewActivityView
import kotlinx.android.synthetic.main.layout_activity_rabbit_view.*

class RabbitViewActivity :
    BaseMvpActivity<RabbitViewActivityPresenter>(),
    RabbitViewActivityView {

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(javaClass.simpleName)
        mTitle.setTitleTextColor(R.color.color_000000)
        mTitle.setTitleTextSize(resources.getDimension(R.dimen.dimens_14sp))
        mTitle.setLeftIconClickListener { finish() }

        id_activity_rabbit_view_rabbit.setOnTouchListener { _, event ->
            id_activity_rabbit_view_rabbit.mRabbitX = event.x
            id_activity_rabbit_view_rabbit.mRabbitY = event.y
            id_activity_rabbit_view_rabbit.invalidate()
            true
        }
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_activity_rabbit_view
    }

    override fun onDestroy() {
        id_activity_rabbit_view_rabbit.release()
        super.onDestroy()
    }
}