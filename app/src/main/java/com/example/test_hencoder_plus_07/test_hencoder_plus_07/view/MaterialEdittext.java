package com.example.test_hencoder_plus_07.test_hencoder_plus_07.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.R;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.Utils;

public class MaterialEdittext extends android.support.v7.widget.AppCompatEditText {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float TEXT_SIZE = Utils.dp2px(12);
    private static final float TEXT_MARGIN = Utils.dp2px(8);

    private boolean isFloatLabelIsShown = false;
    private ObjectAnimator animator;

    private float floating_table_fraction = 0;//透明度和偏移度系数
    private static final float TEXT_TRANSLATE_Y = Utils.dp2px(20);//Y轴偏移距离

    private boolean useFloatingLAbel;
    private int floatingLabelTextColor;

    public float getFloating_table_fraction() {
        return floating_table_fraction;
    }

    public void setFloating_table_fraction(float floating_table_fraction) {
        this.floating_table_fraction = floating_table_fraction;
        invalidate();
    }

    public MaterialEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEdittext);
        useFloatingLAbel = typedArray.getBoolean(R.styleable.MaterialEdittext_useFloatingLAbel, true);
        floatingLabelTextColor = typedArray.getColor(R.styleable.MaterialEdittext_floatingLabelTextColor,
                getResources().getColor(R.color.material_orange_500));
System.out.println("!!!!!" + floatingLabelTextColor);
        if (useFloatingLAbel) {
            initView();
        }

        typedArray.recycle();
    }

    private void initView() {
        setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN),
                getPaddingRight(), getPaddingBottom());
        paint.setTextSize(TEXT_SIZE);
        paint.setStrokeWidth(Utils.dp2px(5));
        paint.setColor(floatingLabelTextColor);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s) && !isFloatLabelIsShown) {
//                    isFloatLabelIsShown = true;
//                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(MaterialEdittext.this
//                            , "floating_table_fraction", 1);
//                    objectAnimator1.start();
//                } else if (TextUtils.isEmpty(s) && isFloatLabelIsShown) {
//                    isFloatLabelIsShown = false;
//                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(MaterialEdittext.this
//                            , "floating_table_fraction", 0);
//                    objectAnimator2.start();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !isFloatLabelIsShown) {
                    isFloatLabelIsShown = true;
                    getAnimator().start();
                } else if (s.length() == 0 && isFloatLabelIsShown) {
                    isFloatLabelIsShown = false;
                    getAnimator().reverse();
                }
            }
        });
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "floating_table_fraction", 0, 1);
        }
        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (useFloatingLAbel) {
            paint.setAlpha((int) (0xff * floating_table_fraction));
            float extraOffset = TEXT_TRANSLATE_Y * (1 - floating_table_fraction);
            canvas.drawText(getHint().toString(), getPaddingLeft(),
                    (getPaddingTop() + TEXT_SIZE) / 2 + extraOffset, paint);
        }
    }
}
