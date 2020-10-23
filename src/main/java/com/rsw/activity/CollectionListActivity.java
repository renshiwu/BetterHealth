package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsw.adapter.CollectionAdapter;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrClassicFrameLayout;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.swipe.SwipeMenuRecyclerView;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.rsw.views.RecyclerAdapterWithHF;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class CollectionListActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    SwipeMenuRecyclerView mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.show)
    FrameLayout mShow;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.empty)
    RelativeLayout mEmpty;
    //带头尾的RecyclerView适配器
    private RecyclerAdapterWithHF mAdapter;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;
    private List<YinshiBean.ListBean> list = new ArrayList<>();
    private CollectionAdapter adapter;
    private Context mContext;
    //每页几条数据
    private int size = 10;
    //第几页
    private int page = 1;
    private int type = 2;
    private CollectionListActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mActivity = this;
        mContext = this;
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        if (MessageEvent.CODE_UPDATE_COLLECTION_STATE == event.getCode()) {
            page = 1;
            initData();
        }
    }

    private void initData() {

        HttpInterface.getInstance().getCollection(SPUtil.getString(mActivity, Constant.USER_ID), page, size, new Action1<YinshiBean>() {
            @Override
            public void call(YinshiBean jsonObject) {
                System.out.println("getCollection=======" + jsonObject.toString());

                if (null == jsonObject && list.size() == 0) {
                    mShow.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                if (page == 1) {
                    list.clear();
                }
                if (jsonObject.toString().contains("list")) {
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
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CollectionAdapter(mContext, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);
        adapter.setOnItemTouchListener(new CollectionAdapter.ItemTouchListener() {
            @Override
            public void onItemClick(View view, int position) {
                YinshiBean.ListBean bean = list.get(position);
                Intent intent = new Intent(mContext, ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
                intent.putExtra("type", 1);
                startActivity(intent);
            }

            @Override
            public void onRightMenuClick(int position) {
                YinshiBean.ListBean bean = list.get(position);
                HttpInterface.getInstance().deleteCollection(SPUtil.getString(mActivity, Constant.USER_ID), bean.getId() + "", new Action1<JsonObject>() {
                    @Override
                    public void call(JsonObject jsonObject) {
                        System.out.println("deleteCollection==============" + jsonObject);
                        if (jsonObject.toString().contains("state")) {
                            if (jsonObject.get("state").getAsInt() == 1) {
                                Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                                page = 1;
                                initData();
                            } else {
                                Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
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

    @Override
    protected CharSequence getTopTitle() {
        return "我的收藏";
    }
}
