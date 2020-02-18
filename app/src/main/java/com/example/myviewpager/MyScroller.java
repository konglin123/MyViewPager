package com.example.myviewpager;

import android.os.SystemClock;

public class MyScroller {

    private int startX;
    private int startY;
    private int distanceX;
    private int distanceY;
    private long startTime;
    //true表示移动结束，false表示未结束
    private boolean isFinish;
    private long totalTime=500;
    private float currX;

    public float getCurrX() {
        return currX;
    }

    public void startScroll(int startX, int startY, int distanceX, int distanceY) {
        this.startX=startX;
        this.startY=startY;
        this.distanceX=distanceX;
        this.distanceY=distanceY;
        this.startTime= SystemClock.uptimeMillis();
        this.isFinish=false;
    }

    //true代表正在移动，false表示移动结束
    public boolean computeScrollOffset(){

        if(isFinish){
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        long passTime=endTime-startTime;
        if(passTime<totalTime){
           //passTime移动的距离
            float distancSamllx=passTime*distanceX/totalTime;
            currX = startX+distancSamllx;
        }else{
            isFinish=true;
            currX=startX+distanceX;
        }
        return true;
    }
}
