package com.example.gittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val array = intArrayOf(1, 2, 5, 10, 4, 3,50, 100, 8, 12, 9, 7,8, 0, 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        冒泡排序()
//        选择排序()
//        快速排序(array,0,array.size-1)
        插入排序()
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
        Log.d("000", "冒泡排序完的结果${array.joinToString()}")
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
     * 1.从待排序列中任意选取一个记录(通常选取第一个记录)作为基准值，然后从有l,r分别指向数组的最左边跟最右边,
     * 2.最右边的首先向左移动(即r--),找到比基准数小的,停下来
     * 3.然后左边的向右移动(l++),找到比基准数大的,停下来
     * 4.交换r,l的值
     * 5.然后重复2,3步骤
     * 6.当r,l重叠时,当前的值跟基准数交换
     * 7.这时候2边其实是无序的，只需要递归方法本身并传入对应的开始位置跟结束位置即可
     */
    fun 快速排序(array: IntArray, left: Int, right: Int) {
        if(left > right) return
        val jizhun = array[left]
        var i: Int = left
        var j: Int = right
        while (i != j) {
            while (array[j] >= jizhun && j > i) {
                j--
            }
            while (array[i] <= jizhun && j > i) {
                i++
            }
            if (j > i) {
                val t = array[i]
                array[i] = array[j]
                array[j] = t
            }
        }
        array[left] = array[i]
        array[i] = jizhun
        快速排序(array, left, i - 1)
        快速排序(array, i + 1, right)
        Log.d("000", "快速排序完的结果${array.joinToString()}")
    }

    //可以看作是分为2个数组，前面的数组是有序的，后面的是无序的，每次从无序数组中取出一个去插入到有序数组中，
    // 最开始只有取第一个数当作第一个数组，这时候是有序的
    //然后从i后面取一个，跟着前面已经排好序的对比，大的话就交互位置
    fun 插入排序() {
        var i = 1
        var temp: Int
        var j: Int
        while (i < array.size) {
            temp = array[i]  //当前i指向的值
            j = i - 1   //从0到当前位置的前一个，所以是i-1
            for (index in 0..j) {   //当前的temp跟0..到当前位置的前一个的大小
                if (temp < array[index]) {
                    val local = array[index]
                    array[index] = temp
                    temp = local
                }
            }
            array[i] = temp //当前i位置的值，因为上面经历了for循环后，最大的值是给了temp,这里得让i位置的值变成temp,让最大的值处于这次循环中的最后面
            i++
        }
        Log.d("000", "选择排序完的结果${array.joinToString()}")
    }
}