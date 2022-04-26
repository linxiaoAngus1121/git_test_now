package com.example.gittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val array = intArrayOf(1, 2, 5, 10, 4, 3, 100, 8, 12, 9, 7, 0, 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        冒泡排序()
        选择排序()
    }


    //冒泡排序
    fun 冒泡排序() {
        //时间复杂都为o(n的平方)
        for (index in 0 until array.size - 1) {     //最大的一层循环
            var isSort = true
            for (secondIndex in 0 until array.size - 1 - index) {  //里面的小循环,从0循环到(size-1-index),这里-index是因为index后面的都是有序的了,不用参与
                if (array[secondIndex] > array[secondIndex + 1]) {
                    //交换位置
                    val temp: Int = array[secondIndex]
                    array[secondIndex] = array[secondIndex + 1]
                    array[secondIndex + 1] = temp
                    isSort = false    //有元素交换，所以不是有序，标记变为false
                }
            }
            if (isSort) break
        }
        Log.d("000", "排序完的结果${array.joinToString()}")
    }

    //选择排序,(最小的在最前面,后面是第二小，第三小....)默认第一个是最小的,然后第一个跟后面的逐个比对,如果后面的某个发现比第一个还小,那就交换2个数的位置,每次循环就是从大层循环开始的
    fun 选择排序() {
        for (index in 0 until array.size - 1) {
            for (secondeIndex in index until array.size - 1) {
                if (array[secondeIndex] < array[index]) {
                    val temp = array[secondeIndex]
                    array[secondeIndex] = array[index]
                    array[index] = temp
                }
            }
        }
        Log.d("000", "选择排序完的结果${array.joinToString()}")
    }

    /**
     * 从待排序列中任意选取一个记录(通常选取第一个记录)作为基准值，然后将记录中关键字比它小的记录都安置在它的位置之前
     * 将记录中关键字比它大的记录都安置在它的位置之后。这样，以该基准值为分界线，将待排序列分成的两个子序列。
     */
    fun 快速排序(array: IntArray) {
      /*  val min = array[0]
        for (index in array.indices) {
            var i = 0
            var j = array.size
            if (array[j] < min) {
                val temp = array[j]
                array[j] = min
                array[0] = temp
            } else {
                j--
            }
        }*/
    }
}