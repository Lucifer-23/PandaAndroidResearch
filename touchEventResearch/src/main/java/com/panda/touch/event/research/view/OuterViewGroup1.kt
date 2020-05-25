package com.panda.touch.event.research.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class OuterViewGroup1 : FrameLayout {
    private var mCount = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var action = ""
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        val result = super.dispatchTouchEvent(ev)

        Log.e(
            "Panda " + javaClass.simpleName,
            String.format("                   dispatchTouchEvent        %s %b", action, result)
        )

        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var action = ""
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        val result = super.onInterceptTouchEvent(ev)

        Log.e(
            "Panda " + javaClass.simpleName,
            String.format("                   onInterceptTouchEvent     %s %b", action, result)
        )

        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        val result = super.onTouchEvent(event)

        Log.e(
            "Panda " + javaClass.simpleName,
            String.format("                   onTouchEvent              %s %b", action, result)
        )

        return result
    }
}