package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;

public class SquareView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int horizonalNum;
    private int verticalNum;

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareView);

        horizonalNum = typedArray.getInt(R.styleable.SquareView_horizonalNum, 3);
        if (horizonalNum <= 3) {
            horizonalNum = 3;
        }
        verticalNum = typedArray.getInt(R.styleable.SquareView_verticalNum, 5);
        if (verticalNum <= 5) {
            verticalNum = 5;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getResources().getColor(R.color.material_pink_500));
        paint.setStyle(Paint.Style.FILL);

        float width = getWidth() / horizonalNum;
        float height = getHeight() / verticalNum;

        for (int i = 0; i < verticalNum; i++) {
            for (int j = 0; j < horizonalNum; j++) {
                int colorType = (int) (Math.random() * 10);
                if (colorType <= 5) {
                    paint.setColor(Color.BLACK);
                } else {
                    paint.setColor(Color.WHITE);
                }
                canvas.drawRect(width * j, height * i,
                        width * (j + 1), height * (i + 1), paint);
            }
        }
    }
}
