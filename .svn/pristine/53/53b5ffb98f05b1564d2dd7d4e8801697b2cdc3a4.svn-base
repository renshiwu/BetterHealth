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

package com.rsw.http;

import android.app.Activity;

import com.rsw.util.Util;

import java.io.IOException;
import java.net.ConnectException;

import rx.functions.Action1;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/3/15
 * <br /> email: chenshufei2@sina.com
 */
public abstract class ErrorAction implements Action1<Throwable> {

    private Activity activity;

    public ErrorAction(Activity mvpView) {
        this.activity = mvpView;
    }


    @Override
    public void call(Throwable throwable) {
        if (null != activity) {
            if (activity.isFinishing()) {
                return;
            }
            if (!Util.hasNetwork(activity)) {
                System.out.println("----没有网络-----");
                // activity.onNoNetworkErrorTip();
            } else if (throwable instanceof ConnectException || throwable instanceof IOException) {
                //  activity.onServerErrorTip();
                System.out.println("----服务器异常-----");

            } else {
                //                    mvpView.onSystemException();
                System.out.println("----系统异常-----");

            }
        }
        onCall(throwable);
    }

    public abstract void onCall(Throwable throwable);
}
