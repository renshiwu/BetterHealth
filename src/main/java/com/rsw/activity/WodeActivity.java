package com.rsw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.rsw.bean.ImageUploadRes;
import com.rsw.bean.UserInfo;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.imagepicker.ImagePicker;
import com.rsw.imagepicker.bean.ImageItem;
import com.rsw.imagepicker.ui.ImageGridActivity;
import com.rsw.util.ImageUtils;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;
import com.rsw.views.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class WodeActivity extends AppCompatActivity {

    @Bind(R.id.profile_bg)
    RelativeLayout mProfileBg;
    @Bind(R.id.img_profile)
    CircleImageView imgProfile;
    @Bind(R.id.img_profile_layout)
    FrameLayout imgProfileLayout;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int REQUEST_CODE_BACKFROUND = 102;

    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.lay_beijing)
    RelativeLayout mLayBeijing;
    @Bind(R.id.beijing)
    FrameLayout mBeijing;
    @Bind(R.id.line_favorite)
    RelativeLayout mLineFavorite;
    @Bind(R.id.btn_settings)
    RelativeLayout btnSettings;
    @Bind(R.id.lay_feedback)
    RelativeLayout layFeedback;
    @Bind(R.id.line_message)
    RelativeLayout lineMessage;
    @Bind(R.id.line_comment)
    RelativeLayout lineComment;
    @Bind(R.id.line_push)
    RelativeLayout linePush;
    @Bind(R.id.line_history)
    RelativeLayout lineHistory;
    private WodeActivity mActivity;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private String mAttachUUID = "";
    private String fileImgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mActivity = this;
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        if (MessageEvent.CODE_LOGING_SUCCESS == event.getCode()) {
            initView();
        } else if (MessageEvent.CODE_LOGINGOUT_SUCCESS == event.getCode()) {
            txtName.setText("点击登录");
            imgProfile.setImageResource(R.drawable.default_img);
            mBeijing.setBackgroundColor(getResources().getColor(R.color.green_main));
        } else if (MessageEvent.CODE_UPDATENICK_SUCCESS == event.getCode()) {
            HttpInterface.getInstance().getUserInfo(SPUtil.getString(mActivity, Constant.USER_ID), new Action1<UserInfo>() {
                @Override
                public void call(UserInfo userInfo) {
                    if (null == userInfo.getTbUserInfo().getNackName() || "".equals(userInfo.getTbUserInfo().getNackName())) {
                        txtName.setText("设置昵称");
                    } else {
                        txtName.setText(userInfo.getTbUserInfo().getNackName());
                        SPUtil.put(mActivity, "nackName", userInfo.getTbUserInfo().getNackName());

                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {

                }
            });
        } else if (MessageEvent.CODE_REGIEST_SUCCESS == event.getCode()) {
            initView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        if (!SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
            HttpInterface.getInstance().getUserInfo(SPUtil.getString(mActivity, Constant.USER_ID), new Action1<UserInfo>() {
                @Override
                public void call(UserInfo userInfo) {
                    String headImg = userInfo.getTbUserInfo().getHeadImg();
                    System.out.println("headImg=============" + headImg);
                    if (null == userInfo.getTbUserInfo().getNackName() || "".equals(userInfo.getTbUserInfo().getNackName())) {
                        txtName.setText("设置昵称");
                    } else {
                        txtName.setText(userInfo.getTbUserInfo().getNackName());
                        SPUtil.put(mActivity, "nackName", userInfo.getTbUserInfo().getNackName());
                    }
                    if (null != headImg) {
                        Glide.with(mActivity).load(headImg).signature(new StringSignature(UUID.randomUUID().toString())).into(imgProfile);
                    }
                    if (null != userInfo.getTbUserInfo().getBackground() || !"".equals(userInfo.getTbUserInfo().getBackground())) {
                        Glide.with(mActivity)
                                .load(userInfo.getTbUserInfo().getBackground()).asBitmap()
                                .signature(new StringSignature(UUID.randomUUID().toString()))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        Drawable drawable = new BitmapDrawable(resource);
                                        mBeijing.setBackgroundDrawable(drawable);
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
    }

    private void showPic(String picPath) {
        // 如果图片符合要求将其上传到服务器
        if (picPath != null && ((picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG")
                || picPath.endsWith(".JPEG") || picPath.endsWith(".jpeg")
                || picPath.endsWith(".bmp") || picPath.endsWith(".BMP")
                || picPath.endsWith(".gif") || picPath.endsWith(".GIF")))) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 2;
            //减少内存
            option.inInputShareable = true;
            option.inPurgeable = true;
            option.inDither = false;
            option.inPreferredConfig = Bitmap.Config.RGB_565;
            option.inJustDecodeBounds = false;//设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，
            // 即opts.width和opts.height。有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            bm = ImageUtils.headScale(bm);
            // 显示在图片控件上
            if (null != bm) {
                int degree = ImageUtils.readPictureDegree(picPath);
                if (degree != 0) {
                    bm = ImageUtils.rotaingImageView(degree, bm);
                }
                imgProfile.setImageBitmap(bm);
                byte[] imgBytes = ImageUtils.Bitmap2Bytes(bm);
                File fileOut = new File(selImageList.get(0).path);
                System.out.println("imgBytes=============" + imgBytes.length);
                HttpInterface.getInstance().headImgUpload(imgBytes, selImageList.get(0).path, SPUtil.getString(mActivity, Constant.USER_ID), 1, new Action1<ImageUploadRes>() {
                    @Override
                    public void call(ImageUploadRes imageUploadRes) {
                        System.out.println("imageUploadRes===============" + imageUploadRes);
                        Glide.with(mActivity).load(imageUploadRes.getHead_img()).signature(new StringSignature(UUID.randomUUID().toString())).into(imgProfile);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                showPic(images.get(0).getPath());
            } else if (data != null && requestCode == REQUEST_CODE_BACKFROUND) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                showBeijing(images.get(0).getPath());
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);

            }
        }
    }

    private void showBeijing(String picPath) {
        // 如果图片符合要求将其上传到服务器
        if (picPath != null && ((picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG")
                || picPath.endsWith(".JPEG") || picPath.endsWith(".jpeg")
                || picPath.endsWith(".bmp") || picPath.endsWith(".BMP")
                || picPath.endsWith(".gif") || picPath.endsWith(".GIF")))) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 2;
            //减少内存
            option.inInputShareable = true;
            option.inPurgeable = true;
            option.inDither = false;
            option.inPreferredConfig = Bitmap.Config.ARGB_8888;
            option.inJustDecodeBounds = false;//设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，
            // 即opts.width和opts.height。有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // bm = ImageUtils.headScale(bm);
            // 显示在图片控件上
            if (null != bm) {
                int degree = ImageUtils.readPictureDegree(picPath);
                if (degree != 0) {
                    bm = ImageUtils.rotaingImageView(degree, bm);
                }
                mBeijing.setBackgroundDrawable(new BitmapDrawable(bm));
                byte[] imgBytes = ImageUtils.Bitmap2Bytes(bm);
                File fileOut = new File(selImageList.get(0).path);
                HttpInterface.getInstance().headImgUpload(imgBytes, selImageList.get(0).path, SPUtil.getString(mActivity, Constant.USER_ID), 2, new Action1<ImageUploadRes>() {
                    @Override
                    public void call(ImageUploadRes imageUploadRes) {
                        System.out.println("上传背景===============" + imageUploadRes);
                        Glide.with(mActivity)
                                .load(imageUploadRes.getBackground_img()).asBitmap()
                                .signature(new StringSignature(UUID.randomUUID().toString()))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        Drawable drawable = new BitmapDrawable(resource);
                                        mBeijing.setBackgroundDrawable(drawable);
                                    }
                                });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
            }
        }
    }

    @OnClick({R.id.img_profile, R.id.txt_name, R.id.lay_beijing, R.id.line_favorite, R.id.btn_settings,
            R.id.lay_feedback, R.id.line_message, R.id.line_comment, R.id.line_push, R.id.line_history})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_profile:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    ImagePicker.getInstance().setSelectLimit(1);
                    intent = new Intent(mActivity, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SELECT);
                }
                break;
            case R.id.txt_name:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, UpdateNickActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lay_beijing:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    ImagePicker.getInstance().setSelectLimit(1);
                    intent = new Intent(mActivity, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_BACKFROUND);
                }

                break;
            case R.id.line_favorite:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, CollectionListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_settings:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lay_feedback:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, FankuiActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.line_message:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, MessageListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.line_comment:
                Toast.makeText(mActivity, "暂无评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.line_push:
                Toast.makeText(mActivity, "暂无推送", Toast.LENGTH_SHORT).show();
                break;
            case R.id.line_history:
                if (SPUtil.getString(mActivity, Constant.USER_ID).equals("")) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, WodeShihuaActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

}
