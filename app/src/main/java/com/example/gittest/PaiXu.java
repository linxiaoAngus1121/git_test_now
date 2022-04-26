package com.example.gittest;

import java.util.Arrays;

/**
 * 请描述类的作用
 *
 * @author linxiao date: 2022年4月26日, 0026 14:43
 */
public class PaiXu {
    public static int[] bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted  = true;//有序标记，每一轮的初始是true
            for (int j = 0; j < arr.length -i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    isSorted  = false;//有元素交换，所以不是有序，标记变为false
                    int t = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
            }
            //一趟下来是否发生位置交换，如果没有交换直接跳出大循环
            if(isSorted )
                break;
        }
        return arr;
    }
}
