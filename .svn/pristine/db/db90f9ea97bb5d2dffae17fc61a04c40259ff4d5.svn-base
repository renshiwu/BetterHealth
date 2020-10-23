package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.rsw.adapter.CaipuAdapter;
import com.rsw.adapter.GridAdapter;
import com.rsw.bean.CpClassBean;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrClassicFrameLayout;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.views.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class CaipuActivity extends AppCompatActivity {
    CaipuActivity activity;
    Context       mContext;
    @Bind(R.id.recyclerView)
    RecyclerView          mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.lay_class)
    LinearLayout          layClass;
    @Bind(R.id.lay_right)
    LinearLayout          layRight;

    private RecyclerAdapterWithHF mAdapter;
    private Handler                   handler = new Handler();
    private List<YinshiBean.ListBean> list    = new ArrayList<>();
    private CaipuAdapter      adapter;
    private GridLayoutManager gridLayoutManager;
    private int page = 1;
    private int size = 30;
    private GridAdapter gridAdapter;
    private GridView    gridView;
    private List<CpClassBean.CpClass> cplist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caipu);
        ButterKnife.bind(this);
        mContext = this;
        activity = this;
        initView();
        initData();
        getCpClass();
    }

    private void getCpClass() {
        HttpInterface.getInstance().getCaipuClass(1, new Action1<CpClassBean>() {
            @Override
            public void call(CpClassBean cpClassBean) {
                if (null == cpClassBean.getCpClass()) {
                    return;
                }
                cplist.clear();
                cplist.addAll(cpClassBean.getCpClass());
                CpClassBean.CpClass bean = new CpClassBean.CpClass();
                bean.setName("全部分类");
                bean.setClassid(-1);
                cplist.add(bean);
                gridAdapter.notifyDataSetChanged();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void initData() {
        HttpInterface.getInstance().getCaipuList("", page, size, new Action1<YinshiBean>() {
            @Override
            public void call(YinshiBean entity) {
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
                mPtrClassicFrameLayout.refreshComplete();
                mPtrClassicFrameLayout.setLoadMoreEnable(false);
            }
        });
    }


    private void initView() {
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CaipuAdapter(mContext, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
//        View header = LayoutInflater.from(activity).inflate(R.layout.caipu_header, null);
//        mAdapter.addHeader(header);
//        gridView = (GridView) header.findViewById(R.id.gridView);
//        gridAdapter = new GridAdapter(mContext, cplist);
//        gridView.setAdapter(gridAdapter);

        mRecyclerView.setAdapter(mAdapter);
        mPtrClassicFrameLayout.autoRefresh(false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.getItemViewType(position) == mAdapter.TYPE_FOOTER) {
                    return 3;
                } else if (mAdapter.getItemViewType(position) == mAdapter.TYPE_HEADER) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });

        adapter.setOnItemClickLitener(new CaipuAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                YinshiBean.ListBean bean = list.get(position);
                Intent intent = new Intent(activity, ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
                intent.putExtra("type", 2);
                intent.putExtra("baseUrl", Constant.BASE_CP_HTML_URL);
                startActivity(intent);
            }
        });
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        initData();
                        getCpClass();
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

    @OnClick({R.id.lay_class, R.id.lay_right})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lay_class:
                intent = new Intent(this, CaipuClassActivity.class);
                startActivity(intent);
                break;
            case R.id.lay_right:
                intent = new Intent(this, SearchCaipuActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
        }
    }

}
