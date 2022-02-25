package com.example.gittest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

/**
 * 请描述类的作用
 *
 * @author: linxiao date: 2021年12月24日, 0024 15:49
 */
public class MText extends LinearLayoutCompat {
    public MText(@NonNull Context context) {
        super(context);
    }

    public MText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("000","MTextdispatchTouchEvent");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("000","MTextTouchEvent");
        return true;
    }
}
