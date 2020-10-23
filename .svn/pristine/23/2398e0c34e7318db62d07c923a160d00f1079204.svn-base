package com.rsw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rsw.bean.ShequBean;
import com.rsw.caipu.R;
import com.rsw.views.CircleImageView;

import java.util.List;

/**
 * Created by RSW on 2018/7/30.
 * com.rsw.adapter
 */


public class ShequAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    LayoutInflater mInflater;
    private Context mContext;
    private List<ShequBean.ListBean> mDatas;

    public ShequAdapter(Context context, List<ShequBean.ListBean> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mItemImg;
        TextView mItemUserName;
        TextView mItemDescript;
        CircleImageView mItemHeader;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater
                .inflate(R.layout.shequ_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mItemImg = (ImageView) view
                .findViewById(R.id.item_img);
        viewHolder.mItemUserName = (TextView) view.findViewById(R.id.user_name);
        viewHolder.mItemDescript = (TextView) view.findViewById(R.id.item_descript);
        viewHolder.mItemHeader = (CircleImageView) view.findViewById(R.id.user_headerImg);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mItemDescript.setText(mDatas.get(position).getContent());
        Glide.with(mContext).load(mDatas.get(position).getImg()).into(viewHolder.mItemImg);
        viewHolder.mItemUserName.setText((null == mDatas.get(position).getNickName() || "".equals(mDatas.get(position).getNickName())) ? "精典用户" : mDatas.get(position).getNickName());
        if ("".equals(mDatas.get(position).getHeadImg()) || null == mDatas.get(position).getHeadImg()) {
            viewHolder.mItemHeader.setImageResource(R.drawable.default_icon);
        } else {
            Glide.with(mContext).load(mDatas.get(position).getHeadImg()).into(viewHolder.mItemHeader);
        }
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView,
                            position);
                }
            });

        }


    }


}














