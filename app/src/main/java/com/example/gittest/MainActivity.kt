package com.example.gittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazyiones.weather.IndexHorizontalScrollView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val IndexHorizontalScrollView=findViewById<IndexHorizontalScrollView>(R.id.index_horizontal_scroll_view)
        val twentyFourHourView=findViewById<TwentyFourHourView>(R.id.twenty_four_hour_view)
        IndexHorizontalScrollView.setTwentyFourHourView(twentyFourHourView)
    }

}
