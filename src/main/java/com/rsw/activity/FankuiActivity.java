package com.rsw.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.util.SPUtil;
import com.rsw.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class FankuiActivity extends BaseActivity {

    @Bind(R.id.advise_content)
    EditText adviseContent;
    @Bind(R.id.advise_contact)
    EditText adviseContact;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.count_tv)
    TextView countTv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initBlackStatusBar(getWindow(), this);
        setContentView(R.layout.activity_fankui);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        adviseContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                countTv.setText(s.length() + "");
            }
        });
    }

    @Override
    protected CharSequence getTopTitle() {
        return "反馈";
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (adviseContent.getText().toString().length() == 0) {
            Toast.makeText(this, "说点什么吧", Toast.LENGTH_SHORT).show();
            adviseContent.requestFocus();
            return;
        }

        HttpInterface.getInstance().addFeedBack(SPUtil.getString(context, Constant.USER_ID), adviseContent.getText().toString(), adviseContact.getText().toString(), email.getText().toString(), new Action1<JsonObject>() {
            @Override
            public void call(JsonObject jsonObject) {
                System.out.println("addFeedBack================" + jsonObject);
                if (jsonObject.toString().contains("state")) {
                    if (jsonObject.get("state").getAsBoolean()) {
                        Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });


    }
}
