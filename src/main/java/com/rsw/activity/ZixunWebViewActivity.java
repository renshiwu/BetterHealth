package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsw.bean.YinshiBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ZixunWebViewActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView       mTvTitle;
    @Bind(R.id.webView)
    WebView        mWebView;
    @Bind(R.id.lay_shoucang)
    RelativeLayout mLayShoucang;
    @Bind(R.id.lay_fenxiang)
    RelativeLayout mLayFenxiang;
    @Bind(R.id.img_shoucang)
    ImageView      mImgShoucang;
    @Bind(R.id.lay_bottom)
    LinearLayout   mLayBottom;
    @Bind(R.id.root)
    LinearLayout   root;
    private YinshiBean.ListBean bean;
    private String webStr = "";
    private Context context;
    private String baseUrl = "";
    private ZixunWebViewActivity activity;
    private int     type       = 2;
    private String  collect_id = "";
    private boolean isSHoucang = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        context = this;
        activity = this;
        bean = (YinshiBean.ListBean) getIntent().getSerializableExtra("item");
        baseUrl = getIntent().getStringExtra("baseUrl");
        type = getIntent().getIntExtra("type", 2);
        collect_id = bean.getId() + "";
        if (type == 1) {
            root.setVisibility(View.VISIBLE);
        } else {
            root.setVisibility(View.GONE);
        }
        init();
        initState();
    }

    private void initState() {
        if (!SPUtil.getString(activity, Constant.USER_ID).equals("")) {
            HttpInterface.getInstance().getCollectionState(SPUtil.getString(activity, Constant.USER_ID), collect_id, new Action1<JsonObject>() {
                @Override
                public void call(JsonObject jsonObject) {
                    System.out.println("getCollectionState================" + jsonObject);
                    if (jsonObject.toString().contains("state")) {
                        if (jsonObject.get("state").getAsInt() == 1) {
                            mImgShoucang.setImageResource(R.drawable.shoucang);
                            isSHoucang = true;

                        } else {
                            mImgShoucang.setImageResource(R.drawable.shoucanghui);
                            isSHoucang = false;
                        }
                    } else {
                        mImgShoucang.setImageResource(R.drawable.shoucanghui);
                        isSHoucang = false;
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {


                }
            });
        }
    }

    private void init() {
        webStr = bean.getContent();
        final WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setTextZoom(100);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        // 启用硬件加速
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);

        String CSS_STYLE = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/normal.css\"/>";

        mWebView.loadDataWithBaseURL("about:blank", webStr + CSS_STYLE, "text/html", "utf-8", null);


    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println();
            view.loadUrl(url);
            return true;

        }
    };

    public void share() {
        UMWeb web = new UMWeb(Constant.BASE_URL + "page/newsShare.html?id=" + bean.getId() + "&type=" + type);
        UMImage image;
        web.setTitle(bean.getName());
        web.setDescription(bean.getName());
        if (null == bean.getImg() || "".equals(bean.getImg())) {
            image = new UMImage(activity, R.drawable.default_img);
        } else {
            image = new UMImage(activity, bean.getImg());
        }
        web.setThumb(image);
        ShareBoardConfig shareBoardConfig = new ShareBoardConfig();
        shareBoardConfig.setCancelButtonText("取消");
        new ShareAction(activity).withText("分享").setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE).withMedia(web)
                .setCallback(shareListener).open(shareBoardConfig);
    }

    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示
            System.out.println("---onStart---");
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            System.out.println("---onResult---");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            System.out.println("---onError---");
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            System.out.println("---onCancel---");
            Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected CharSequence getTopTitle() {
        return "详情";
    }

    @OnClick({R.id.lay_shoucang, R.id.lay_fenxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_shoucang:
                if (SPUtil.getString(activity, Constant.USER_ID).equals("")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (isSHoucang) {
                    HttpInterface.getInstance().deleteCollection(SPUtil.getString(activity, Constant.USER_ID), collect_id, new Action1<JsonObject>() {
                        @Override
                        public void call(JsonObject jsonObject) {
                            System.out.println("deleteCollection==============" + jsonObject);
                            if (jsonObject.toString().contains("state")) {
                                if (jsonObject.get("state").getAsInt() == 1) {
                                    EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_UPDATE_COLLECTION_STATE, ""));
                                    Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                                    mImgShoucang.setImageResource(R.drawable.shoucanghui);
                                    isSHoucang = false;
                                } else {
                                    Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
                                    mImgShoucang.setImageResource(R.drawable.shoucang);
                                    isSHoucang = true;
                                }
                            } else {
                                Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
                                mImgShoucang.setImageResource(R.drawable.shoucang);
                                isSHoucang = true;
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });


                } else {
                    HttpInterface.getInstance().saveCollection(SPUtil.getString(activity, Constant.USER_ID), collect_id, new Action1<JsonObject>() {
                        @Override
                        public void call(JsonObject jsonObject) {
                            if (jsonObject.toString().contains("state")) {
                                if (jsonObject.get("state").getAsInt() == 1) {
                                    EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_UPDATE_COLLECTION_STATE, ""));
                                    Toast.makeText(activity, "收藏成功", Toast.LENGTH_SHORT).show();
                                    mImgShoucang.setImageResource(R.drawable.shoucang);
                                    isSHoucang = true;
                                } else if (jsonObject.get("state").getAsInt() == 2) {
                                    Toast.makeText(activity, "您已经收藏过", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "收藏失败", Toast.LENGTH_SHORT).show();
                                    mImgShoucang.setImageResource(R.drawable.shoucanghui);
                                    isSHoucang = false;
                                }
                            } else {
                                Toast.makeText(activity, "收藏失败", Toast.LENGTH_SHORT).show();
                                mImgShoucang.setImageResource(R.drawable.shoucanghui);
                                isSHoucang = false;
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });
                }
                break;
            case R.id.lay_fenxiang:
                share();
                break;
        }
    }
}
