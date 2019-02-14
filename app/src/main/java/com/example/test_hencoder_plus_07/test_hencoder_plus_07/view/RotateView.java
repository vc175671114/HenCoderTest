package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

import java.text.DecimalFormat;

public class RotateView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String angleStr = "0.00";
    private Bitmap bitmap;

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) Utils.dp2px(150));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                calculateAngle(event);
                break;
            case MotionEvent.ACTION_MOVE:
                calculateAngle(event);
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void calculateAngle(MotionEvent event) {
        double angle = 0;
        if (event.getPointerCount() == 2) {
            if (event.getX(1) > event.getX(0)) {
                angle = Math.atan((event.getY(0) - event.getY(1))
                        / (event.getX(1) - event.getX(0))) * 180 / Math.PI;
            } else {
                angle = Math.atan((event.getY(0) - event.getY(1))
                        / (event.getX(1) - event.getX(0))) * 180 / Math.PI;
                if (angle < 0) {
                    angle = angle + 180;
                } else {
                    angle = angle - 180;
                }
            }
            DecimalFormat df = new DecimalFormat("#.00");
            angleStr = df.format(angle);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(Utils.dp2px(20));
        paint.setStrokeWidth(Utils.dp2px(5));

        canvas.save();
        canvas.rotate(-Float.parseFloat(angleStr), getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2,
                (getHeight() - bitmap.getHeight()) / 2, paint);
        canvas.restore();

        Rect bounds = new Rect();
        paint.getTextBounds(angleStr, 0, angleStr.length(), bounds);
        canvas.drawText("当前与X轴角度为：" + angleStr, (getWidth() - bounds.width()) / 2,
                getHeight() - bounds.height(), paint);

    }
}
