package com.example.test_hencoder_plus_07.test_hencoder_plus_07;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test_hencoder_plus_07.test_hencoder_plus_07.view.ImageCutAutoView;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.view.PointAnimateView;
import com.example.test_hencoder_plus_07.test_hencoder_plus_07.view.StringAnimateView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageCutAutoView imageCutAutoView;

    private PointAnimateView pointAnimateView;

    private StringAnimateView stringAnimateView;

    private Button btn_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.two_page_layout);
        setContentView(R.layout.activity_main);

        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);

//        imageCutAutoView = findViewById(R.id.view);
//
//        ObjectAnimator cameraXAnimator = ObjectAnimator.ofFloat(imageCutAutoView, "rotateXDegree", 45);
//        cameraXAnimator.setDuration(2500);
//
//        ObjectAnimator bottomAnimator = ObjectAnimator.ofFloat(imageCutAutoView, "rotateDegree", 270);
//        bottomAnimator.setDuration(3000);
//
//        ObjectAnimator topAnimator = ObjectAnimator.ofFloat(imageCutAutoView, "topRotateXDegree", -45);
//        topAnimator.setDuration(3000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(cameraXAnimator, bottomAnimator, topAnimator);
//        animatorSet.setStartDelay(1000);
//        animatorSet.start();


//        pointAnimateView = findViewById(R.id.view);
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(pointAnimateView, "point",
//                new PointEvaluator(), new Point((int) Utils.dp2px(300), (int) Utils.dp2px(300)));
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(3000);
//        objectAnimator.start();


//        stringAnimateView = findViewById(R.id.view);
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(stringAnimateView, "index", new StringEvaluator()
//                , 89);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(9000);
//        objectAnimator.start();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_1:
                startActivity(intent.setClass(this, RecyclerViewTest01.class));
                break;
            default:
                break;
        }
    }

    class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int currentX = (int) (startValue.x + (endValue.x - startValue.x) * fraction);
            int currentY = (int) (startValue.y + (endValue.y - startValue.y) * fraction);
            return new Point(currentX, currentY);
        }
    }

    class StringEvaluator implements TypeEvaluator<Integer> {

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            return (int) (startValue + (endValue - startValue) * fraction);
        }
    }
}
