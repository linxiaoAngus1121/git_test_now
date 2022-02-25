package com.example.gittest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年2月14日, 0014 16:44
 */
class MyProgressBar(context: Context, attr: AttributeSet?) : View(context, attr) {
    constructor(context: Context) : this(context, null)

    private val IMAGEPADDING = 20.px
    private val IMAGEWIDTH = 300.px
    private val IMAGEHEIGHT = 20.px
    private val mWidth = IMAGEPADDING + IMAGEWIDTH
    val bitmap = Bitmap.createBitmap(
        mWidth.toInt(),
        (IMAGEPADDING + IMAGEHEIGHT).toInt(),
        Bitmap.Config.ARGB_8888
    )
    val mTextCanvas = Canvas(bitmap)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
    }

    private val mContentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    private val mTxtPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 20.px
        textAlign = Paint.Align.CENTER
    }

    private var mCurProgress = 0f

    private val bounds = RectF(IMAGEPADDING, IMAGEPADDING, mWidth, IMAGEPADDING + IMAGEHEIGHT)

    private val XFRERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)




    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawContent(canvas)
        drawText(canvas)
    }

    val mTextMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private fun drawText(canvas: Canvas) {
        val saveLayer = canvas.saveLayer(bounds, null)

        mTxtPaint.color = Color.GREEN               //DST
        val textLength = "下载中".length
        val textLeft: Float = bounds.centerX() - textLength / 2
        val fm: Paint.FontMetrics = mTxtPaint.fontMetrics
        val textBottom: Float = bounds.centerY() + (fm.bottom - fm.top) / 2 - fm.descent
        canvas.drawText("下载中", textLeft, textBottom, mTxtPaint)

        mTxtPaint.xfermode=mTextMode
        mTxtPaint.color = Color.WHITE
        canvas.drawRoundRect(   //进度--属于SRC
            IMAGEPADDING,
            IMAGEPADDING,
            IMAGEPADDING + mCurProgress * mWidth,
            IMAGEPADDING + IMAGEHEIGHT,
            20f.px,
            20f.px,
            mTxtPaint
        )
        mTxtPaint.xfermode = null
        canvas.restoreToCount(saveLayer)

    }

    private fun drawContent(canvas: Canvas) {
        val count1 = canvas.saveLayer(bounds, null)
        canvas.drawRoundRect(       //这个属于背景Dst
            IMAGEPADDING,
            IMAGEPADDING,
            mWidth,
            IMAGEPADDING + IMAGEHEIGHT,
            20f.px,
            20f.px,
            paint
        )
        mContentPaint.xfermode = XFRERMODE
        canvas.drawRoundRect(   //进度--属于SRC
            IMAGEPADDING,
            IMAGEPADDING,
            IMAGEPADDING + mCurProgress * mWidth,
            IMAGEPADDING + IMAGEHEIGHT,
            20f.px,
            20f.px,
            mContentPaint
        )
        mContentPaint.xfermode = null
        canvas.restoreToCount(count1)
    }


    fun setProgress(progress: Float) {
        mCurProgress = when {
            progress > 1 -> 1f
            progress < 0 -> 0f
            else -> progress
        }
        invalidate()
    }
}