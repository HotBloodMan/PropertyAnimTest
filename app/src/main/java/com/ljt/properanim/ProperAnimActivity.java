package com.ljt.properanim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a1.propertyanimtest.R;

public class ProperAnimActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPro;
    private ImageView ivPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proper_anim);
        btnPro = (Button) findViewById(R.id.btn_proper);
        ivPro = (ImageView) findViewById(R.id.iv_pro);
        btnPro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //1通过 ObjectAnimator.ofFloat(<控件句柄>,<控件属性>,<从什么float值>,<到什么float值
//        ObjectAnimator animator = ObjectAnimator.ofFloat(ivPro, "alpha", 1f, 0f, 1f);
//        animator.setDuration(1500);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.start();
        //2 动画合并
//        ObjectAnimator animator = ObjectAnimator.ofFloat(ivPro,"alpha",1f,0f,1f);
//        ValueAnimator animator1 = ObjectAnimator.ofFloat(ivPro,"rotation",0,180,0);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivPro,"scaleY",0,3,1);
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(ivPro,"scaleX",0,3,1);
//        AnimatorSet animatorSet = new AnimatorSet();
        /*
        * 先实例化这个属性动画集合 AnimatorSet set = new AnimatorSet().

　　　　　　通过set.play(<animator1>).with(<animator2>);来表示让Animator1和Animator2一起执行，

　　　　　　通过set.play(<animator1>).after(<animator2>)来表示让Animator1在Animator2后执行

　　　　　　通过set.play(<animator1>).before(<animator2>)来表示让Animator1在Animator2之前执行、
        *
        * */
//        animatorSet.play(animator1).with(animator2).with(animator3);
//        animatorSet.setDuration(3000);
//        animatorSet.start();

        //3为了是属性复用化，节省代码操作，我们把对控件的一组属性的一次操作提取出来称为一个属性操作集
        // ，比如，我想对控件进行平移和旋转，我们可以先把
       //平移和旋转（以及他们操作的数值）单独拿出来作为一个属性，然后控件调用这个动画集，
        // 就可以实现无论什么控件都可以公用这个属性动画。
        PropertyValuesHolder pro = PropertyValuesHolder.ofFloat("translationX",0F,100F);
        PropertyValuesHolder pro2 = PropertyValuesHolder.ofFloat("rotation",0F,360F);
        ObjectAnimator.ofPropertyValuesHolder(ivPro,pro,pro2);

        /*
        * ValueAnimator并不会改变属性的大小，他只是在一段时间生成某些值，比如上面的ofFloat中设置了从0F,100F,并设置时间为1000
　　　　　　则ValueAnimator的作用就是在1000中匀速生成0F到100F的值，然后再为控件在1000中为控件的属性设上不同值，这就是动画的原理
        *
        * */
        ValueAnimator value = ValueAnimator.ofFloat(0F,100F);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
         public void onAnimationUpdate(ValueAnimator animation) {
//            通过设置一个计时器，就是每秒就为控件设置获取到的值
          float value = (float) animation.getAnimatedValue();
            ivPro.scrollTo(-(int) value,0);
          }
          });
        value.setDuration(3000);
        value.start();
//        value.ofObject(new TypeEvaluator<Float>() {
//            @Override
//            public Float evaluate(float fraction, Float startValue, Float endValue) {
        /*
        * 我们可以反回任意的值，其中fraction是从0到1，相当于进度的百分比,我们可以通过条件语句判断来
        * 反回真正需要反回的值，然后通过addUpdateListener
　　　　  就可以很灵活的实现各种值的反回
        *
        * */
//            return null;
//            }
//            },0F,100F);



    }
}
