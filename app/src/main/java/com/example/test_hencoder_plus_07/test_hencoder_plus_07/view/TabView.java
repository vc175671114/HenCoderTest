package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class TabView extends View {

    private int[] colors = {R.color.material_amber_500, R.color.material_yellow_500,
            R.color.material_green_500, R.color.material_blue_500,
            R.color.material_red_500, R.color.material_pink_500,
            R.color.material_cyan_500, R.color.material_deepPurple_500};
    private float[] widths = {Utils.dp2px(150), Utils.dp2px(200), Utils.dp2px(250),
            Utils.dp2px(220), Utils.dp2px(80), Utils.dp2px(300),
            Utils.dp2px(180), Utils.dp2px(350)};
    private float[] heights = {Utils.dp2px(150), Utils.dp2px(100), Utils.dp2px(200),
            Utils.dp2px(180), Utils.dp2px(220), Utils.dp2px(300),
            Utils.dp2px(280), Utils.dp2px(350)};

    private float width;
    private float height;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float TEXTSIZE = Utils.dp2px(30);
    String str;

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        str = typedArray.getString(R.styleable.TabView_str);
    }

    private void init() {
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getResources().getColor(colors[(int) Math.floor(Math.random() * 7)]));
        paint.setTextScaleX(TEXTSIZE);
        width = widths[(int) Math.floor(Math.random() * 7)];
        height = heights[(int) Math.floor(Math.random() * 7)];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();

        canvas.drawRect(0, 0, width, height, paint);
        paint.setColor(getResources().getColor(R.color.material_textBlack_black));
        canvas.drawText("12356", width / 2, height / 2, paint);
    }
}
