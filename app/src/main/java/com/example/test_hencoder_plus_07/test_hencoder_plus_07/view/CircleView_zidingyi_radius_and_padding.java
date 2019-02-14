package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

@SuppressWarnings("ALL")
public class CircleView_zidingyi_radius_and_padding extends View {
    private float radius;
    private float padding;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView_zidingyi_radius_and_padding(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView_zidingyi_radius_and_padding);
        radius = Utils.dp2px(typedArray.getFloat(R.styleable.CircleView_zidingyi_radius_and_padding_radius, 180));
        padding = Utils.dp2px(typedArray.getFloat(R.styleable.CircleView_zidingyi_radius_and_padding_padding, 20));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float size = (radius + padding) * 2;
        setMeasuredDimension((int) size, (int) size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getResources().getColor(R.color.material_blueGrey_200));
        paint.setColor(getResources().getColor(R.color.material_lightBlue_500));
        canvas.drawCircle(radius + padding, radius + padding, radius, paint);
    }
}
