package com.example.gittest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.util.logging.Logger

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年11月24日, 0024 10:22
 */
class MyCos: ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("000","this is ConstraintLayout的onTouchEvent")
        return super.onTouchEvent(event)
    }
}
