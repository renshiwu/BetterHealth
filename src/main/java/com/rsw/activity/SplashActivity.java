/*
 * Copyright 2016. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.rsw.bean.SplashBean;
import com.rsw.caipu.R;
import com.rsw.http.HttpInterface;
import com.rsw.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;


public class SplashActivity extends AppCompatActivity   {

    @Bind(R.id.img)
    ImageView      img;
    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(this.getWindow());
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        HttpInterface.getInstance().getSplashImage(new Action1<SplashBean>() {
            @Override
            public void call(SplashBean jsonObject) {
                if (null != jsonObject.getData()) {
                    Glide.with(SplashActivity.this).load(jsonObject.getData().get(0).getImg()).into(img);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainPage();
            }
        }, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    public void toMainPage() {
        Intent intent = new Intent(this, MTabActivity.class);
        startActivity(intent);
        finish();
    }


    //防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


