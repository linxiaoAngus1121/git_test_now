package com.example.gittest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 请描述类的作用
 *
 * @author: linxiao date: 2021年12月24日, 0024 15:49
 */
public class MCos extends ConstraintLayout {
    public MCos(@NonNull Context context) {
        super(context);
    }

    public MCos(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MCos(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MCos(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("000","MCosonTouchEvent");
        return super.onTouchEvent(event);
    }
}
