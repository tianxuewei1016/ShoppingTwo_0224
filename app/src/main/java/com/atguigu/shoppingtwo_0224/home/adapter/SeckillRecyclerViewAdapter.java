package com.atguigu.shoppingtwo_0224.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingtwo_0224.R;
import com.atguigu.shoppingtwo_0224.home.bean.HomeBean;
import com.atguigu.shoppingtwo_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/12 18:05
 * QQ：93226539
 * 作用：秒杀的适配器
 */

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<HomeBean.ResultEntity.SeckillInfoEntity.ListEntity> datas;

    public SeckillRecyclerViewAdapter(Context mContext, HomeBean.ResultEntity.SeckillInfoEntity seckill_info) {
        this.mContext = mContext;
        this.datas = seckill_info.getList();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeBean.ResultEntity.SeckillInfoEntity.ListEntity listEntity = datas.get(position);
        //绑定数据
        holder.tvCoverPrice.setText("￥" + listEntity.getCover_price());
        holder.tvOriginPrice.setText("￥" + listEntity.getOrigin_price());
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE + listEntity.getFigure())
                .into(holder.ivFigure);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_figure)
        ImageView ivFigure;
        @InjectView(R.id.tv_cover_price)
        TextView tvCoverPrice;
        @InjectView(R.id.tv_origin_price)
        TextView tvOriginPrice;
        @InjectView(R.id.ll_root)
        LinearLayout llRoot;

        public MyViewHolder(final View view) {
            super(view);
            ButterKnife.inject(this, view);

            //设置每条的点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
