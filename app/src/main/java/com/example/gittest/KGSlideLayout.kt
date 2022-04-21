package com.example.gittest

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import androidx.core.view.ViewCompat
import androidx.core.view.get
import kotlin.math.roundToInt


/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月20日, 0020 15:45
 */
class KGSlideLayout(context: Context, attributeSet: AttributeSet?) :
    HorizontalScrollView(context, attributeSet) {

    constructor(context: Context) : this(context, null)

    private lateinit var mContainerView: View
    private lateinit var mMenuView: View
    private var mMenuWidth = 0 //需要自定义属性获取= 0
    var isMenuOpen = false

    init {
        attributeSet?.let {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.KGSlideLayout)
            mMenuWidth =
                obtainStyledAttributes.getDimension(R.styleable.KGSlideLayout_menu_width, 325f)
                    .roundToInt()
            obtainStyledAttributes.recycle()
        }
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        //设置宽度为mMenuWidth
        val mRootView = getChildAt(0) as? ViewGroup
            ?: throw  NullPointerException("child can not be null !!!")
        if (mRootView.childCount != 2) {
            throw  RuntimeException("Only two can be placed View !!!");
        }
        mMenuView = mRootView.getChildAt(0)
        mContainerView = mRootView.getChildAt(1)
        mMenuView.layoutParams = mMenuView.layoutParams?.apply {
            width = mMenuWidth
        }
        mContainerView.layoutParams.width = getScreenWidth(context)
    }

    //获取屏幕宽度
    private fun getScreenWidth(mContext: Context): Int {
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics();
        wm.defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        scrollTo(mMenuWidth, 0) //滚到mMenuWidth，这样才能让进来就是主页菜单
    }

//    第二点 在手指抬起的时候，应该根据当前位置去判断菜单时打开，还是关闭。判断菜单打开关闭的条件是我们滑动的距离和菜单的宽做对比
//    1）.在关闭时，向右滑动，如果滑动的距离小于菜单宽度的一半，这是当我们抬起手指时，应当关闭菜单，大于菜单宽度的一半时，应当打开菜单.
//    2）.在打开时，向左滑动，如果滑动的距离小于菜单宽度的一半，这是当我们抬起手指时，应当打开菜单，大于菜单宽度的一半时，应当关闭菜单.

    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        if(onInterceptTouchEvent(ev)) return true
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {
                //你滑动的距离，因为我们默认是关闭菜单的(即在onLayout方法中已经调用了 scrollTo(mMenuWidth, 0))，所以这个距离默认已经是mMenuWidth的取值了
                //所以会导致下面1，3的情况
                //1如果你在侧边打开的情况下，往左滑,因为侧边已经是最左了，curX的距离只有很小，都是10几，20几，所以只会进入if
                //2如果你在侧边打开的情况下，往右滑,那就判断你滑动的距离了，如果>一半，那就进入if
                //3如果你在侧边关闭的情况下，往右滑,因为现在侧边是关闭的，已经最右了，curX距离只会很大，都是1000几，所以只会进入else
                //4如果你在侧边关闭的情况下，往左滑,那就判断你滑动的距离了，如果>一半，那就进入if
                val curX = scrollX
                if (curX < mMenuWidth / 2) { //未滚动到mMenuView宽度的一半
                    open()
                } else {
                    close()
                }
                return false
            }
        }
        return super.onTouchEvent(ev)
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        // l  代表滑动后当前ScrollView可视界面的左上角在整个ScrollView的X轴中的位置
        //l:left 变化mMenuWidth -> 0,越靠近于0
        val scale = 1f * l / mMenuWidth //从1->0

        val rightScaleValue: Float = 0.85f + 0.15f * scale
        mContainerView.run {
            pivotX = 0f
            pivotY = mContainerView.measuredHeight / 2f
            scaleX = rightScaleValue
            scaleY = rightScaleValue
        }
        //scale 大，这个值就会变小
        val leftAlphaValue = (1 - scale) * 0.2f + 0.8f
        val leftScaleValue = (1 - scale) * 0.15f + 0.85f
        mMenuView.run {
            alpha = leftAlphaValue
            scaleX = leftScaleValue
            scaleY = leftScaleValue
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.d("000","is ${ev.action == MotionEvent.ACTION_UP}")
        if (isMenuOpen  && ev.action == MotionEvent.ACTION_UP && ev.x > mMenuView.width) {
            close()
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }


    private fun open() {
        smoothScrollTo(0, 0)
        isMenuOpen = true
    }


    private fun close() {
        smoothScrollTo(mMenuWidth, 0)
        isMenuOpen = false
    }

}