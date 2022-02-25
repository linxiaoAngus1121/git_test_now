package com.example.gittest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.marginStart

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年2月25日, 0025 10:51
 */
class ParentLayout : ViewGroup {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var cWidth = 0
        var startLeft: Int
        var startRight: Int
        var startTop: Int
        var startBottom: Int
        for (index in 0 until childCount) {     //until 不包含最后一个
            val child = getChildAt(index)
            val marginStart = child.marginStart
            //cWidth(上一个的宽度)+marginStart(开始的margin)+ (getChildAt(index - 1)?.marginEnd ?: 0)上一个的marginEnd
            // 因为这个是体现在startLeft的，所以是得在startLeft上做处理，不能放在startRight去处理
            startLeft = cWidth + marginStart + (getChildAt(index - 1)?.marginEnd ?: 0)
            startRight = startLeft + child.measuredWidth
            startTop = 0
            startBottom = startTop + child.measuredHeight
            cWidth += startRight
            child.layout(startLeft, startTop, startRight, startBottom)
        }
        Log.d(TestMeasureMode.TAG, "this is onLayout")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                Log.d(TestMeasureMode.TAG, "this is widthMode--EXACTLY")
            }
            MeasureSpec.AT_MOST -> {
                Log.d(TestMeasureMode.TAG, "this is widthMode--AT_MOST")
            }

        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                Log.d(TestMeasureMode.TAG, "this is heightMode--EXACTLY")
            }
            MeasureSpec.AT_MOST -> {
                Log.d(TestMeasureMode.TAG, "this is heightMode--AT_MOST")
            }

        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}