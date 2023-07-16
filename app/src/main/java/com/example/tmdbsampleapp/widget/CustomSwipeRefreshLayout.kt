package com.example.tmdbsampleapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

class CustomSwipeRefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    private var startGestureX = 0f
    private var startGestureY = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startGestureX = ev.x
                startGestureY = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
                if (abs(startGestureX - ev.x) > abs(startGestureY - ev.y)) return false
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}