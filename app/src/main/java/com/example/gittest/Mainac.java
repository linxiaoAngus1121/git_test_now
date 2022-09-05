package com.example.gittest;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 请描述类的作用
 *
 * @author linxiao date: 2022年9月5日, 0005 15:55
 */
public class Mainac extends AppCompatActivity {

    private List<View> views = new ArrayList<>();
    public List<String> list = new ArrayList<>();
    private TextSwitcher upMarqueeView;

    private Handler handler = new Handler();
    private int marker;
    List<String> texts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         upMarqueeView = (TextSwitcher) findViewById(R.id.up);
        upMarqueeView.setFactory(() -> new TextView(getBaseContext()));

    }


    @Override
    protected void onResume() {
        super.onResume();
        texts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            texts.add("循环....."+i);
        }
        new TextSwitcherAnimation(upMarqueeView,texts).create();
    }

}
