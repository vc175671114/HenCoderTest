package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;


public class SportsView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float PAINT_WIDTH = Utils.dp2px(20);
    private static final float RADIS = Utils.dp2px(150);
    private static final int START_AGILE = (int) (Math.random() * 360);
    private static final int SWEEP_AGILE = (int) (Math.random() * 230);

    private String text = "Hencoder Plus";

    public SportsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() / 2;
        int height = getHeight() / 2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(PAINT_WIDTH);
        paint.setColor(getResources().getColor(com.zzhoujay.materialcolors.R.color.material_grey_300));
        canvas.drawCircle(width, height, RADIS, paint);
        paint.setColor(getResources().getColor(com.zzhoujay.materialcolors.R.color.material_pink_400));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(width - RADIS, height - RADIS, width + RADIS, height + RADIS,
                START_AGILE, SWEEP_AGILE, false, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        //绘制文字居中，方法一
//        paint.setTextSize(Utils.dp2px(35));
//        paint.setColor(getResources().getColor(com.zzhoujay.materialcolors.R.color.material_yellow_600));
//        Rect rect = new Rect();
//        paint.getTextBounds(text, 0, text.length(), rect);
//        float offset = (rect.top + rect.bottom) / 2;
//        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - offset, paint);
        //绘制文字居中，方法二
        paint.setTextSize(Utils.dp2px(39));
        paint.setColor(getResources().getColor(com.zzhoujay.materialcolors.R.color.material_red_600));
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        paint.getFontMetrics(fontMetrics);
        float offset1 = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - offset1, paint);

        //关于居左不靠边的问题
//        paint.setTextSize(Utils.dp2px(80));
//        paint.setTextAlign(Paint.Align.LEFT);
//        canvas.drawText(text, 0, 200, paint);
//
//        paint.setTextSize(Utils.dp2px(20));
//        canvas.drawText(text, 0, 250, paint);
//
//        paint.setTextSize(Utils.dp2px(20));
//        paint.getTextBounds(text, 0, text.length(), rect);
//        canvas.drawText(text, -rect.left, 250 + paint.getFontSpacing(), paint);//-rect.left表示，text从text负的左边空档处开始绘制
    }
}
