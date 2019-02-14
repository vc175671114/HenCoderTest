package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class ImageTextView extends View {

    private String textStr = "说一下「高级」这个词。同样的一个词，不同的人有不同的理解，不同的位置和环境有不同的定义。A 公司的「高级」，在 B 公司也许叫「中级」，到了 C 公司可能又成了「资深」。想来我最后一次换工作时，薪资涨了不少，职位却是从 「高级 Android 工程师」「降级」成了 「Android Engineer」。所以为了理解的统一，我先明确一下我所指的「高级 Android 工程师」的具体是谁：我这里说的「高级 Android 工程师」，主要指的就是国内大多数小型和微型公司里的 Android 骨干或 Android Leader。这些人在公司的职位通常叫做「高级 Android 工程师」，技术也很不错，但和一些有技术积淀的大公司中的高级工程师相比，他们中的多数人往往（注意是「多数人」「往往」，不是全部，谢绝学我老婆抬杠）底子不够扎实，基础相对薄弱，所以很容易在到达一个还不算很高的技术水平之后，就感到难以继续提升了。他们并不是不想上进，而是不知道应该怎么上进，很多人都已经尝试过很多学习方法，但都好像没有刚入行时那样进步神速，感觉每天都是一个全新的自己了。据我了解，现在中国的程序员中，这样的人非常多。他们是每个公司的骨干，但技术水平却没有达到自己期望的高度（甚至有不少人，也没有达到公司同事以为的高度）。我在这里所说的「高级」，指的就是这些人。";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;

    private int startIndex = 0;
    private int endIndex = 0;
    private int cutIndex;
    private float textHeight = Utils.dp2px(20);

    private float bitmapTop = Utils.dp2px(50);
    private int bitmapLeft = (int) Utils.dp2px(150);

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(16));
        paint.setTextAlign(Paint.Align.LEFT);

        bitmap = Utils.getAvatar(getResources(), bitmapLeft);
    }

    float[] cutWidth = new float[1];

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, getWidth() - bitmapLeft, bitmapTop, paint);

//        while (startIndex <= endIndex) {
//            cutIndex = paint.breakText(textStr, true, getWidth(), cutWidth);
//            endIndex += cutIndex;
//            if (endIndex >= textStr.length()) {
//                endIndex = textStr.length();
//            }
//            System.out.println("startIndex---" + startIndex + ",endIndex---" + endIndex + ",textHeight---" + textHeight);
//            canvas.drawText(textStr, startIndex, endIndex, 0, textHeight, paint);
//            startIndex += cutIndex;
//            textHeight += paint.getFontSpacing();
//        }


        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        while (startIndex <= endIndex) {
            paint.getFontMetrics(fontMetrics);
            //文字绘制的底部是canvas.drawText方法中的floatY
            if (!(textHeight < bitmapTop ||
                    textHeight + fontMetrics.top > bitmapTop + bitmap.getHeight())) {
                System.out.println("fontMetrics.descent---" + fontMetrics.descent + ",bitmapTop---" + bitmapTop +
                        ",fontMetrics.ascent---" + fontMetrics.ascent + ",bitmapTop + bitmap.getHeight()---" +
                        bitmapTop + bitmap.getHeight() + ",textHeight---" + textHeight);
                cutIndex = paint.breakText(textStr, true, getWidth() - bitmapLeft, cutWidth);
            } else {
                cutIndex = paint.breakText(textStr, true, getWidth(), cutWidth);
            }

            endIndex += cutIndex;
            if (endIndex >= textStr.length()) {
                endIndex = textStr.length();
            }
//            System.out.println("startIndex---" + startIndex + ",endIndex---" + endIndex + ",textHeight---" + textHeight);
            canvas.drawText(textStr, startIndex, endIndex, 0, textHeight, paint);
            startIndex += cutIndex;
            textHeight += paint.getFontSpacing();
        }
    }
}
