package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 互不干扰型
 */
public class MultiView_04 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //轻量级的map，要注意她的用法，添加用add，遍历获取用valueAt，根据key获取用get
    SparseArray<Path> pathMap = new SparseArray<>();

    private int[] colors = {R.color.material_amber_500, R.color.material_yellow_500,
            R.color.material_green_500, R.color.material_blue_500,
            R.color.material_red_500, R.color.material_pink_500,
            R.color.material_cyan_500, R.color.material_deepPurple_500,
            R.color.material_amber_500, R.color.material_yellow_500,
            R.color.material_green_500, R.color.material_blue_500,
            R.color.material_red_500, R.color.material_pink_500,
            R.color.material_cyan_500, R.color.material_deepPurple_500,
            R.color.material_amber_500, R.color.material_yellow_500,
            R.color.material_green_500, R.color.material_blue_500,
            R.color.material_red_500, R.color.material_pink_500,
            R.color.material_cyan_500, R.color.material_deepPurple_500};

    public MultiView_04(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStrokeWidth(Utils.dp2px(5));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int ponitIndex = event.getActionIndex();
                int currentID = event.getPointerId(ponitIndex);
                Path path = new Path();
                path.moveTo(event.getX(ponitIndex), event.getY(ponitIndex));
                pathMap.append(currentID, path);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    currentID = event.getPointerId(i);
                    path = pathMap.get(currentID);
                    path.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                pathMap.remove(event.getPointerId(event.getActionIndex()));
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < pathMap.size(); i++) {
            paint.setColor(getResources().getColor(colors[i]));
            canvas.drawPath(pathMap.valueAt(i), paint);
        }
    }
}
