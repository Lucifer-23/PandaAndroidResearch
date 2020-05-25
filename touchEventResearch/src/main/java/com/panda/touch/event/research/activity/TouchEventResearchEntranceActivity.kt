package com.panda.touch.event.research.activity

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.panda.touch.event.research.R
import com.panda.touch.event.research.mvp.base.BaseMvpActivity
import com.panda.touch.event.research.mvp.prensenter.TouchEventResearchEntranceActivityPresenter
import com.panda.touch.event.research.mvp.view.TouchEventResearchEntranceActivityView

class TouchEventResearchEntranceActivity :
    BaseMvpActivity<TouchEventResearchEntranceActivityPresenter>(),
    TouchEventResearchEntranceActivityView {

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(javaClass.simpleName)
        mTitle.setTitleTextColor(R.color.color_000000)
        mTitle.setTitleTextSize(resources.getDimension(R.dimen.dimens_14sp))
        mTitle.setLeftIconClickListener { finish() }
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_activity_touch_event_research_entrance
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var action = ""
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        val result = super.dispatchTouchEvent(ev)

        Log.e(
            "Panda " + javaClass.simpleName,
            String.format("dispatchTouchEvent        %s %b", action, result)
        )

        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = ""
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        val result = super.onTouchEvent(event)

        Log.e(
            "Panda " + javaClass.simpleName,
            String.format("onTouchEvent              %s %b", action, result)
        )

        return result
    }
}