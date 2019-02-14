package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class ImageCutAutoView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera camera = new Camera();
    private static final int BITMAP_WIDTH = (int) Utils.dp2px(300);
    private Bitmap bitmap = Utils.getAvatar(getResources(), BITMAP_WIDTH);

    private float rotateDegree;
    private float rotateXDegree;
    private float topRotateXDegree;

    public float getRotateDegree() {
        return rotateDegree;
    }

    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        invalidate();//必须有这一行代码，否则重置绘画不会执行
    }

    public float getRotateXDegree() {
        return rotateXDegree;
    }

    public void setRotateXDegree(float rotateXDegree) {
        this.rotateXDegree = rotateXDegree;
        invalidate();//必须有这一行代码，否则重置绘画不会执行
    }

    public float getTopRotateXDegree() {
        return topRotateXDegree;
    }

    public void setTopRotateXDegree(float topRotateXDegree) {
        this.topRotateXDegree = topRotateXDegree;
        invalidate();//必须有这一行代码，否则重置绘画不会执行
    }

    public ImageCutAutoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int bitmapWidth = bitmap.getWidth() / 2;
        int bitmapHeignt = bitmap.getHeight() / 2;

        //用BITMAP_WIDTH是因为旋转后切的范围要比原来方方正正的要大一些
        RectF topRectF = new RectF(-BITMAP_WIDTH, -BITMAP_WIDTH, BITMAP_WIDTH, 0);
        RectF bottomRectF = new RectF(-BITMAP_WIDTH, 0, BITMAP_WIDTH, BITMAP_WIDTH);

        canvas.save();
        camera.save();
        canvas.translate(width, height);
        canvas.rotate(-rotateDegree);
        camera.rotateX(topRotateXDegree);
        camera.setLocation(0, 0, Utils.getCameraZ());//消除糊脸效果
        camera.applyToCanvas(canvas);//必须有这一句才能将camera投射到canvas上
        canvas.clipRect(topRectF);
        canvas.rotate(rotateDegree);
        canvas.translate(-width, -height);
        canvas.drawBitmap(bitmap, width - bitmapWidth, height - bitmapHeignt, paint);
        camera.restore();
        canvas.restore();

        canvas.save();
        camera.save();
        canvas.translate(width, height);
        canvas.rotate(-rotateDegree);
        camera.rotateX(rotateXDegree);
        camera.setLocation(0, 0, Utils.getCameraZ());//消除糊脸效果
        camera.applyToCanvas(canvas);//必须有这一句才能将camera投射到canvas上
        canvas.clipRect(bottomRectF);
        canvas.rotate(rotateDegree);
        canvas.translate(-width, -height);
        canvas.drawBitmap(bitmap, width - bitmapWidth, height - bitmapHeignt, paint);
        camera.restore();
        canvas.restore();

    }
}
