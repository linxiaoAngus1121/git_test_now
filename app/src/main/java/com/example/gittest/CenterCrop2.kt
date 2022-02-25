package com.example.gittest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年2月14日, 0014 14:29
 */
class CenterCrop2(context: Context, attr: AttributeSet?) : View(context, attr) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val IMAGEWIDTH = 200f.px
    private val IMAGEPADDING = 20f.px
    private val BACKGROUNDPADDING = 17f.px
    private val PADDING = 3f.px
    private val RADIUS=20f.px
    private val bounds =
        RectF(IMAGEPADDING, IMAGEPADDING, IMAGEPADDING + IMAGEWIDTH, IMAGEPADDING + IMAGEWIDTH)
    private val XFRERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val XFRERMODE1 = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

    private val bounds1 =
        RectF(BACKGROUNDPADDING, BACKGROUNDPADDING, BACKGROUNDPADDING + IMAGEWIDTH + PADDING * 2,
            BACKGROUNDPADDING + IMAGEWIDTH + PADDING * 2)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val saveLayer = canvas.saveLayer(bounds1, null)
        paint.xfermode = XFRERMODE1
        paint.color = Color.WHITE
        canvas.drawRoundRect(
            BACKGROUNDPADDING,
            BACKGROUNDPADDING,
            BACKGROUNDPADDING + IMAGEWIDTH + PADDING * 2,
            BACKGROUNDPADDING + IMAGEWIDTH + PADDING * 2,
            RADIUS,RADIUS,
            paint)
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)

        val count = canvas.saveLayer(bounds, null)
        //椭圆是底板（红色）
        canvas.drawRoundRect(
            IMAGEPADDING,
            IMAGEPADDING,
            IMAGEPADDING + IMAGEWIDTH,
            IMAGEPADDING + IMAGEWIDTH,
            RADIUS,RADIUS,
            paint)
//        canvas.drawOval(IMAGEPADDING,IMAGEPADDING,IMAGEPADDING+IMAGEWIDTH,IMAGEPADDING+IMAGEWIDTH,paint)  //圆形图
        paint.xfermode = XFRERMODE
        //蓝色
        canvas.drawBitmap(getAvatar(IMAGEWIDTH.toInt()), IMAGEPADDING, IMAGEPADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)

    }

    private fun getAvatar(width: Int): Bitmap {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.card_3, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.card_3, option)
    }
}