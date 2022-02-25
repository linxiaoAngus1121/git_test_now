package com.example.gittest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *
 * 自定的View最好不要超过父控件的大小，这样才能保证自己能在父控件中完整显示
 * 自定的View（如果是ViewGroup）的子控件最好不要超过自己的大小，这样才能保证子控件显示完整
 * 如果明确为View指定了尺寸，最好按照指定的尺寸设置
 * @author linxiao date: 2022年2月25日, 0025 10:29
 */
class TestMeasureMode : View {

    companion object {
         const val TAG = "000"
    }

    private val MAXWIDTH = 70.px

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr)


    /**
     * MeasureSpec是父控件提供给子View的一个参数，作为设定自身大小参考，只是个参考，要多大，还是View自己说了
     * 它这个值的意思应该是父view对子view,
     * 比如EXACTLY，是指父view对子view是想他是这个值,子view你得听父view的，对应match_parent或者一个具体的值
     * 比如AT_MOST，是指父view对子view是最大是这个值,你可以比他小，但是不能比他大，对应wrap_content
     * 比如UNSPECIFIED，是指父view对子view没有限制，可以是子 View想要的任何尺寸
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var finalWidth = widthSize
        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                Log.d(TAG, "this is widthMode--EXACTLY")
            }
            MeasureSpec.AT_MOST -> {
                Log.d(TAG, "this is widthMode--AT_MOST")
//                finalWidth = MAXWIDTH.toInt()
            }
            else -> Log.d(TAG, "this is widthMode--UNSPECIFIED")
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> Log.d(TAG, "this is heightMode--EXACTLY")
            MeasureSpec.AT_MOST -> Log.d(TAG, "this is heightMode--AT_MOST")
            else -> Log.d(TAG, "this is heightMode--UNSPECIFIED")
        }
        setMeasuredDimension(finalWidth, heightSize)
    }
}