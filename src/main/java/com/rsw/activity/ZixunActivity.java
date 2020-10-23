package com.rsw.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.fragment.Fragment1;
import com.rsw.fragment.Fragment10;
import com.rsw.fragment.Fragment11;
import com.rsw.fragment.Fragment2;
import com.rsw.fragment.Fragment3;
import com.rsw.fragment.Fragment4;
import com.rsw.fragment.Fragment5;
import com.rsw.fragment.Fragment6;
import com.rsw.fragment.Fragment7;
import com.rsw.fragment.Fragment8;
import com.rsw.fragment.Fragment9;
import com.rsw.http.OkGoUpdateHttpUtil;
import com.rsw.util.CProgressDialogUtils;
import com.rsw.util.Util;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZixunActivity extends AppCompatActivity {
    @Bind(R.id.msg_ViewPager)
    ViewPager msgViewPager;
    MsgViewPagerAdapter myPageAdapter;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private String mUpdateUrl = Constant.BASE_URL + "getVersion";
    private ZixunActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(getWindow());
        setContentView(R.layout.shouye_layout);
        ButterKnife.bind(this);
        activity = this;
        initView();
        getappVersion();
    }

    private void getappVersion() {
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(mUpdateUrl)
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //不显示通知栏进度条
                //                .dismissNotificationProgress()
                //是否忽略版本
                //                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度，如果是强制更新，则设置无效
                //                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                .setTopPic(R.drawable.top_6)
                //为按钮，进度条设置颜色。
                .setThemeColor(getResources().getColor(R.color.main_green))
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                //                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
                //                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。

                    }
                })
                //不自动，获取
                //                .setIgnoreDefParams(true)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        System.out.println("json==============" + json);
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject obj = new JSONObject(json);
                            JSONArray array = obj.getJSONArray("result");
                            if (array.length() == 1) {
                                JSONObject jsonObject = (JSONObject) array.get(0);
                                final String newVersion = jsonObject.optString("version");
                                updateAppBean
                                        //（必须）是否更新Yes,No
                                        .setUpdate(jsonObject.optString("update"))
                                        //（必须）新版本号，
                                        .setNewVersion(newVersion)
                                        //（必须）下载地址
                                        .setApkFileUrl(jsonObject.optString("apkFileUrl"))
                                        //测试下载路径是重定向路径
                                        .setUpdateLog(jsonObject.optString("updateLog"));
                                //是否强制更新，可以不设置
                                int must = jsonObject.optInt("constraint");
                                int code = jsonObject.optInt("code");
                                System.out.println("code==========" + code);
                                updateAppBean.setCode(code);
                                if (must == 1) {
                                    updateAppBean.setConstraint(true);
                                } else {
                                    updateAppBean.setConstraint(false);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(activity);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(activity);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {

                    }
                });
    }


    private void initView() {
        myPageAdapter = new MsgViewPagerAdapter(getSupportFragmentManager());

        ArrayList<Fragment> mList = new ArrayList<Fragment>();
        mList.add(new Fragment1());
        mList.add(new Fragment10());
        mList.add(new Fragment2());
        mList.add(new Fragment3());
        mList.add(new Fragment4());
        mList.add(new Fragment5());
        mList.add(new Fragment6());
        mList.add(new Fragment7());
        mList.add(new Fragment8());
        mList.add(new Fragment9());
        mList.add(new Fragment11());
        myPageAdapter.setData(mList);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("推荐");
        titles.add("社会");
        titles.add("百科");
        titles.add("母婴");
        titles.add("瘦身");
        titles.add("情感");
        titles.add("心理");
        titles.add("女性");
        titles.add("男性");
        titles.add("旅游");
        titles.add("时尚");
        myPageAdapter.setTitles(titles);

        msgViewPager.setOffscreenPageLimit(0);
        msgViewPager.setAdapter(myPageAdapter);
        // 将ViewPager与TabLayout相关联
        tablayout.setupWithViewPager(msgViewPager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private class MsgViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<String> titles;
        ArrayList<Fragment> mList;

        public MsgViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(ArrayList<Fragment> mList) {
            this.mList = mList;
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles == null ? null : titles.get(position);
        }
    }


}
