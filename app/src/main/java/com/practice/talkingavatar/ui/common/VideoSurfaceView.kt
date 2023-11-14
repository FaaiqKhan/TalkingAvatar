package com.practice.talkingavatar.ui.common

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceView

class VideoSurfaceView : SurfaceView {

    private var inOtherShape = false
    private var shapePath: Path? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchDraw(canvas: Canvas) {
        if (inOtherShape && shapePath != null) canvas.clipPath(shapePath!!)
        super.dispatchDraw(canvas)
    }

    fun cropCircle(centerX: Float, centerY: Float, radius: Int) {
        shapePath = Path()
        shapePath!!.addCircle(centerX, centerY, radius.toFloat(), Path.Direction.CW)
    }

    fun setOtherShape(inOtherShape: Boolean) {
        this.inOtherShape = inOtherShape
        invalidate()
    }
}