package com.rsw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;

import java.util.List;

/**
 * Created by RSW on 2018/7/18.
 * com.rsw.adapter
 */

public class CaipuAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    LayoutInflater            mInflater;
    List<YinshiBean.ListBean> mDatas;
    Context                   context;

    public CaipuAdapter(Context context, List<YinshiBean.ListBean> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mDatas = mDatas;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView iv_image;
        TextView  tv_title;

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater
                .inflate(R.layout.caipu_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iv_image = (ImageView) view
                .findViewById(R.id.img);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        YinshiBean.ListBean entity = mDatas.get(position);
        String imgUrl = "";
        if (null != entity.getImg()) {
            imgUrl = entity.getImg();
        } else {
            imgUrl = entity.getImgPath();
            imgUrl = Constant.BASE_CP_IMG_URL + imgUrl;
        }
        System.out.println("imgUrl=====================" + imgUrl);
        Glide.with(context).load(imgUrl).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(viewHolder.iv_image); //设置占位图，在加载之前显示,.thumbnail(0.1f) thumbnail设置缩略图
        viewHolder.tv_title.setText(entity.getName());
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