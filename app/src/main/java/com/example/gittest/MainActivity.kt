package com.example.gittest

import android.os.Bundle
import android.view.animation.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var but透明: Button
    lateinit var but平移: Button
    lateinit var but旋转: Button
    lateinit var but缩放: Button
    lateinit var findViewById: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById = findViewById(R.id.view)
        but透明 = findViewById(R.id.touming)
        but平移 = findViewById(R.id.pingyi)
        but旋转 = findViewById(R.id.xuanzhuan)
        but缩放 = findViewById(R.id.suofang)

        but透明.setOnClickListener {

            //代码方式
            val loadAnimation=AlphaAnimation(0.0f,1.0f).apply {
                    duration=2000
            }
            //xml方式
//            val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.hhhh)
            findViewById.startAnimation(loadAnimation)
        }

        but平移.setOnClickListener {
            val loadAnimation=TranslateAnimation(Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_PARENT,0.9f,Animation.ABSOLUTE,0f,Animation.ABSOLUTE,0f).apply {
                duration=2000
            }
//            val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.tran)
            findViewById.startAnimation(loadAnimation)
        }

        but旋转.setOnClickListener {
            val loadAnimation =RotateAnimation(0f,90f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f).apply {
                duration=2000
            }
//            val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.xuanzhuan)
            findViewById.startAnimation(loadAnimation)
        }

        but缩放.setOnClickListener {
            val loadAnimation=ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f).apply {
                duration=2000
            }
//            val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale)
            findViewById.startAnimation(loadAnimation)
        }
    }

}