package com.example.day03text1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by 雪碧 on 2019/10/28.
 */

public class RecyAdpter extends RecyclerView.Adapter {
    private ArrayList<Bean.ResultsBean> mDatasBeans;
    private Context mContext;

    public RecyAdpter(ArrayList<Bean.ResultsBean> datasBeans, Context context) {
        mDatasBeans = datasBeans;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item1, null);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item2, null);
            return new ViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type==1){
            ViewHolder viewHolder= (ViewHolder) holder;
            viewHolder.mTvTitle.setText(mDatasBeans.get(position).get_id());
            viewHolder.mTvDesc.setText(mDatasBeans.get(position).getDesc());
            Glide.with(mContext).load(mDatasBeans.get(position).getUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolder.mIvImage);
        }
        if (type==2){
            ViewHolder2 viewHolder2= (ViewHolder2) holder;
            viewHolder2.mTvTitle.setText(mDatasBeans.get(position).get_id());
            viewHolder2.mTvDesc.setText(mDatasBeans.get(position).getDesc());
            Glide.with(mContext).load(mDatasBeans.get(position).getUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolder2.mIvImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnclick!=null){
                    mOnclick.onclick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatasBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView mIvImage;
        TextView mTvTitle;
        TextView mTvDesc;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvImage = (ImageView) itemView.findViewById(R.id.Iv_image);
            this.mTvTitle = (TextView) itemView.findViewById(R.id.Tv_title);
            this.mTvDesc = (TextView) itemView.findViewById(R.id.Tv_desc);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder{
        View view;
        ImageView mIvImage;
        TextView mTvTitle;
        TextView mTvDesc;


        public ViewHolder2(View itemView) {
            super(itemView);
            this.mIvImage = (ImageView) itemView.findViewById(R.id.Iv_image2);
            this.mTvTitle = (TextView) itemView.findViewById(R.id.Tv_title2);
            this.mTvDesc = (TextView) itemView.findViewById(R.id.Tv_desc2);
        }
    }
    interface Onclick{
        void  onclick(int position);
    }
    private  Onclick mOnclick;

    public void setOnclick(Onclick onclick) {
        mOnclick = onclick;
    }
}
