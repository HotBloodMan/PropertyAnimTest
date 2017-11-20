package com.ljt.properanim;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a1.propertyanimtest.R;

public class SecondActivity extends Activity implements View.OnClickListener {
    public static String TAG= SecondActivity.class.getSimpleName();
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mButton = (Button) findViewById(R.id.btn_second);
        mButton.setOnClickListener(this);
        ObjectAnimator.ofFloat().start();
    }

    @Override
    public void onClick(View v) {
        if(v==mButton){
            Log.d(TAG,TAG+" ----->>>v==mButton ");
            performAnimate(mButton,mButton.getWidth(),500);
        }
    }
    private void performAnimate(final View target, final int start, final int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator=new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值
                int currentValue= (int) animator.getAnimatedValue();
                 Log.d(TAG,TAG+" ----->>>currentValue= "+currentValue);
                //获得当前进度占整个动画过程的比例 浮点型 0~1
                float fraction = animator.getAnimatedFraction();
                //直接通过整形估值器，通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width=mEvaluator.evaluate(fraction,start,end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }
}
