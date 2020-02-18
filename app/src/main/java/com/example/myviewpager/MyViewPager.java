package com.example.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyViewPager extends ViewGroup {


    private static final String TAG = "MyViewPager";
    private OnPagerChangeListener onPagerChangeListener;

    private GestureDetector gestureDetector;
    private float startX;
    private int tempIndex;
    private int currentIndex;
    private Scroller myScroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        myScroller=new Scroller(context);
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                scrollBy((int)distanceX,getScrollY());
                Log.e(TAG, "onScrollX: "+getScrollX()+"onScrollY: "+getScrollY());
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    private float downX;
    private float downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        gestureDetector.onTouchEvent(ev);
        boolean result=false;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "onInterceptTouchEvent: ACTION_DOWN" );
                downX=ev.getX();
                downY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "onInterceptTouchEvent: ACTION_MOVE" );
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX=Math.abs(endX-downX);
                float distanceY=Math.abs(endY-downY);
                if(distanceX>distanceY&&distanceX>5){
                    result=true;
                }
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onInterceptTouchEvent: ACTION_UP" );
                break;
        }

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.e(TAG, "onTouchEvent: "+event.getAction());
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "onTouchEvent: ACTION_DOWN");
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onTouchEvent: ACTION_UP");
                float endX = event.getX();
                int tempIndex=currentIndex;
                if(startX-endX>getWidth()/2){
                    tempIndex++;
                }else if(endX-startX>getWidth()/2){
                    tempIndex--;
                }
                scroll2Index(tempIndex);
                break;
        }
        return true;
    }

    public void scroll2Index(int tempIndex) {
        if(tempIndex<0){
            tempIndex=0;
        }else if(tempIndex>getChildCount()-1){
            tempIndex=getChildCount()-1;
        }
        currentIndex=tempIndex;
        onPagerChangeListener.onChange(currentIndex);
        //瞬间回弹应改为有回弹效果的
//        scrollTo(currentIndex*getWidth(),getScrollY());
        myScroller.startScroll(getScrollX(),getScrollY(),currentIndex*getWidth()-getScrollX(),0,Math.abs(currentIndex*getWidth()-getScrollX()));
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(myScroller.computeScrollOffset()){
            scrollTo((int)myScroller.getCurrX(),0);
            invalidate();
        }
    }

    public interface  OnPagerChangeListener{
        void onChange(int index);
    }

    public void setOnPagerChangeListener(OnPagerChangeListener onPagerChangeListener) {
        this.onPagerChangeListener = onPagerChangeListener;
    }
}
