package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class MeterView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int ANGLE = 120;
    private static final float RADIS = Utils.dp2px(150);
    private static final float METER_LENGTH = Utils.dp2px(100);
    private int center_x;
    private int center_y;

    private Path dashPath = new Path();
    private PathDashPathEffect pathDashPathEffect;
    //刻度总数
    private static final int METER_NUMBERS = 15;
    //指针对应刻度数
    private static final int METER_MARK = (int) (Math.random() * 14 + 1);

    public MeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        center_x = getWidth() / 2;
        center_y = getHeight() / 2;

        paint.setStrokeWidth(Utils.dp2px(2));
        paint.setStyle(Paint.Style.STROKE);

        Path arc = new Path();
        arc.addArc(center_x - RADIS, center_y - RADIS,
                center_x + RADIS, center_y + RADIS,
                90 + ANGLE / 2, 360 - ANGLE);
        dashPath.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        pathDashPathEffect = new PathDashPathEffect(dashPath,
                (pathMeasure.getLength() - Utils.dp2px(2)) / METER_NUMBERS,//减Utils.dp2px(2)是因为要剪掉一个标码的宽度，否则会超出刻度表的边界
                0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(center_x - RADIS, center_y - RADIS,
                center_x + RADIS, center_y + RADIS,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(center_x - RADIS, center_y - RADIS,
                center_x + RADIS, center_y + RADIS,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);

        canvas.drawLine(center_x, center_y,
                (float) Math.cos(Math.toRadians(getAngleFromMark())) * METER_LENGTH + center_x,
                (float) Math.sin(Math.toRadians(getAngleFromMark())) * METER_LENGTH + center_y,
                paint);
    }

    private int getAngleFromMark() {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / METER_NUMBERS * METER_MARK);
    }
}
