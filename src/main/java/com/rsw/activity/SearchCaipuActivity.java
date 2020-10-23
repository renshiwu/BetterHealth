package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rsw.adapter.CaipuAdapter;
import com.rsw.bean.CpTwoClassBean;
import com.rsw.bean.HotBean;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrClassicFrameLayout;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.util.Density;
import com.rsw.util.Util;
import com.rsw.views.FlowLayout;
import com.rsw.views.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class SearchCaipuActivity extends AppCompatActivity {

    @Bind(R.id.lay_back)
    LinearLayout          mLayBack;
    @Bind(R.id.lay_search)
    LinearLayout          mLaySearch;
    @Bind(R.id.et_search)
    EditText              mEtSearch;
    @Bind(R.id.recyclerView)
    RecyclerView          mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.lay_result)
    LinearLayout          mLayResult;
    @Bind(R.id.flowLayout)
    FlowLayout            mFlowLayout;
    @Bind(R.id.lay_hot)
    LinearLayout          mLayHot;
    @Bind(R.id.tv_tip)
    TextView              mTvTip;
    private SearchCaipuActivity   activity;
    private RecyclerAdapterWithHF mAdapter;
    private Handler                   handler    = new Handler();
    private List<YinshiBean.ListBean> listResult = new ArrayList<>();
    private CaipuAdapter      adapter;
    private GridLayoutManager gridLayoutManager;
    private int                    page = 1;
    private int                    size = 15;
    private List<HotBean.ListBean> list = new ArrayList<>();
    private Context mContext;
    private String keyWords = "";
    private CpTwoClassBean.CpTwoClass item;
    private int                       classid;
    private int                       twoClassid;
    private int                       type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(getWindow());
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        activity = this;
        mContext = this;
        initView();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            item = (CpTwoClassBean.CpTwoClass) getIntent().getSerializableExtra("item");
            keyWords = item.getName();
            mLayResult.setVisibility(View.VISIBLE);
            mLayHot.setVisibility(View.GONE);
            mEtSearch.setText(keyWords);
            getData(keyWords);
        }
        getHot();
    }

//    private void getTypeData() {
//        HttpInterface.getInstance().getCaipuByClass(classid, twoClassid, page, size, new Action1<YinshiBean>() {
//            @Override
//            public void call(YinshiBean entity) {
//                if (null == entity) {
//                    mPtrClassicFrameLayout.refreshComplete();
//                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
//                    return;
//                }
//                if (page == 1) {
//                    listResult.clear();
//                }
//                if (entity.getList().size() == 0) {
//                    mPtrClassicFrameLayout.refreshComplete();
//                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
//                    return;
//                }
//                listResult.addAll(entity.getList());
//                if (listResult.size() == 0) {
//                    mLayResult.setVisibility(View.GONE);
//                    mLayHot.setVisibility(View.VISIBLE);
//                    return;
//                }
//                mLayResult.setVisibility(View.VISIBLE);
//                mLayHot.setVisibility(View.GONE);
//                adapter.notifyDataSetChanged();
//                mPtrClassicFrameLayout.refreshComplete();
//                if (entity.getList().size() < size) {
//                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
//                } else {
//                    mPtrClassicFrameLayout.setLoadMoreEnable(true);
//                    mPtrClassicFrameLayout.loadMoreComplete(true);
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//
//            }
//        });
//    }


    private void initView() {
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CaipuAdapter(mContext, listResult);
        mAdapter = new RecyclerAdapterWithHF(adapter);
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
                YinshiBean.ListBean bean = listResult.get(position);
                Intent intent = new Intent(activity, ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
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
                        getData(keyWords);
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
                        getData(keyWords);
                    }
                }, 500);
            }
        });
    }

    private void getHot() {
        HttpInterface.getInstance().getHotKeys(new Action1<HotBean>() {
            @Override
            public void call(HotBean cpHotBean) {
                System.out.println("getHotKeys===========" + cpHotBean.toString());
                if (null == cpHotBean.getList()) {
                    return;
                }
                list.addAll(cpHotBean.getList());
                mFlowLayout.removeAllViews();
                for (int i = 0; i < list.size(); i++) {
                    int ranHeight = Density.dp2px(activity, 25);
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                    lp.setMargins(Density.dp2px(activity, 5), 0, Density.dp2px(activity, 5), 0);
                    TextView tv = new TextView(activity);
                    tv.setPadding(Density.dp2px(activity, 10), 0, Density.dp2px(activity, 10), 0);
                    tv.setTextColor(Color.parseColor("#bebbbb"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    tv.setText(list.get(i).getName());
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setLines(1);
                    tv.setBackgroundResource(R.drawable.hot_key_bg);
                    mFlowLayout.addView(tv, lp);
                }
                mFlowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        page = 1;
                        listResult.clear();
                        adapter.notifyDataSetChanged();
                        mLayResult.setVisibility(View.VISIBLE);
                        mLayHot.setVisibility(View.GONE);
                        keyWords = list.get(position).getName();
                        mEtSearch.setText(keyWords);
                        getData(keyWords);
                    }
                });

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(activity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(String keyWords) {
        HttpInterface.getInstance().getCaipuList(keyWords, page, size, new Action1<YinshiBean>() {
            @Override
            public void call(YinshiBean entity) {
                if (null == entity) {
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                if (page == 1) {
                    listResult.clear();
                }
                if (entity.getList().size() == 0) {
                    mPtrClassicFrameLayout.refreshComplete();
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                    return;
                }
                listResult.addAll(entity.getList());
                if (listResult.size() == 0) {
                    mLayResult.setVisibility(View.GONE);
                    mLayHot.setVisibility(View.VISIBLE);
                    return;
                }
                mLayResult.setVisibility(View.VISIBLE);
                mLayHot.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                mPtrClassicFrameLayout.refreshComplete();
                if (entity.getList().size() < size) {
                    mPtrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    mPtrClassicFrameLayout.setLoadMoreEnable(true);
                    mPtrClassicFrameLayout.loadMoreComplete(true);
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

    @OnClick({R.id.lay_back, R.id.lay_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_back:
                if (mLayResult.getVisibility() == View.VISIBLE) {
                    listResult.clear();
                    adapter.notifyDataSetChanged();
                    mLayResult.setVisibility(View.GONE);
                    mLayHot.setVisibility(View.VISIBLE);
                    mEtSearch.setText("");
                } else {
                    finish();
                }
                break;
            case R.id.lay_search:
                page = 1;
                listResult.clear();
                adapter.notifyDataSetChanged();
                Util.hidejianpan(this);
                mLayResult.setVisibility(View.VISIBLE);
                mLayHot.setVisibility(View.GONE);
                keyWords = mEtSearch.getText().toString().trim();
                getData(keyWords);
                break;
        }
    }
}
