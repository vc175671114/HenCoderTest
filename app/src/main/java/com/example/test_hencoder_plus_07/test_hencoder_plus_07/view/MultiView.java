package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class MultiView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    public MultiView(Context context, AttributeSet attrs) {
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
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = moveX;
                originalOffsetY = moveY;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = originalOffsetX + event.getX() - downX;
                moveY = originalOffsetY + event.getY() - downY;
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
