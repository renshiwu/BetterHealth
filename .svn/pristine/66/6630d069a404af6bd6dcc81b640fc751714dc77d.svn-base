package com.rsw.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.rsw.caipu.R;
import com.rsw.util.Density;
import com.rsw.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MTabActivity extends TabActivity implements View.OnClickListener {

    TabHost mTabHost;
    TabWidget tabWidget;
    Context context;
    MTabActivity activity;
    @Bind(R.id.main_tab_group)
    LinearLayout mMainTabGroup;
    @Bind(R.id.iv_bottom)
    ImageView mIvBottom;

    private RadioButton tab_button1, tab_button2, tab_button3, tab_button4;

    private int mState = 1;
    private RadioButton[] rbs;
    private FrameLayout r1, r2, r3, r4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initBlackStatusBar(this.getWindow(), this);
        setContentView(R.layout.main_tb);
        ButterKnife.bind(this);
        context = this;
        activity = this;
        tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        initTab();
        switchState(1);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initTab() {
        mTabHost = (TabHost) getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("1")
                .setContent(new Intent(this, ZixunActivity.class)));

        mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator("2")
                .setContent(new Intent(this, CaipuActivity.class)));

        mTabHost.addTab(mTabHost.newTabSpec("3").setIndicator("3")
                .setContent(new Intent(this, ShequActivity.class)));

        mTabHost.addTab(mTabHost.newTabSpec("4").setIndicator("4")
                .setContent(new Intent(this, WodeActivity.class)));

        tab_button1 = (RadioButton) findViewById(R.id.mainTabs_radio1);
        tab_button2 = (RadioButton) findViewById(R.id.mainTabs_radio2);
        tab_button3 = (RadioButton) findViewById(R.id.mainTabs_radio3);
        tab_button4 = (RadioButton) findViewById(R.id.mainTabs_radio4);

        r1 = (FrameLayout) findViewById(R.id.r1);
        r2 = (FrameLayout) findViewById(R.id.r2);
        r3 = (FrameLayout) findViewById(R.id.r3);
        r4 = (FrameLayout) findViewById(R.id.r4);
        rbs = new RadioButton[4];
        //初始化控件，中间大个的，周围小弟
        rbs[0] = tab_button1;
        rbs[1] = tab_button2;
        rbs[2] = tab_button3;
        rbs[3] = tab_button4;


        Drawable drawableFirst = getResources().getDrawable(R.drawable.t_sy_selector);
        drawableFirst.setBounds(0, 0, Density.dp2px(context, 18), Density.dp2px(context, 18));
        rbs[0].setCompoundDrawables(null, drawableFirst, null, null);

        Drawable drawableFirst2 = getResources().getDrawable(R.drawable.t_xz_selector);
        drawableFirst2.setBounds(0, 0, Density.dp2px(context, 18), Density.dp2px(context, 18));
        rbs[1].setCompoundDrawables(null, drawableFirst2, null, null);

        Drawable drawableFirst3 = getResources().getDrawable(R.drawable.t_xiaoxi_selector);
        drawableFirst3.setBounds(0, 0, Density.dp2px(context, 18), Density.dp2px(context, 18));
        rbs[2].setCompoundDrawables(null, drawableFirst3, null, null);

        Drawable drawableFirst4 = getResources().getDrawable(R.drawable.t_wd_selector);
        drawableFirst4.setBounds(0, 0, Density.dp2px(context, 18), Density.dp2px(context, 18));
        rbs[3].setCompoundDrawables(null, drawableFirst4, null, null);


        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);

    }

    private void switchState(int state) {
        mState = state;
        tab_button1.setChecked(false);
        tab_button2.setChecked(false);
        tab_button3.setChecked(false);
        tab_button4.setChecked(false);

        switch (mState) {
            case 1:
                tab_button1.setChecked(true);
                mTabHost.setCurrentTabByTag("1");
                Util.initStatusBar(activity.getWindow());
                break;
            case 2:
                tab_button2.setChecked(true);
                mTabHost.setCurrentTabByTag("2");
                Util.initStatusBar(activity.getWindow());
                break;
            case 3:
                tab_button3.setChecked(true);
                mTabHost.setCurrentTabByTag("3");
                Util.initStatusBar(activity.getWindow());
                break;
            case 4:
                tab_button4.setChecked(true);
                mTabHost.setCurrentTabByTag("4");
                Util.initStatusBar(activity.getWindow());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.r1:
                switchState(1);
                break;
            case R.id.r2:
                switchState(2);
                break;
            case R.id.r3:
                switchState(3);
                break;
            case R.id.r4:
                switchState(4);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}