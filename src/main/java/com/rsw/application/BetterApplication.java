package com.rsw.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.rsw.http.GlideImageLoader;
import com.rsw.imagepicker.ImagePicker;
import com.rsw.imagepicker.view.CropImageView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2018/7/17.
 *///538,194

public class BetterApplication extends android.support.multidex.MultiDexApplication {
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //d029544aa4f1f814225bac69666f1a82 签名
        context = this;
        MultiDex.install(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SDKInitializer.initialize(this);
        //设置使用https请求
        SDKInitializer.setHttpsEnable(true);
        String registrationID = JPushInterface.getRegistrationID(this);//jpush
        System.out.println("极光id=================" + registrationID);
            /*友盟分享初始化*/
        UMShareAPI.get(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setQQZone("1107231656", "oFH5CD7Dr0jvc36N");
        PlatformConfig.setWeixin("wxffa0c053129e8360", "f7df493fa563fd649503967337e914d3");//04055b89d6c62fa5b04a6bbedc02ab1f
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
}
