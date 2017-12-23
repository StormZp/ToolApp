package com.storm.tool.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.storm.tool.utils.LogUtil;


public class DragViewGroup extends LinearLayout {
    private final String TAG = DragViewGroup.class.getCanonicalName();
    private int lastX, lastY, screenWidth, screenHeight;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;//减去下边的高度
    }

    //定位
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //可以在这里确定这个viewGroup的：宽 = r-l.高 = b - t
//        int w = (l - r) / 2;
//        int h = (t-b) / 2;
//        this.layout(screenWidth - w, screenHeight + h, screenWidth + w, screenHeight - h);
    }


    //拦截touch事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //        LogTool.e("onInterceptTouchEvent");
        int     action      = ev.getAction();
        boolean isMove      = false;
        int     x           = (int) ev.getRawX();
        int     y           = (int) ev.getRawY();
        boolean isIntercept = false;
        /** 按下时的位置控件相对屏幕左上角的位置X */
        int startDownX = 0;
        /** 按下时的位置控件距离屏幕左上角的位置Y */
        int startDownY = 0;
        /** 控件相对屏幕左上角移动的位置X */
        int lastMoveX;
        /** 控件相对屏幕左上角移动的位置Y */
        int lastMoveY;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) ev.getRawX();//设定移动的初始位置相对位置
                lastY = (int) ev.getRawY();
                startDownX = lastMoveX = (int) ev.getRawX();
                startDownY = lastMoveY = (int) ev.getRawY();
//                LogUtil.error("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE://移动
                //event.getRawX()事件点距离屏幕左上角的距离
                int dx = (int) ev.getRawX() - lastX;
                int dy = (int) ev.getRawY() - lastY;

                int left = this.getLeft() + dx;
                int top = this.getTop() + dy;
                int right = this.getRight() + dx;
                int bottom = this.getBottom() + dy;
                if (left < 0) { //最左边
                    left = 0;
                    right = left + this.getWidth();
                }
                if (right > screenWidth) { //最右边
                    right = screenWidth;
                    left = right - this.getWidth();
                }
                if (top < 0) {  //最上边
                    top = 0;
                    bottom = top + this.getHeight();
                }
                if (bottom > screenHeight) {//最下边
                    bottom = screenHeight;
                    top = bottom - this.getHeight();
                }
                this.layout(left, top, right, bottom);//设置控件的新位置
                invalidate();
//                LogUtil.error("position:" + left + ", " + top + ", " + right + ", " + bottom);
                lastX = (int) ev.getRawX();//再次将滑动其实位置定位
                lastY = (int) ev.getRawY();
//                LogUtil.error("lastX= " + lastX + " lastY= " + lastY);
                break;
            case MotionEvent.ACTION_UP:


                int lastMoveDx = Math.abs((int) ev.getRawX() - startDownX);
                int lastMoveDy = Math.abs((int) ev.getRawY() - startDownY);
                if (0 != lastMoveDx || 0 != lastMoveDy) {
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                // 每次移动都要设置其layout，不然由于父布局可能嵌套listview，当父布局发生改变冲毁（如下拉刷新时）则移动的view会回到原来的位置
//                LayoutParams lpFeedback = new LayoutParams(
//                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                lpFeedback.leftMargin = DragViewGroup.this.getLeft();
//                lpFeedback.topMargin = DragViewGroup.this.getTop();
//                lpFeedback.setMargins(DragViewGroup.this.getLeft(), DragViewGroup.this.getTop(), 0, 0);
//                DragViewGroup.this.setLayoutParams(lpFeedback);
//                LogUtil.error("ACTION_DOWN");
                break;
            default:
                LogUtil.error("aaa");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


}