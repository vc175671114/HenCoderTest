package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

import java.util.ArrayList;
import java.util.List;

public class StringAnimateView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<String> stringList = new ArrayList<>();
    private int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        invalidate();
    }

    public StringAnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        for (int i = 0; i < 90; i++) {
            stringList.add((int) (Math.random() * 9999) + "");
        }
        paint.setTextSize(Utils.dp2px(30));
        paint.setStrokeWidth(Utils.dp2px(8));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(stringList.get(index), getWidth() / 2, getHeight() / 2, paint);
    }
}
