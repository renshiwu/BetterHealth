package com.rsw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

public class RegisterActivity extends BaseActivity implements TextWatcher {

    @Bind(R.id.register_phone)
    EditText mRegisterPhone;
    @Bind(R.id.register_pass)
    EditText mRegisterPass;
    @Bind(R.id.register_btn)
    TextView mRegisterBtn;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initStatusBar(getWindow());
        setContentView(R.layout.activity_register);
        context = this;
        mRegisterPass.addTextChangedListener(this);
        mRegisterPhone.addTextChangedListener(this);
        hideNavigationIcon();
        setRootColor(R.color.green_main);
        hideDividerLine();
    }

    @Override
    protected CharSequence getTopTitle() {
        return "注册";
    }


    @OnClick({R.id.register_btn, R.id.parent_phone, R.id.parent_pass, R.id.register_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_btn://下一步
                final String phone = mRegisterPhone.getText().toString();
                final String psd = mRegisterPass.getText().toString();
                if (phone.length() == 0) {
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psd.length() == 0) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpInterface.getInstance().register(phone, psd, new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        System.out.println("baseBean===========" + baseBean);
                        if (baseBean.getState().equals("true")) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            HttpInterface.getInstance().login(phone, psd, new Action1<BaseBean>() {
                                @Override
                                public void call(BaseBean baseBean) {
                                    System.out.println("baseBean===========" + baseBean);
                                    if (baseBean.getState().equals("true")) {
                                        SPUtil.put(context, Constant.USER_ID, baseBean.getUser_id());
                                        EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_REGIEST_SUCCESS, ""));
                                        finish();
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
                break;
            case R.id.parent_phone://点击父类获取焦点
                mRegisterPhone.requestFocus();
                break;
            case R.id.parent_pass://点击父类获取焦点
                mRegisterPass.requestFocus();
                break;
            case R.id.register_agree://用户协议
                startActivity(new Intent(this, AgreeDetialActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!mRegisterPass.getText().toString().equals("") && !mRegisterPhone.getText().toString().equals("")) {
            mRegisterBtn.setBackgroundResource(R.drawable.shape_register_btn);
        } else {
            mRegisterBtn.setBackgroundResource(R.drawable.shape_register_btn_32);
        }
    }
}
