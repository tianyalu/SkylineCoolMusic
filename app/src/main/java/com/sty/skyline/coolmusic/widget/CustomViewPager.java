package com.sty.skyline.coolmusic.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Shi Tianyi on 2018/5/16/0016.
 */

public class CustomViewPager extends ViewPager {
    private PointF downPoint = new PointF();
    private OnSingleTouchListener onSingleTouchListener;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnSingleTouchListener{
        void onSingleTouch(View v);
    }

    public void setOnSingleTouchListener(OnSingleTouchListener listener){
        this.onSingleTouchListener = listener;
    }

    public void onSingleTouch(View v){
        if(onSingleTouchListener != null){
            onSingleTouchListener.onSingleTouch(v);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录按下时候的坐标
                downPoint.x = ev.getX();
                downPoint.y = ev.getY();
                if(this.getChildCount() > 1){ //有内容，多余一个时通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(this.getChildCount() > 1){ //有内容，多余一个时通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                //在up时判断是否按下和松手的坐标为同一个点
                if(PointF.length(ev.getX() - downPoint.x, ev.getY() - downPoint.y) < (float) 5.0){
                    onSingleTouch(this);
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
