package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rsw.alertview.AlertView;
import com.rsw.alertview.OnDismissListener;
import com.rsw.alertview.OnItemClickListener;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.rsw.views.SwitchView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.sb_push)
    SwitchView sbPush;
    @Bind(R.id.nicheng)
    RelativeLayout layNicheng;
    @Bind(R.id.lay_login_out)
    RelativeLayout layLoginOut;
    private AlertView mAlertView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        sbPush.setShadow(true);
        sbPush.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
                System.out.println("推送打开");
                Toast.makeText(context, "推送打开", Toast.LENGTH_SHORT).show();
           //     JPushInterface.resumePush(getApplicationContext());//接收，可以再任意处设置
//                HttpInterface.getInstance().updUserProperty("2", "1", new Action1<JsonObject>() {//推送打开
//                    @Override
//                    public void call(JsonObject jsonObject) {
//                        gainUserInfo();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });

            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
                System.out.println("推送关闭");
                Toast.makeText(context, "推送关闭", Toast.LENGTH_SHORT).show();
             //   JPushInterface.stopPush(getApplicationContext());//不接收
//                HttpInterface.getInstance().updUserProperty("2", "0", new Action1<JsonObject>() {//推送关闭
//                    @Override
//                    public void call(JsonObject jsonObject) {
//                        gainUserInfo();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });
            }
        });
        mAlertView = new AlertView("提示", "你确定要退出登录吗?", "取消", new String[]{"确定"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    logout();
                }
            }
        }).setCancelable(false).setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });

    }

    public void logout() {
        SPUtil.put(context, Constant.USER_ID, "");
        EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_LOGINGOUT_SUCCESS, ""));
        finish();
    }

    @Override
    protected CharSequence getTopTitle() {
        return "设置";
    }

    @OnClick({R.id.nicheng, R.id.lay_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nicheng:
                if (SPUtil.getString(context, Constant.USER_ID).equals("")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, UpdateNickActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lay_login_out:
                mAlertView.show();
                break;
        }
    }

}
