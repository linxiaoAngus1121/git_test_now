package com.example.gittest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年11月24日, 0024 10:14
 */
class MyFragmeLayout : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("000","this is MyFragmeLayout的onTouchEvent")
        return super.onTouchEvent(event)
    }
}