package com.rsw.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.rsw.caipu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgreeDetialActivity extends BaseActivity {

    @Bind(R.id.agree_web)
    WebView mAgreeWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_detial);
        ButterKnife.bind(this);
        mAgreeWeb.loadUrl("file:///android_asset/xieyi.html");
    }

    @Override
    protected CharSequence getTopTitle() {
        return "用户协议";
    }
}
