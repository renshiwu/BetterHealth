package com.rsw.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.rsw.util.Util;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class UpdateNickActivity extends BaseActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.editText)
    EditText editText;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initBlackStatusBar(getWindow(), this);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        editText.setText(SPUtil.getString(context, "nackName"));
    }

    @Override
    protected CharSequence getTopTitle() {
        return "修改昵称";
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (editText.getText().toString().length() == 0) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpInterface.getInstance().updateNick(SPUtil.getString(context, Constant.USER_ID), editText.getText().toString(), new Action1<JsonObject>() {
            @Override
            public void call(JsonObject jsonObject) {
                System.out.println("getCollectionState================" + jsonObject);
                if (jsonObject.toString().contains("state")) {
                    if (jsonObject.get("state").getAsInt() == 1) {
                        Toast.makeText(context, "设置昵称成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_UPDATENICK_SUCCESS, ""));
                        finish();
                    } else {
                        Toast.makeText(context, "设置昵称失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "设置昵称失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
