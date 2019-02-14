package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class PieChartView extends View {

    private int[] chars = {80, 60, 90, 20, 110};
    private int[] colors = {Color.MAGENTA, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int startAgile = 0;
    private static final int RADIS = (int) Utils.dp2px(150);
    private static final int SHOW_CHART = (int) (Math.random() * 6 - 1);
    private static final float SHOW_LENGTH = Utils.dp2px(15);

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF(getWidth() / 2 - RADIS, getHeight() / 2 - RADIS,
                getWidth() / 2 + RADIS, getHeight() / 2 + RADIS);

        for (int i = 0; i < chars.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == SHOW_CHART) {
                canvas.translate((float) Math.cos(Math.toRadians(startAgile + chars[i] / 2)) * SHOW_LENGTH,
                        (float) Math.sin(Math.toRadians(startAgile + chars[i] / 2)) * SHOW_LENGTH);
            }
            canvas.drawArc(rect, startAgile, chars[i], true, paint);
            canvas.restore();
            startAgile += chars[i];
        }
    }
}
