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
 * 接管型
 */
public class MultiView_01 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    public MultiView_01(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) Utils.dp2px(220));
    }

    float moveX;
    float moveY;
    float downX;
    float downY;
    float originalOffsetX;
    float originalOffsetY;

    int onUseId;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onUseId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = moveX;
                originalOffsetY = moveY;
                break;
            case MotionEvent.ACTION_MOVE:
                int currentIndex = event.findPointerIndex(onUseId);
                moveX = originalOffsetX + event.getX(currentIndex) - downX;
                moveY = originalOffsetY + event.getY(currentIndex) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointer_down_index = event.getActionIndex();
                onUseId = event.getPointerId(pointer_down_index);
                downX = event.getX(pointer_down_index);
                downY = event.getY(pointer_down_index);
                originalOffsetX = moveX;
                originalOffsetY = moveY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointer_up_index = event.getActionIndex();
                if (event.getPointerId(pointer_up_index) == onUseId) {
                    int newIndex;
                    if (pointer_up_index == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    onUseId = event.getPointerId(newIndex);
                    downX = event.getX(pointer_up_index);
                    downY = event.getY(pointer_up_index);
                    originalOffsetX = moveX;
                    originalOffsetY = moveY;
                }
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
