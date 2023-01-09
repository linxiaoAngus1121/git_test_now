# git_test_now
相当于进度条
![image](https://user-images.githubusercontent.com/31264313/211251387-b9ce877a-f6f3-4b73-b5b3-882cd06c9022.png)
知识点： 1.测量模式 (1).EXACTLY 固定尺寸，即我们写的match_parent 或者写的固定100，200dp这种
                   (2).UNSPECIFIED 没有限制，这种情况一般不会出现，未指明的，未指定的。在这个模式下父控件不会干涉 子View 想要多大的尺寸。这其实取决于父容器。拿最常用的       RecyclerView 为例，在 Item 进行 measure 时，如果列表可滚动，并且 Item 的宽或高设置了 WRAP_CONTENT 的话，那么接下来， ItemView 的 onMeasure 方法就会收到 MesureSpec.UNSPECIFIED 它表达的是：在可滚动的 ViewGroup 中，不应该限制 Item 的尺寸(如果是水平滚动，就不限制宽度)。为什么呢？ 因为是可以滚动的，无论 Item 有多宽，有多高，通过滚动也一样能看到滚动前被遮挡的部分。

有同学可能会有疑问： 我设置了 WRAP_CONTENT ，在 onMeasure 中应该收到的是 AT_MOST 才对啊，为什么会强制变成 UNSPECIFIED ？

这是因为考虑到 Item 的尺寸有可能超出这个可滚动的 ViewGroup 的尺寸，而在 AT_MOST 模式下， 子View 的尺寸不能超出所在的 ViewGroup 的尺寸，最多只能等于。所以在这个场景下，用 UNSPECIFIED 会更合适，这个模式下你想要多大就多大。
                    (3)AT_MOST 即最大是怎们样的，这个时候我们取小的那个
         2.等边三角形算高度，利用勾股定理，本来是 a*a + b*b =c*c， 现在a*a=c*c-b*b
         val traingleHeight = sqrt((triangleWidth * triangleWidth - mCircleRadius / 2 * mCircleRadius / 2).toDouble())
         3.画三角形，最好就是用path就行了
         4.画字居中
          val fontMetrics = mTextPaint.fontMetricsInt
        val baseline = (mTextBackRect.bottom + mTextBackRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(fl, mTextBackRect.centerX(), baseline, mTextPaint)
        5.滑动的时候，如果不响应down事件（即onTouchEvent返回了false,那其实move也就不会触发了）
