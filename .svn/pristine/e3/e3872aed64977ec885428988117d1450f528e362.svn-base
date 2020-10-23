package com.rsw.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rsw.adapter.ShequAdapter;
import com.rsw.bean.ShequBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrClassicFrameLayout;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.swipe.SwipeMenuRecyclerView;
import com.rsw.util.SPUtil;
import com.rsw.util.Util;
import com.rsw.views.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class WodeShihuaActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    SwipeMenuRecyclerView mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.show)
    FrameLayout mShow;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.empty)
    RelativeLayout mEmpty;
    private List<ShequBean.ListBean> list = new ArrayList<>();
    private ShequAdapter adapter;
    private Context mContext;
    private List<Integer> imgs = new ArrayList<>();
    private int page = 1;
    private int size = 10;
    private RecyclerAdapterWithHF mAdapter;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initBlackStatusBar(getWindow(), this);
        setContentView(R.layout.activity_wode_shihua);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        HttpInterface.getInstance().getShihuaByuserId(SPUtil.getString(mContext, Constant.USER_ID), page, size, new Action1<ShequBean>() {
            @Override
            public void call(ShequBean jsonObject) {
                if (null == jsonObject && list.size() == 0) {
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                if (page == 1) {
                    list.clear();
                }
                list.addAll(jsonObject.getList());
                if (list.size() == 0) {
                    mShow.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                } else {
                    mEmpty.setVisibility(View.GONE);
                    mShow.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                mPtrClassicFrameLayout.refreshComplete();
                if (jsonObject.getList().size() < size) {
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    mPtrClassicFrameLayout.setLoadMoreEnable(true);
                    mPtrClassicFrameLayout.loadMoreComplete(true);
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("jsonObject============" + throwable);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ShequAdapter(mContext, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        initData();
                    }
                }, 500);
            }
        });

        mPtrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        initData();
                    }
                }, 500);
            }
        });
    }

    @Override
    protected CharSequence getTopTitle() {
        return "我的食话";
    }
}

