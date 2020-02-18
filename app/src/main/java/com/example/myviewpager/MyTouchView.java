package com.example.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyTouchView extends View {

    private static final String TAG = "MyTouchView";
    public MyTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent: "+ev.getAction());
        return super.onTouchEvent(ev);
    }
}
