package com.rsw.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsw.caipu.R;
import com.rsw.util.Util;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity {

    private   int            mNavigationIcon;
    private   Drawable       mNavigationDrawable;
    private   Toolbar        mToolbar;
    private   TextView       mTitleTextView;
    private   ImageView      mIv_center_log;
    private   TextView       tv_right;
    private   RelativeLayout lay_del;
    private   LinearLayout   root;
    private int mToolBarBackgroundColor = -1;
    private View      mDivider_line;
    private ImageView iv_right;
    private Context   context;
    private View      tianchong_view;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initBlackStatusBar(getWindow(), this);
        context = this;
        mNavigationIcon = R.drawable.back_grey;
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(100);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        View toolView = findViewById(R.id.toolbar);
        mDivider_line = findViewById(R.id.divider_line);
        tianchong_view = findViewById(R.id.tianchong_view);
        tv_right = (TextView) findViewById(R.id.tv_right);
        lay_del = (RelativeLayout) findViewById(R.id.lay_del);
        if (null != tianchong_view) {
            if (Build.VERSION.SDK_INT <= 19) {//解决4.4以下无法沉浸状态栏造成的bug
                tianchong_view.setVisibility(View.GONE);
            } else {
                tianchong_view.setVisibility(View.VISIBLE);
            }
        }
        root = (LinearLayout) findViewById(R.id.top_root);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        if (null != toolView) {
            mToolbar = (Toolbar) toolView;
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mTitleTextView = (TextView) toolView.findViewById(R.id.tv_title);
            mIv_center_log = (ImageView) toolView.findViewById(R.id.iv_center_log);
            CharSequence title = getTopTitle();
            if (null != title && title.length() > 0) {
                mTitleTextView.setText(title);
            } else {
                mTitleTextView.setText("");
            }
            showNavigationIcon();
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickNavigationIcon();
                }
            });

            if (-1 != mToolBarBackgroundColor) {
                mToolbar.setBackgroundColor(mToolBarBackgroundColor);
            }
        }
    }

    public void showNavigationIcon() {//显示返回按钮
        mToolbar.setNavigationIcon(mNavigationIcon);
        if (null != mNavigationDrawable) {
            mToolbar.setNavigationIcon(mNavigationDrawable);
        }
    }

    public void hideNavigationIcon() {//隐藏返回按钮
        mToolbar.setNavigationIcon(null);
    }

    public void changeRightText(String text) {
        tv_right.setText(text);
    }

    public void hideRightText() {//隐藏右侧文本按钮
        lay_del.setVisibility(View.GONE);
    }

    public void showRightText() {//显示右侧文本按钮
        lay_del.setVisibility(View.VISIBLE);
    }

    protected void onClickRight(int src, View.OnClickListener v) {//点击右侧按钮
        iv_right.setImageResource(src);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v);
    }

    public void showRight() {//显示右侧按钮
        iv_right.setVisibility(View.VISIBLE);
    }

    public void hideRight() {//隐藏右侧按钮
        iv_right.setVisibility(View.GONE);
    }

    public void hideDividerLine() {//隐藏分割线
        mDivider_line.setVisibility(View.GONE);
    }

    public void hideActionBar() {//隐藏导航栏
        root.setVisibility(View.GONE);
    }

    public void showActionBar() {//显示导航栏
        root.setVisibility(View.VISIBLE);
    }


    public void setToolBarBackgroundColor(int toolBarBackgroundColor) {
        this.mToolBarBackgroundColor = toolBarBackgroundColor;
    }

    protected abstract CharSequence getTopTitle();


    //重写标题名
    protected void setBaseTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setBaseTitleColor(int color) {
        mTitleTextView.setTextColor(getResources().getColor(color));
    }

    public void setRootColor(int color) {//设置导航栏颜色
        root.setBackgroundColor(getResources().getColor(color));
        mTitleTextView.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_white);
        tv_right.setTextColor(getResources().getColor(R.color.white));
    }


    protected void onClickNavigationIcon() {//返回点击
        finish();
    }

    //设置导航icon，通常都是返回icon
    protected void setNavigationIcon(int iconResourceId) {
        mNavigationIcon = iconResourceId;
        showNavigationIcon();
    }

    protected void setNavigationIcon(Drawable navigationDrawable) {
        mNavigationDrawable = navigationDrawable;
        showNavigationIcon();
    }

    protected void setDisableNavigationIcon() {
        mToolbar.setNavigationIcon(null);
    }

    protected void setCenterImageEnable() {
        mTitleTextView.setVisibility(View.GONE);
        mIv_center_log.setVisibility(View.VISIBLE);
    }

    protected String getRightTitle() {
        return "";
    }

    protected int getRightIcon() {
        return -1;
    }

    protected void onClickRight() {

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }




    public void launch(Class<? extends Activity> clazz) {
        launch(new Intent(this, clazz));
    }

    public void launch(Intent intent) {
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right) {
            onClickRight();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        boolean isKeepRight = false;
        String rightTitle = getRightTitle();
        if (!TextUtils.isEmpty(rightTitle)) {
            menu.getItem(0).setTitle(rightTitle);
            isKeepRight = true;
        }
        int rightIcon = getRightIcon();
        if (-1 != rightIcon) {
            menu.getItem(0).setIcon(rightIcon);
            isKeepRight = true;
        }
        if (!isKeepRight) {
            menu.clear();
        }
        return true;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
