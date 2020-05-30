package com.panda.custom.view.research.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.panda.custom.view.research.R

class RabbitView : View {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    var mRabbitX = 290f // 兔子显示位置的X坐标
    var mRabbitY = 130f // 兔子显示位置的Y坐标

    private val mPaint = Paint() // 创建并实例化Paint的对象

    private val mRabbitBitmap: Bitmap = BitmapFactory.decodeResource(
        resources,
        R.mipmap.mipmap_icon_rabbit
    ) // 根据图片生成位图对象

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mRabbitBitmap, mRabbitX, mRabbitY, mPaint) // 绘制小兔子
    }

    fun release() {
        if (!mRabbitBitmap.isRecycled) { // 判断图片是否回收
            mRabbitBitmap.recycle() // 强制回收图片
        }
    }
}