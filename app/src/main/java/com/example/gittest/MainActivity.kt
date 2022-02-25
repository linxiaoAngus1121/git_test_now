package com.example.gittest

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bar= findViewById<MyProgressBar>(R.id.aaaa)
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 5000
        animator.addUpdateListener { animation: ValueAnimator ->
            val percentage = animation.animatedValue as Float
            bar.setProgress(percentage)
        }
        animator.start()
    }

    //这是master分支新增的onRestart方法
    override fun onRestart() {
        super.onRestart()
    }
}