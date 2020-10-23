package com.rsw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsw.adapter.ImagePickerAdapter;
import com.rsw.caipu.R;
import com.rsw.constants.Constant;
import com.rsw.http.HttpInterface;
import com.rsw.imagepicker.ImagePicker;
import com.rsw.imagepicker.bean.ImageItem;
import com.rsw.imagepicker.ui.ImageGridActivity;
import com.rsw.imagepicker.ui.ImagePreviewDelActivity;
import com.rsw.util.ImageUtils;
import com.rsw.util.MessageEvent;
import com.rsw.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class AddShihuaActivity extends BaseActivity {

    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_publish)
    TextView tvPublish;
    @Bind(R.id.et_edit)
    EditText etEdit;
    @Bind(R.id.edit_number_tv)
    TextView editNumberTv;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList;
    private int maxImgCount = 1;
    private int size = 0;
    private List<String> attUUIDS = new ArrayList<>();
    private AddShihuaActivity mActivity;
    private BitmapFactory.Options option = new BitmapFactory.Options();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shihua);
        ButterKnife.bind(this);
        mActivity = this;
        initWidget();
        initView();
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case IMAGE_ITEM_ADD:
                        if (adapter.getItemCount() == 4) {
                            Toast.makeText(AddShihuaActivity.this, "最多上传1张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(mActivity, ImageGridActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        break;
                    default:
                        //打开预览
                        Intent intentPreview = new Intent(mActivity, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                        break;
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        option.inSampleSize = 2;
        //减少内存
        option.inInputShareable = true;
        option.inPurgeable = true;
        option.inDither = false;
        option.inPreferredConfig = Bitmap.Config.ARGB_8888;
        option.inJustDecodeBounds = false;//设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，
        // 即opts.width和opts.height。有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
        // 根据图片的SDCard路径读出Bitmap
        etEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editNumberTv.setText(s.length() + "");
            }
        });
    }

    @Override
    protected CharSequence getTopTitle() {
        return "发布食话";
    }

    @OnClick({R.id.tv_cancel, R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_publish:
                upLoadImage();
                break;
        }
    }

    private void upLoadImage() {
        System.out.println("selImageList===============" + selImageList.size());

        for (int i = 0; i < selImageList.size(); i++) {
            String picPath = selImageList.get(i).getPath();
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            if (null != bm) {
                int degree = ImageUtils.readPictureDegree(picPath);
                if (degree != 0) {
                    bm = ImageUtils.rotaingImageView(degree, bm);
                }
                byte[] imgBytes = ImageUtils.Bitmap2Bytes(bm);
                System.out.println("imgBytes===============" + imgBytes.length);
                System.out.println("selImageList.get(0).path===============" + selImageList.get(0).path);
                System.out.println((int) ((Math.random() * 9 + 1) * 100000));
                String name = (selImageList.get(0).path.substring(selImageList.get(0).path.lastIndexOf("/") + 1));
                HttpInterface.getInstance().addShihua(imgBytes, name, selImageList.get(0).path, SPUtil.getString(mActivity, Constant.USER_ID), etEdit.getText().toString(), new Action1<JsonObject>() {
                    @Override
                    public void call(JsonObject jsonObject) {
                        System.out.println("addShihua===============" + jsonObject);
                        if (jsonObject.get("state").getAsBoolean()) {
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.CODE_ADDSHIHUA_SUCCESS, ""));
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
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
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }
}


