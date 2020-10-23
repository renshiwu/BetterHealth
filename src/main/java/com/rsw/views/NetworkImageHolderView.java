package com.rsw.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;

/**
 * Created by RSW on 2018/7/9.
 * com.rsw.views
 */

public class NetworkImageHolderView implements Holder<YinshiBean.ListBean> {
    private ImageView imageView;
    private TextView  title;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        imageView = (ImageView) view.findViewById(R.id.iv_img);
        title = (TextView) view.findViewById(R.id.tv_title);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, YinshiBean.ListBean item) {
        String imgUrl = item.getImg();
        Glide.with(context).load(imgUrl).into(imageView);
        title.setText(item.getName());
    }

}
