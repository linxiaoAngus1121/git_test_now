package com.example.gittest

import android.database.Cursor
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView
import com.example.gittest.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private val views: MutableList<View> = mutableListOf()
    var list: MutableList<String> = ArrayList()
    var upMarqueeView: TextSwitcher?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        inflate.apply {
            //parent1==MyCos
            parent1.setOnClickListener {
                Log.d("000","this is 最顶的COs")
            }
            //first，second是两个按钮
            first.setOnClickListener {
                Log.d("000","this is first")
            }
            second.setOnClickListener {
                Log.d("000","this is second")
            }
            //content==MyFragmeLayout
            //如果下面这里的注释放开，当MyFragmeLayout里面onTouchEvent返回super时，就不会触发parent1的onTouchEvent方法，理解是返回了super,但是因为我们把MyFragmeLayout设置
            //成可以点击的，这个时候相当于消耗了事件，所以是不会触发
            /*  content.setOnClickListener {
                  Log.d("000","this is content")
              }*/
        }
    }


    override fun onResume() {
        super.onResume()
    }


    //这是master分支新增的onRestart方法
    override fun onRestart() {
        super.onRestart()
    }
}