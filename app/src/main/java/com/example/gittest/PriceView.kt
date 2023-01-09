package com.example.gittest

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import kotlin.math.sqrt

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2023年1月5日, 0005 9:46
 */
private val Float.px: Float
    get() {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
    }

class PriceView : View {
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val mBackGroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#C7CBCF")
    }
    private val mProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
    }

    private val mCircleWhitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 3f.px
    }

    private val mCircleBluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val mTextBackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val mTrianglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
    }


    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 10f.px
        color = Color.WHITE
    }

    //进度条的高度
    private val progressHeight = 10f.px

    //内部留有的边距
    private val mInnerPadding = 18f.px

    //进度条开始的顶部位置
    private val startTop = paddingTop + 70f.px

    //进度条开始的底部位置
    private val startBottom = startTop + progressHeight

    //圆的半径
    private val mCircleRadius = 15f.px

    //左圆的圆心位置
    private var mLeftCirlcleX = mInnerPadding
    private var mLeftCirlcleY = startBottom - progressHeight / 2

    //背景的圆角矩形
    private val mBackGroundRect = RectF()

    //进度的圆角矩形
    private val mProgressRect = RectF()

    //上面那个指针的背景
    private val mTextBackRect = RectF()

    //指针的背景的高度
    private val mTextBackgroundHeight = 25f.px

    //三角形的path
    val mTrianglePath = Path()

    //边长
    val triangleWidth = 15f.px

    // 三角形的高度，通过勾股定理算出来 a*a + b*b =c*c
    val traingleHeight = sqrt((triangleWidth * triangleWidth - mCircleRadius / 2 * mCircleRadius / 2).toDouble())

    val count = 80

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = getMode(widthMeasureSpec)
        val widthSize = getSize(widthMeasureSpec)

        val heightMode = getMode(heightMeasureSpec)
        val heightSize = getSize(heightMeasureSpec)

        val mExpectWidth = measuredWidth - paddingStart - paddingEnd - mInnerPadding
        val mExpectHeight = paddingTop + 70f.px + mInnerPadding * 2 + paddingBottom
        val mWidth = when (widthMode) {
            EXACTLY -> {    //如果是固定的尺寸
                widthSize.toFloat()
            }
            UNSPECIFIED -> {   //没有限制，但是一般可能少数会出现这个情况
                mExpectWidth
            }
            else -> {        //如果是WrapContent使用计算的宽度
                mExpectWidth.coerceAtMost(widthSize.toFloat()) //取小的那个
            }
        }

        val mHeight = when (heightMode) {
            EXACTLY -> {
                heightSize.toFloat() - mInnerPadding
            }
            UNSPECIFIED -> {   //没有限制,但是一般可能少数会出现这个情况
                mExpectHeight
            }
            else -> {       //取小的那个 //如果是WrapContent使用计算的宽度
                mExpectHeight.coerceAtMost(heightSize.toFloat())
            }
        }
        setMeasuredDimension(mWidth.toInt(), mHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.RED)

        drawBackProgress(canvas)

        drawProgress(canvas)

        drawLeftCircle(canvas)

        drawText(canvas)

        drawTriangle(canvas)

    }

    private fun drawProgress(canvas: Canvas) {
        mProgressRect.set(mBackGroundRect.left, startTop, mLeftCirlcleX, startBottom)
        canvas.drawRoundRect(mProgressRect, 50f, 50f, mProgressPaint)
    }

    private fun drawTriangle(canvas: Canvas) {
        mTrianglePath.reset()
        mTrianglePath.moveTo((mTextBackRect.centerX() - triangleWidth / 2), mTextBackRect.bottom)
        mTrianglePath.lineTo((mTextBackRect.centerX() + triangleWidth / 2), mTextBackRect.bottom)
        mTrianglePath.lineTo(mTextBackRect.centerX(), (mTextBackRect.bottom + traingleHeight).toFloat())
        mTrianglePath.close()
        canvas.drawPath(mTrianglePath, mTrianglePaint)
    }

    private fun drawText(canvas: Canvas) {
        mTextBackRect.set(mLeftCirlcleX - mCircleRadius, mInnerPadding, mLeftCirlcleX + mCircleRadius, mInnerPadding + mTextBackgroundHeight)
        canvas.drawRect(mTextBackRect, mTextBackPaint)

        val totalLength = mBackGroundRect.right - mBackGroundRect.left    //这样算出来的，才是中间那个灰色圆角矩形的长度

        //假设 总共60份，总共的宽度是1000 ，那一份就是60/1000=0.06，现在的长度mLeftCirlcleX=500，所以现在的值是500*0.06=30
        val oneLength = count / totalLength //mProgressRect.right-mProgressRect.left 这里要减掉left,才是当前的长度
        val fl = String.format("%.1f", (mProgressRect.right - mProgressRect.left) * oneLength)

        val fontMetrics = mTextPaint.fontMetricsInt
        val baseline = (mTextBackRect.bottom + mTextBackRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(fl, mTextBackRect.centerX(), baseline, mTextPaint)
    }

    private fun drawLeftCircle(canvas: Canvas) {
        canvas.drawCircle(mLeftCirlcleX, mLeftCirlcleY, mCircleRadius, mCircleWhitePaint)

        canvas.drawCircle(mLeftCirlcleX, mLeftCirlcleY, mCircleRadius, mCircleBluePaint)    //外圈带边框
    }

    private fun drawBackProgress(canvas: Canvas) {
        mBackGroundRect.set(mInnerPadding, startTop, width.toFloat() - mInnerPadding, startBottom)
        canvas.drawRoundRect(mBackGroundRect, 50f, 50f, mBackGroundPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                mLeftCirlcleX = event.x
                mLeftCirlcleX = when {
                    mLeftCirlcleX < mInnerPadding + mCircleRadius / 2 -> {
                        mInnerPadding
                    }
                    mLeftCirlcleX > mBackGroundRect.right -> {
                        mBackGroundRect.right
                    }
                    else -> {
                        event.x
                    }
                }
            }

        }
        invalidate()
        return true
    }
}