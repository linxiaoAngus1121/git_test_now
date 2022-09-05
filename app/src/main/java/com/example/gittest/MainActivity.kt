package com.example.gittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val views: MutableList<View> = mutableListOf()
    var list: MutableList<String> = ArrayList()
    var upMarqueeView: TextSwitcher?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        upMarqueeView = findViewById(R.id.up)
        upMarqueeView?.setFactory {
            TextView(this)
        }
        val texts: MutableList<String> = ArrayList()
        for (i in 0..9) {
            texts.add("循环.....$i")
        }
        upMarqueeView?.setText(texts.get(0));
    }


    override fun onResume() {
        super.onResume()
    }


    //这是master分支新增的onRestart方法
    override fun onRestart() {
        super.onRestart()
    }
}