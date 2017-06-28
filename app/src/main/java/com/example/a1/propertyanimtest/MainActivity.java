package com.example.a1.propertyanimtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    // 购物车父布局
    private RelativeLayout mShoppingCartRly;
    // 购物车列表显示
    private ListView mShoppingCartLv;
    // 购物数目显示
    private TextView mShoppingCartCountTv;
    // 购物车图片显示
    private ImageView mShoppingCartIv;

    //购物车商品数目
    private int goodsCount=0;
    private List<GoodsModel> mData;
    private GoodsAdapter goodsAdapter;
    private PathMeasure mPathMeasure;
    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition=new float[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findView
        mShoppingCartLv = (ListView) findViewById(R.id.lv_bezier_curve_shopping_cart);
        mShoppingCartCountTv = (TextView) findViewById(R.id.tv_bezier_curve_shopping_cart_count);
        mShoppingCartRly = (RelativeLayout) findViewById(R.id.rly_bezier_curve_shopping_cart);
        mShoppingCartIv = (ImageView) findViewById(R.id.iv_bezier_curve_shopping_cart);
        
        //是否显示购物车商品数目
        isShowCartGoodsCount();
        
        //添加数据源
        addData();
        
        //设置适配器
        setAdapter();
        
    }

    private void setAdapter() {
        Logger.d("setAdapter()");
        goodsAdapter = new GoodsAdapter(this, mData);
        //设置适配器监听
        goodsAdapter.setmCallBackListener(new GoodsAdapter.CallBackListener() {
            @Override
            public void callBackImg(ImageView goodImg) {
                Logger.d("setAdapter()----222");
                //添加商品到购物车
                addGoodsToCart(goodImg);
            }
        });
        //设置适配器
        mShoppingCartLv.setAdapter(goodsAdapter);
    }

    //添加商品到购物车
    private void addGoodsToCart(ImageView goodImg) {

        Logger.d("addGoodsToCart()----111");
        final ImageView goods = new ImageView(this);
        goods.setImageDrawable(goodImg.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        mShoppingCartRly.addView(goods,params);

        //
        int [] parentLocation=new int[2];
        mShoppingCartRly.getLocationInWindow(parentLocation);

        //开始坐标
        int startLoc[]=new int[2];
        goodImg.getLocationInWindow(startLoc);

        //结束坐标
        int endLoc[]=new int[2];
        mShoppingCartIv.getLocationInWindow(endLoc);

        //开始掉落的起始点
        float startX=startLoc[0] - parentLocation[0] +goodImg.getWidth()/2;
        float startY=startLoc[1] - parentLocation[1] +goodImg.getHeight()/2;

        //掉落后终点坐标
        float toX=endLoc[0]-parentLocation[0]+mShoppingCartIv.getWidth()/5;
        float toY=endLoc[1]-parentLocation[1];

        //开始绘制曲线
        Path path = new Path();
        //移动到起始点
        path.moveTo(startX,startY);
        path.quadTo((startX+toX)/2,startY,toX,toY);

        mPathMeasure = new PathMeasure(path, false);

        //属性动画实现
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,mPathMeasure.getLength());
        valueAnimator.setDuration(500);

        //匀速
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
               mPathMeasure.getPosTan(value,mCurrentPosition,null);
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //加1
                goodsCount++;
                isShowCartGoodsCount();
                mShoppingCartCountTv.setText(String.valueOf(goodsCount));
                //把执行动画的图片从父布局移除
                mShoppingCartRly.removeView(goods);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


    }


    private void addData() {

        Logger.d("addData()----111");
        //初始数据源
        mData = new ArrayList<>();
        //添加数据
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setmGoodBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.goods_one));
        mData.add(goodsModel);

        goodsModel=new GoodsModel();
        goodsModel.setmGoodBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.goods_two));
        mData.add(goodsModel);

        goodsModel=new GoodsModel();
        goodsModel.setmGoodBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.goods_three));
        mData.add(goodsModel);

    }


    private void isShowCartGoodsCount() {
        if(goodsCount==0){
            mShoppingCartCountTv.setVisibility(View.GONE);
        }else{
            mShoppingCartCountTv.setVisibility(View.VISIBLE);
        }
    }
}
