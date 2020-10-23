package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rsw.bean.BaseBean;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.rsw.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.lay_del)
    RelativeLayout mLayDel;
    @Bind(R.id.register_phone)
    EditText       mRegisterPhone;
    @Bind(R.id.register_pass)
    EditText       mRegisterPass;
    @Bind(R.id.login_miss)
    TextView       mLoginMiss;
    @Bind(R.id.login_btn)
    TextView       mLoginBtn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(getWindow());
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        context = this;
        initView();
    }

    private void initView() {
        hideNavigationIcon();
        setRootColor(R.color.green_main);
        hideDividerLine();
        changeRightText("注册");
        showRightText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected CharSequence getTopTitle() {
        return "登录";
    }

    @OnClick(R.id.lay_del)
    public void onClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        if (MessageEvent.CODE_REGIEST_SUCCESS == event.getCode()) {
            finish();
        }
    }

    @OnClick({R.id.login_miss, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_miss:
                break;
            case R.id.login_btn:
                String phone = mRegisterPhone.getText().toString();
                String psd = mRegisterPass.getText().toString();
                if (phone.length() == 0) {
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psd.length() == 0) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpInterface.getInstance().login(phone, psd, new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean.getState().equals("true")) {
                            SPUtil.put(context, Constant.USER_ID, baseBean.getUser_id());
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_LOGING_SUCCESS, ""));
                            finish();
                        } else {
                            Toast.makeText(context, "登录失败，用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

                break;
        }
    }
}
