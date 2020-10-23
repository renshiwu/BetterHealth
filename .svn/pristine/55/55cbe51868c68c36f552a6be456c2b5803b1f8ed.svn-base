package com.rsw.ptr;

import android.view.View;
import android.view.View.OnClickListener;


public interface LoadMoreHandler {

    /**
     * @param contentView
     * @param loadMoreView
     * @param onClickLoadMoreListener
     * @return 是否有 init ILoadMoreView
     */
    boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, OnClickListener
            onClickLoadMoreListener);

    void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

    void removeFooter();

    void addFooter();
}
