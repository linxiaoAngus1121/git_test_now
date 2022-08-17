package com.example.gittest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.lazyiones.weather.App
import com.lazyiones.weather.Weather
import com.lazyiones.weather.dp

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年8月16日, 0016 16:03
 */
class TwentyFourHourView : View {

    companion object {
        private val TEMP = intArrayOf(21, 21, 23, 23, 23, 22, 23, 23, 23, 22, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 25, 26, 25, 24)
        private val WEATHER = arrayOf("晴", "晴", "多云", "多云", "多云", "雷阵雨", "小雨", "中雨", "中雨", "中雨", "中雨", "大雨", "大雪", "大雪", "大雪", "大雪", "大雪", "大雪", "大雪", "大雪", "雨夹雪", "雨夹雪", "雨夹雪", "雨夹雪")
        private val ICON = intArrayOf(R.drawable.w0, -1, R.drawable.w1, -1, -1, R.drawable.w5, R.drawable.w7, R.drawable.w9, -1, -1, -1, R.drawable.w10, R.drawable.w15, -1, -1, -1, -1, -1, -1, -1, R.drawable.w19, -1, -1, -1)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val itemSize = 24 //24小时
    private val data = ArrayList<Weather>() //数据集合
    private var maxTemp: Int = 0 //最大温度
    private var minTemp: Int = 0 //最小温度
    private val marginLeftItem by lazy { 2.dp() } //左边预留宽度
    private val marginRightItem by lazy { 20.dp() } //右边预留宽度
    private val itemWidth by lazy { 30.dp() } //每个Item的宽度
    private val bottomTextHeight by lazy { 16.dp() } //底部文字高度
    private val mWidth by lazy { marginLeftItem + marginRightItem + itemSize * itemWidth } //View的宽度
    private val mHeight by lazy { 140.dp() } //View的高度
    private val tempBaseTop by lazy { (mHeight - bottomTextHeight) / 3 } //温度折线的上边Y坐标
    private val tempBaseBottom by lazy { (mHeight - bottomTextHeight) * 3 / 4 } //温度折线的下边Y坐标
    private var maxScrollOffset: Int = 0 //滚动条最长滚动距离
    private var scrollOffset: Int = 0 //滚动条偏移量
    private var scrollWidth: Int = 0 //IndexHorizontalScrollView的宽度

    private val linePaint: Paint by lazy { Paint() }
    private val rectPaint: Paint by lazy { Paint() }


    init {
        linePaint.apply {
            pathEffect = CornerPathEffect(180f)
            style = Paint.Style.FILL
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        rectPaint.apply {
            color = Color.parseColor("#1AFFFFFF")
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            strokeWidth = 1f

        }
        initData()
    }

    private fun initData() {
        data.let {
            maxTemp = TEMP.maxOrNull()!!
            minTemp = TEMP.minOrNull()!!
            for (i in 0 until itemSize) {
                val time = if (i < 10) {
                    "0$i:00"
                } else {
                    "$i:00"
                }
                val left = marginLeftItem + i * itemWidth
                val right = left + itemWidth - 1
                val point = calculateTempPoint(left, right, TEMP[i])
                data.add(Weather(ICON[i], TEMP[i], WEATHER[i], time, point))
            }
        }
    }

    private fun calculateTempPoint(left: Int, right: Int, temp: Int): Point {
        val minHeight = tempBaseTop.toDouble()
        val maxHeight = tempBaseBottom.toDouble()
        val tempY = maxHeight - (temp - minTemp) * 1.0 / (maxTemp - minTemp) * (maxHeight - minHeight)
        return Point((left + right) / 2, tempY.toInt())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    fun setScrollOffset(offset: Int, maxScrollOffset: Int, scrollWidth: Int) {
        this.scrollWidth = scrollWidth
        this.maxScrollOffset = maxScrollOffset
        scrollOffset = offset //                currentItemIndex = calculateItemIndex()
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        onDrawLine(canvas)
        onDrawRectangle(canvas)
        onDrawIcon(canvas)
    }

    private fun onDrawLine(canvas: Canvas) {
        val path = Path()
        val firstPoint = data[0].tempPoint
        path.moveTo((firstPoint.x).toFloat(), (firstPoint.y).toFloat())
        data.forEachIndexed { index, weather ->
            val tempPoint = weather.tempPoint
            if (index != 0) {
                val pointPre: Point = data[index - 1].tempPoint
                if (index == 1) {
                    path.lineTo((tempPoint.x).toFloat(), (tempPoint.y).toFloat())
                } else {
                    path.rLineTo((tempPoint.x - pointPre.x).toFloat(), tempPoint.y - pointPre.y.toFloat())
                }
            }
        }
        canvas.drawPath(path, linePaint)
    }

    private fun onDrawRectangle(canvas: Canvas) {
        data.let { data ->
            val pathBG = Path()
            val point0 = data[0].tempPoint
            pathBG.moveTo(point0.x.toFloat(), point0.y.toFloat())
            data.forEach { weather ->
                val point: Point = weather.tempPoint
                val i = data.indexOf(weather)
                if (i != 0) {
                    val pointPre: Point = data[i - 1].tempPoint
                    when {
                        i == 1 -> pathBG.lineTo(point.x.toFloat(), point.y.toFloat())
                        weather.icon != -1 -> pathBG.rLineTo(point.x - pointPre.x - 1f.dp(), (point.y - pointPre.y).toFloat())
                        else -> pathBG.rLineTo((point.x - pointPre.x).toFloat(), (point.y - pointPre.y).toFloat())
                    }
                    var pointBackup: Point = data[0].tempPoint //一样的话就把icon替换成-1
                    if (data[i].icon != -1 || (getGoneBehind(i) && i == data.size - 1)) {
                        for (j in 0 until i) {
                            if (data[j].icon != -1) { //找到第一个不一样的，因为一样的会被合在一个矩形里面
                                pointBackup = data[j].tempPoint
                            }
                        }

                        rectPaint.color = if (pointBackup.x < getScrollBarX() + 46f.dp() && getScrollBarX() + 46f.dp() < point.x) Color.RED
                        else Color.parseColor("#1AFFFFFF")
                        val detalheight = mHeight - bottomTextHeight - 4f.dp() - point.y
                        pathBG.rLineTo(0f, detalheight)      //画曲线的末端到底部
                        pathBG.rLineTo(pointBackup.x - point.x + 1f.dp(), 0f)  //因为要往左边画，左边小，所以得用原来的-当前的，得到一个负值，才是往左边画
                        canvas.drawPath(pathBG, rectPaint)
                        pathBG.reset() //移到新的点开始画
                        pathBG.moveTo(point.x.toFloat(), point.y.toFloat())
                    }

                }
            }

        }
    }

    private fun onDrawIcon(canvas: Canvas) {
        /**
         * 从图中可以看到，天气的icon是在每个矩形的正中间，
         * 随着手指的滑动，最左边的icon所在的矩形如果被view的左边界盖住，
         * 矩形中的icon保持在view的左边界和矩形的右边界中间，最右边同理。
         */
        data.forEachIndexed { index, weather ->
            val point: Point = weather.tempPoint
            if (index != 0) { //找到矩形的宽度
                var pointBackup: Point = data[0].tempPoint          //这个值是为了找到上一个不一样icon的item
                var icon = 0
                var indexBackUp = 0 //走了下面这个if,代表跟上一个是不一样的icon了
                if (weather.icon != -1 || (getGoneBehind(index) && index == data.size - 1)) {
                    for (j in 0 until index) {
                        if (data[j].icon != -1) { //找到第一个不一样的，因为一样的会被合在一个矩形里面
                            icon = data[j].icon
                            indexBackUp = j
                            pointBackup = data[j].tempPoint
                        }
                    } //(point.x - pointBackup.x)矩形的宽
                    // (point.x - pointBackup.x) / 2 这里是矩形宽度的一半，这里减完其实就是很小的一个值，所以下面要加上pointBackup.x 才能到正确的位置
                    var left = (point.x - pointBackup.x) / 2 + pointBackup.x - 10.dp()  //矩形的左边
                    var right = (point.x - pointBackup.x) / 2 + pointBackup.x + 10.dp()   //矩形的右边
                    //（右边-左边-移动的距离）/2 这里剪完是很小的一个值，所以下面要加上pointBackup.x 才能到正确的位置
                    val newLeft = (point.x - (pointBackup.x - getItemLeftMargin(indexBackUp))) / 2 + pointBackup.x -getItemLeftMargin(indexBackUp)
                    val newRight = (point.x + getItemRightMargin(index) - pointBackup.x) / 2 + pointBackup.x
                    if (getItemLeftMargin(indexBackUp) < 0 && newLeft + 20.dp() < point.x && index - indexBackUp > 1) {
                        left = newLeft - 10.dp()
                        right = left + 20.dp()
                    } else if (getItemLeftMargin(indexBackUp) < 0 && newLeft + 40.dp() >= point.x && index - indexBackUp > 1) {
                        left = point.x - 30.dp()
                        right = left + 20.dp()
                    }
                    if (getItemRightMargin(index) < 0 && newRight > pointBackup.x + 10.dp() && index - indexBackUp > 1) {
                        right = newRight + 10.dp()
                        left = right - 20.dp()
                    }

                    val drawable = ContextCompat.getDrawable(App.context, icon)
                    drawable?.bounds = Rect(left, tempBaseBottom + 5.dp(), right, tempBaseBottom + 20.dp())
                    drawable!!.draw(canvas)
                }
            }
        }
    }

    private fun getItemLeftMargin(i: Int): Int {
        return (marginLeftItem + i * itemWidth + itemWidth / 2) - scrollOffset
    }

    private fun getItemRightMargin(i: Int): Int {
        val left: Int = marginLeftItem + i * itemWidth + (itemWidth) / 2
        return scrollWidth - (left - scrollOffset)
    }

    private fun getGoneBehind(index: Int): Boolean {
        val list: ArrayList<Boolean> = ArrayList()
        for (i in index until itemSize) { //一样的话就把icon替换成-1
            list.add(data[i].icon == -1)
        }
        return !list.contains(false)
    }

    private fun getScrollBarX(): Int { //19*30 * 4 /1196
        val x = (itemSize - 5) * itemWidth * scrollOffset / maxScrollOffset
        return x + marginLeftItem
    }
}