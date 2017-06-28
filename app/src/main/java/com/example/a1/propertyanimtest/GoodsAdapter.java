package com.example.a1.propertyanimtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/6/28.
 */

public class GoodsAdapter extends BaseAdapter {

    //数据源（购物车商品图片
    private List<GoodsModel> mData;
    //布局
    private LayoutInflater mLayoutInflater;
    //回调监听
    private CallBackListener mCallBackListener;

    public GoodsAdapter(Context context, List<GoodsModel> mData){
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData !=null ? mData.size() :0 ;
    }

    @Override
    public Object getItem(int position) {
        return mData !=null?mData.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
        if(convertView==null){
            convertView=mLayoutInflater.inflate(R.layout.adapter_shopping_cart_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            //复用ViewHolder
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //更新UI
        if(position<mData.size()){
            viewHolder.updataUI(mData.get(position));
        }

        return convertView;
    }




    class ViewHolder{
        //显示商品图片
        ImageView mShoppingCartItemIV;

        public ViewHolder(View view){
            mShoppingCartItemIV = (ImageView) view.findViewById(R.id.iv_shopping_cart_item);
            view.findViewById(R.id.tv_shopping_cart_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mShoppingCartItemIV !=null&&mCallBackListener!=null){
                        mCallBackListener.callBackImg(mShoppingCartItemIV);
                    }
                }
            });
        }


        public  void updataUI(GoodsModel goodsModel){
            if(goodsModel!=null&& goodsModel.getmGoodBitmap()!=null
                    && mShoppingCartItemIV!=null){
                mShoppingCartItemIV.setImageBitmap(goodsModel.getmGoodBitmap());
            }
        }
    }



    public void setmCallBackListener(CallBackListener mCallBackListener) {
        this.mCallBackListener = mCallBackListener;
    }

    public interface  CallBackListener{
        void callBackImg(ImageView goodImg);
    }
}
