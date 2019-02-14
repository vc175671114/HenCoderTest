package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class CircleView extends View {
    private static final float RADIUS = Utils.dp2px(80);
    private static final float PADDING = Utils.dp2px(20);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) ((RADIUS + PADDING) * 2);
        int height = (int) ((RADIUS + PADDING) * 2);

//        width = resolveSizeAndState(width, widthMeasureSpec, 0);
//        height = resolveSizeAndState(height, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getResources().getColor(R.color.material_blueGrey_200));
        paint.setColor(getResources().getColor(R.color.material_lightBlue_500));
        canvas.drawCircle(RADIUS + PADDING, RADIUS + PADDING, RADIUS, paint);
    }
}
