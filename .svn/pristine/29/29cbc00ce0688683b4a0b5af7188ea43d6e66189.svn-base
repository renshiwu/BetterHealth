package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsw.adapter.CaipuLeftAdapter;
import com.rsw.adapter.CaipuRightAdapter;
import com.rsw.bean.CpClassBean;
import com.rsw.bean.CpTwoClassBean;
import com.rsw.caipu.R;
import com.rsw.http.HttpInterface;
import com.rsw.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class CaipuClassActivity extends AppCompatActivity {

    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.title)
    TextView     title;
    @Bind(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @Bind(R.id.recyclerView2)
    RecyclerView recyclerView2;


    private LinearLayoutManager linearLayoutManager;
    private List<CpClassBean.CpClass> list = new ArrayList<>();
    private CaipuLeftAdapter adapter;


    private CaipuRightAdapter adapter2;
    private GridLayoutManager gridLayoutManager;
    private List<CpTwoClassBean.CpTwoClass> list2 = new ArrayList<>();
    private Context mContext;
    private int     classid, twoClassid;
    private CaipuClassActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(getWindow());
        setContentView(R.layout.activity_caipu_class);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        initView();
        initData();
    }

    private void initData() {
        HttpInterface.getInstance().getCaipuClass(0, new Action1<CpClassBean>() {
            @Override
            public void call(CpClassBean cpClassBean) {
                if (null == cpClassBean.getCpClass()) {
                    return;
                }
                list.clear();
                list.addAll(cpClassBean.getCpClass());
                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    HttpInterface.getInstance().getCaipuTwoClass(list.get(0).getClassid(), new Action1<CpTwoClassBean>() {
                        @Override
                        public void call(CpTwoClassBean cpTwoClassBean) {
                            if (null == cpTwoClassBean.getCpTwoClass()) {
                                return;
                            }
                            list2.clear();
                            list2.addAll(cpTwoClassBean.getCpTwoClass());
                            adapter2.notifyDataSetChanged();

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
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
        recyclerView1.setLayoutManager(linearLayoutManager);
        adapter = new CaipuLeftAdapter(mContext, list);
        recyclerView1.setAdapter(adapter);
        adapter.setOnItemClickLitener(new CaipuLeftAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();

                classid = list.get(position).getClassid();

                HttpInterface.getInstance().getCaipuTwoClass(classid, new Action1<CpTwoClassBean>() {
                    @Override
                    public void call(CpTwoClassBean cpTwoClassBean) {
                        if (null == cpTwoClassBean.getCpTwoClass()) {
                            return;
                        }
                        list2.clear();
                        list2.addAll(cpTwoClassBean.getCpTwoClass());
                        adapter2.notifyDataSetChanged();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
            }
        });
        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(gridLayoutManager);
        adapter2 = new CaipuRightAdapter(mContext, list2);
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickLitener(new CaipuRightAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, SearchCaipuActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("item", list2.get(position));
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.lay_back)
    public void onViewClicked() {
        finish();
    }
}
