/*
Copyright 2015 Chanven

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.rsw.ptr;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 加载更多布局切换接口
 *
 * @author Chanven
 */
public interface ILoadMoreViewFactory {

    ILoadMoreView madeLoadMoreView();

    /**
     * ListView底部加载更多的布局切换
     *
     * @author Chanven
     */
    interface ILoadMoreView {

        /**
         * 初始化
         *
         * @param footViewHolder
         * @param onClickLoadMoreListener 加载更多的点击事件，需要点击调用加载更多的按钮都可以设置这个监听
         */
        void init(FootViewAdder footViewHolder, OnClickListener onClickLoadMoreListener);

        /**
         * 显示普通布局
         */
        void showNormal();

        /**
         * 显示已经加载完成，没有更多数据的布局
         */
        void showNomore();

        /**
         * 显示正在加载中的布局
         */
        void showLoading();

        /**
         * 显示加载失败的布局
         *
         * @param e
         */
        void showFail(Exception e);

        void setFooterVisibility(boolean isVisible);

    }

    interface FootViewAdder {

        View addFootView(View view);

        View addFootView(int layoutId);

    }

}
