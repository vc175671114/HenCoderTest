package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class IconView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIS = Utils.dp2px(150);

    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;

        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(width, height, RADIS+Utils.dp2px(10), paint);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(width, height, RADIS, paint);
        paint.setXfermode(xfermode);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_01);
        canvas.drawBitmap(bitmap, width - bitmap.getWidth() / 2, height - bitmap.getHeight() / 2, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }
}
