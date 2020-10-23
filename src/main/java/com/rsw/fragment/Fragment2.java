package com.rsw.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsw.activity.ZixunWebViewActivity;
import com.rsw.adapter.YinshiListAdapter;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.views.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class Fragment2 extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrFrameLayout mPtrClassicFrameLayout;
    //带头尾的RecyclerView适配器
    private RecyclerAdapterWithHF mAdapter;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;
    private List<YinshiBean.ListBean> list = new ArrayList<>();
    private YinshiListAdapter adapter;
    private Context mContext;
    //每页几条数据
    private int size = 10;
    //第几页
    private int page = 1;
    private int type = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.activity_one, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity().getApplicationContext();
        initView();
        initData();
        return view;
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new YinshiListAdapter(mContext, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);
        adapter.setOnItemClickLitener(new YinshiListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                YinshiBean.ListBean bean = list.get(position);
                Intent intent = new Intent(mContext, ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        mPtrClassicFrameLayout.autoRefresh(false);

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


    public String getSeason() {
        int seasonNumber = Calendar.getInstance().get(Calendar.MONTH);
        return seasonNumber >= 1 && seasonNumber <= 3 ? "春"
                : seasonNumber >= 4 && seasonNumber <= 6 ? "夏"
                : seasonNumber >= 7 && seasonNumber <= 9 ? "秋" : seasonNumber >= 10 ? "冬" : "冬";
    }

    private void initData() {

        HttpInterface.getInstance().getYunfuyinshiList(page, size, type, new Action1<YinshiBean>() {
            @Override
            public void call(YinshiBean entity) {
                System.out.println("entity==========="+entity.toString());
                if (null == mPtrClassicFrameLayout) {
                    return;
                }
                if (null == entity) {
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                if (page == 1) {
                    list.clear();
                }
                if (entity.getList().size() == 0) {
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                if (entity.getList().size() > 0) {
                    list.addAll(entity.getList());
                    adapter.notifyDataSetChanged();
                    mPtrClassicFrameLayout.refreshComplete();
                    if (entity.getList().size() < size) {
                        mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        mPtrClassicFrameLayout.setLoadMoreEnable(true);
                        mPtrClassicFrameLayout.loadMoreComplete(true);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable========"+throwable);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
