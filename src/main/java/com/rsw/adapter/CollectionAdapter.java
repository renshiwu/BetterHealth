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
import com.rsw.swipe.SwipeItemLayout;
import com.rsw.util.DateUtils;
import com.rsw.util.Util;

import java.util.List;

/**
 * Created by RSW on 2018/9/19.
 * com.rsw.adapter
 */

public class CollectionAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ItemTouchListener {
        void onItemClick(View view, int position);

        void onRightMenuClick(int position);
    }

    private ItemTouchListener mItemTouchListener;

    public void setOnItemTouchListener(ItemTouchListener mItemTouchListener) {
        this.mItemTouchListener = mItemTouchListener;
    }

    LayoutInflater mInflater;
    List<YinshiBean.ListBean> mDatas;
    Context context;

    public CollectionAdapter(Context context, List<YinshiBean.ListBean> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mDatas = mDatas;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView iv_image;
        TextView tv_title;
        TextView tv_content;
        View lay_one, lay_three, lay_tip;
        TextView tv_title2;
        ImageView iv_img1, iv_img2, iv_img3;
        TextView tv_author;
        TextView tv_time;
        TextView tv_pick;
        TextView tv_date;
        TextView mDeleteText;
        SwipeItemLayout mSwipeItemLayout;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater
                .inflate(R.layout.collect_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iv_image = (ImageView) view
                .findViewById(R.id.iv_image);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
        viewHolder.lay_one = view.findViewById(R.id.lay_one);
        viewHolder.lay_three = view.findViewById(R.id.lay_three);
        viewHolder.lay_tip = view.findViewById(R.id.lay_tip);

        viewHolder.tv_pick = (TextView) view.findViewById(R.id.tv_pick);
        viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);


        viewHolder.tv_author = (TextView) view.findViewById(R.id.tv_author);
        viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);

        viewHolder.tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
        viewHolder.iv_img1 = (ImageView) view
                .findViewById(R.id.iv_img1);
        viewHolder.iv_img2 = (ImageView) view
                .findViewById(R.id.iv_img2);
        viewHolder.iv_img3 = (ImageView) view
                .findViewById(R.id.iv_img3);

        viewHolder.mDeleteText = (TextView) view.findViewById(R.id.item_sliding_delete);
        viewHolder.mSwipeItemLayout = (SwipeItemLayout) view.findViewById(R.id.swipe_layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        YinshiBean.ListBean entity = mDatas.get(position);
        if (null == entity.getImage() || !entity.getImage().contains("@rsw@")) {
            viewHolder.lay_one.setVisibility(View.VISIBLE);
            viewHolder.lay_three.setVisibility(View.GONE);
            String imgUrl = "";
            if (null != entity.getImg()) {
                imgUrl = entity.getImg();
            } else {
                imgUrl = entity.getImgPath();
                imgUrl = Constant.BASE_IMG_URL + imgUrl;
            }
            System.out.println("imgUrl=============" + imgUrl);
            Glide.with(context).load(imgUrl).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(viewHolder.iv_image);
            //设置占位图，在加载之前显示,.thumbnail(0.1f) thumbnail设置缩略图
            viewHolder.tv_title.setText(entity.getName());
            if (null != entity.getDescription() && !"".equals(entity.getDescription())) {
                viewHolder.tv_content.setVisibility(View.VISIBLE);
                viewHolder.lay_tip.setVisibility(View.GONE);
                viewHolder.tv_content.setText(entity.getDescription());
            } else {
                viewHolder.lay_tip.setVisibility(View.VISIBLE);
                viewHolder.tv_content.setVisibility(View.GONE);
                if (null != entity.getPick()) {
                    viewHolder.tv_pick.setVisibility(View.VISIBLE);
                    viewHolder.tv_pick.setText(Util.ellpiseText(entity.getPick()));
                } else {
                    viewHolder.tv_pick.setVisibility(View.GONE);
                }
                if (null != entity.getTime() && !"".equals(entity.getTime())) {
                    viewHolder.tv_date.setText(DateUtils.stampToDate(entity.getTime()));
                    viewHolder.tv_date.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_date.setVisibility(View.GONE);
                }
            }

        } else {
            viewHolder.lay_one.setVisibility(View.GONE);
            viewHolder.lay_three.setVisibility(View.VISIBLE);
            String imgUrl = entity.getImage();
            String[] strs = imgUrl.split("@rsw@");
            if (strs.length >= 3) {
                Glide.with(context).load(strs[0]).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(viewHolder.iv_img1);
                Glide.with(context).load(strs[1]).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(viewHolder.iv_img2);
                Glide.with(context).load(strs[2]).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(viewHolder.iv_img3);
            }
            viewHolder.tv_title2.setText(entity.getName());
            System.out.println("entity.getPick()=============" + entity.getPick());

            if (null != entity.getPick()) {
                viewHolder.tv_author.setText(entity.getPick());
                viewHolder.tv_author.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_author.setVisibility(View.GONE);
            }
            if (null != entity.getTime() && !"".equals(entity.getTime())) {
                viewHolder.tv_time.setText(DateUtils.stampToDate(entity.getTime()));
                viewHolder.tv_time.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_time.setVisibility(View.GONE);
            }
        }
        if (mItemTouchListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onItemClick(viewHolder.itemView,
                            position);
                }
            });

            if (viewHolder.mDeleteText != null) {
                viewHolder.mDeleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemTouchListener.onRightMenuClick(position);
                        viewHolder.mSwipeItemLayout.close();
                    }
                });
            }

        }


    }


}