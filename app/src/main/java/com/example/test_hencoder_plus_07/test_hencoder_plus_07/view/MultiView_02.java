package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

/**
 * 协作型
 */
public class MultiView_02 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    public MultiView_02(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) Utils.dp2px(220));
    }

    float moveX;
    float moveY;
    float downX;
    float downY;
    float originalOffsetX;
    float originalOffsetY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX = 0;
        float sumY = 0;
        int fingerPointNum = event.getPointerCount();
        for (int i = 0; i < fingerPointNum; i++) {
            //因为抬起事件后count没有减，而滑动事件时，count减了，所以要在抬起事件时做处理
            if (!(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP
                    && i == event.getActionIndex())) {
                sumX += event.getX(i);//这里是根据index来获取的，不能写默认值event.getX()
                sumY += event.getY(i);
            }
        }
        //因为抬起事件后count没有减，而滑动事件时，count减了，所以要在抬起事件时做处理
        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            fingerPointNum -= 1;
        }

        float aveX = sumX / fingerPointNum;
        float aveY = sumY / fingerPointNum;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = aveX;
                downY = aveY;
                originalOffsetX = moveX;
                originalOffsetY = moveY;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = originalOffsetX + aveX - downX;
                moveY = originalOffsetY + aveY - downY;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(moveX, moveY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
