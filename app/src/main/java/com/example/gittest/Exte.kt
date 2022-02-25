package com.example.gittest

import android.content.res.Resources
import android.util.TypedValue

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年2月14日, 0014 14:47
 */

val Float.px: Float
    get() {
    return    TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this,
            Resources.getSystem().displayMetrics
        )
    }


val Int.px:Float
get() {
    return this.toFloat().px
}