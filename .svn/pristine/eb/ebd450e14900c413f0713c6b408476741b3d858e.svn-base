package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.rsw.adapter.YinshiListAdapter;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.http.HttpInterface;
import com.rsw.ptr.OnLoadMoreListener;
import com.rsw.ptr.PtrDefaultHandler;
import com.rsw.ptr.PtrFrameLayout;
import com.rsw.views.NetworkImageHolderView;
import com.rsw.views.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class ShouyeActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView   mRecyclerView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrFrameLayout mPtrClassicFrameLayout;
    //带头尾的RecyclerView适配器
    private RecyclerAdapterWithHF mAdapter;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;
    private List<YinshiBean.ListBean> list = new ArrayList<>();
    private YinshiListAdapter adapter;
    /*https://www.cndzys.com/yinshi/changshi/index[参数1].html*/
    private Context           mContext;
    //每页几条数据
    private int size = 20;
    //第几页
    private int page = 1;
    private ShouyeActivity   activity;
    private ConvenientBanner convenientBanner;
    private List<YinshiBean.ListBean> banners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);
        mContext = this;
        activity = this;
        hideDividerLine();
        hideNavigationIcon();
        hideActionBar();
        initView();
        initData();
        initBannerData();
        //   inittest();
    }

    private void inittest() {


        //        ss = ss.replaceAll("\\<a[^\\>]*\\>", "");
        //        ss = ss.replaceAll("</a>", "");
        //        System.out.println("ss==========" + ss);


        //        try {
        //            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/fod",
        //                    "abc.htm");
        //            System.out.println("===================" + Environment.getExternalStorageDirectory().getPath());
        //            if (!file.exists())
        //                file.mkdirs();
        //            FileOutputStream fos = new FileOutputStream(file);
        //
        //
        //            fos.write(ss.getBytes());
        //            fos.close();
        //            System.out.println("==============写入成功==============");
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }


    }

    @Override
    protected CharSequence getTopTitle() {
        return null;
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new YinshiListAdapter(mContext, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        View view = LayoutInflater.from(this).inflate(R.layout.header, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        mAdapter.addHeader(view);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (getScollYDistance() > Density.getSceenHeight(mContext) / 2) {
//                    mTopBtn.setVisibility(View.VISIBLE);
//                } else {
//                    mTopBtn.setVisibility(View.GONE);
//                }
//            }
//        });
        adapter.setOnItemClickLitener(new YinshiListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                YinshiBean.ListBean bean = list.get(position);
                Intent intent = new Intent(activity, ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
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
                        initBannerData();
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

//    private int getScollYDistance() {
//        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//        int position = layoutManager.findFirstVisibleItemPosition();
//        View firstVisiableChildView = layoutManager.findViewByPosition(position);
//        int itemHeight = firstVisiableChildView.getHeight();
//        return (position) * itemHeight - firstVisiableChildView.getTop();
//    }

    private void initBannerData() {
        HttpInterface.getInstance().getBanners(5, new Action1<YinshiBean>() {
            @Override
            public void call(YinshiBean entity) {
                if (null == entity) {
                    convenientBanner.setVisibility(View.GONE);
                    return;
                }
                banners.clear();
                if (entity.getList().size() == 0) {
                    convenientBanner.setVisibility(View.GONE);
                } else {
                    convenientBanner.setVisibility(View.VISIBLE);
                    banners.addAll(entity.getList());
                    loadBannerData();
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

    }

    private void loadBannerData() {
        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {

            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, banners)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        YinshiBean.ListBean bean = banners.get(position);
                        Intent intent = new Intent(activity, ZixunWebViewActivity.class);
                        intent.putExtra("item", bean);
                        startActivity(intent);
                    }
                })
                .setManualPageable(true);
        if (banners.size() > 1) {
            convenientBanner.setPageIndicator(new int[]{R.drawable.hot_white, R.drawable.hot_yellow});
            convenientBanner.setPointViewVisible(true);
            convenientBanner.startTurning(3500);
        }
    }

    private void initData() {
        HttpInterface.getInstance().getYunfuyinshiList(page, size, 7, new Action1<YinshiBean>() {
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

            }
        });
    }

}
