package com.rsw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rsw.bean.CpClassBean;
import com.rsw.caipu.R;

import java.util.List;

/**
 * Created by Administrator on 2018/7/22.
 */


public class CaipuLeftAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    LayoutInflater mInflater;
    List<CpClassBean.CpClass> mDatas;
    Context context;
    private int selectedPosition = 0;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public CaipuLeftAdapter(Context context, List<CpClassBean.CpClass> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mDatas = mDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView name;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater
                .inflate(R.layout.left_adapter_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        CpClassBean.CpClass entity = mDatas.get(position);
        viewHolder.name.setText(entity.getName());
        if (position == selectedPosition) {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.green_main));
        } else {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.gray_cf));
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.c99));
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