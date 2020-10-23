package com.rsw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsw.bean.CpClassBean;
import com.rsw.caipu.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<CpClassBean.CpClass> menus;
    private int[] imgs = {R.drawable.icon01,
            R.drawable.icon02,
            R.drawable.icon03,
            R.drawable.icon04, R.drawable.icon05,
            R.drawable.icon06, R.drawable.icon07,
            R.drawable.icon08};

    public GridAdapter(Context mContext, List<CpClassBean.CpClass> menus) {
        super();
        this.mContext = mContext;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus == null ? 0 : menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (menus == null) {
            return null;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            final LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cp_grid_item, null);
            holder.tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);
            holder.iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CpClassBean.CpClass menu = menus.get(position);
        holder.tv_menu.setText(menu.getName());
        holder.iv_menu.setImageResource(imgs[position]);

        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_menu;
        public ImageView iv_menu;

    }


}
