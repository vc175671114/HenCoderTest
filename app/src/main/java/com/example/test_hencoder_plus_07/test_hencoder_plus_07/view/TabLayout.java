package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabLayout extends ViewGroup {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    List<Rect> childRectList = new ArrayList<>();

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //必须有这个方法，不然onMeasure()中的MarginLayoutParams()方法会报错
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //初始化工作放在onMeasure中是因为，onMeasure方法会调用两次，所以需要在方法中初始化
        int maxHeight = 0;//当前行最高的高度
        int maxWidth = 0;//最宽行的宽度
        int heightUsed = 0;//父布局已经使用了的高度
        int widthUsed = 0;//当前行已使用了的宽度
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //right设置为0是因为要让view的宽度在无限大的情况下布局，如果宽度太小会导致高度变形
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);//测量child

            //MeasureSpec.getSize(widthMeasureSpec)父view的宽度
            //mode != MeasureSpec.UNSPECIFIED要加上
            if (mode != MeasureSpec.UNSPECIFIED &&
                    widthUsed + child.getMeasuredWidth() > MeasureSpec.getSize(widthMeasureSpec)) {//测量完查看是否宽度已经超过父view的宽度
                heightUsed += maxHeight;
                //新的一行,新一行的最高高度清零
                maxHeight = 0;
                //新的一行,新一行的已使用宽度清零
                widthUsed = 0;
                //重新测量child
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            Rect childBounds;
            if (childRectList.size() <= i) {
                childBounds = new Rect();
                childRectList.add(childBounds);
            } else {
                childBounds = childRectList.get(i);
            }
            childBounds.set(widthUsed, heightUsed,
                    widthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            widthUsed += child.getMeasuredWidth();
            maxWidth = Math.max(maxWidth, widthUsed);
            maxHeight = Math.max(child.getMeasuredHeight(), maxHeight);
        }

        int width = maxWidth;
        int height = maxHeight + heightUsed;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = childRectList.get(i);
            View child = getChildAt(i);
            //测量完成后，用child的layout在父view中布局子view
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
