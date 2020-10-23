package com.rsw.ptr;//package com.bulaitesi.bdhr.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.rsw.caipu.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by RSW on 2017/9/1.
 * baidertrs.zhijienet.ptr
 */


public class PtrClassicAnimHeader extends FrameLayout implements PtrUIHandler {

    private final static String           KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static       SimpleDateFormat sDataFormat           = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private              int              mRotateAniTime        = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    //    private TextView        mTitleTextView;
    private long mLastUpdateTime = -1;
    private String  mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;

    private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();


    // 动画持续时间
    private final int ROTATE_ANIM_DURATION = 180;
    //    private AnimationDrawable frameAnim;
    private Context   context;

    //    int pics[] = {R.drawable.loading1, R.drawable.loading2, R.drawable.loading3, R.drawable.loading4,
    //            R.drawable.loading5, R.drawable.loading6, R.drawable.loading7, R.drawable.loading8};

    private RelativeLayout mHeaderViewContent;

    private View      header;

    public PtrClassicAnimHeader(Context context) {
        super(context);
        this.context = context;
        initViews(null);
    }

    public PtrClassicAnimHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public PtrClassicAnimHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.PtrClassicHeader, 0, 0);
        if (arr != null) {
            mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime);
        }
        //    buildAnimation();
        //View header = LayoutInflater.from(getContext()).inflate(R.layout.cube_ptr_classic_default_header, this);
        header = LayoutInflater.from(getContext()).inflate(R.layout.ptr_anim_header, this);
        //        mTitleTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
        mHeaderViewContent = (RelativeLayout) header.findViewById(R.id.mHeaderViewContent);
        //        initloading();
        //       resetView();
    }

    //    public void initloading() {
    //        // 通过逐帧动画的资源文件获得AnimationDrawable示例
    //        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.loading);
    //        // 把AnimationDrawable设置为ImageView的背景
    //        iv_frame.setBackgroundDrawable(frameAnim);
    //    }

    //    public void animstart() {
    //        if (frameAnim != null && !frameAnim.isRunning()) {
    //            iv_frame.setBackgroundDrawable(null);
    //            initloading();
    //            iv_frame.post(new Runnable() {
    //                @Override
    //                public void run() {
    //                    frameAnim.start();
    //                }
    //            });
    //        }
    //
    //    }

    //    public void setanimPos(int state) {
    //        if (state < 8) {
    //            iv_frame.setBackgroundDrawable(getResources().getDrawable(pics[state]));
    //        }
    //    }
    //
    //    public void setRotateAniTime(int time) {
    //        if (time == mRotateAniTime || time == 0) {
    //            return;
    //        }
    //        mRotateAniTime = time;
    //        buildAnimation();
    //    }

    //    /**
    //     * Specify the last update time by this key string
    //     *
    //     * @param key
    //     */
    //    public void setLastUpdateTimeKey(String key) {
    //        if (TextUtils.isEmpty(key)) {
    //            return;
    //        }
    //        mLastUpdateTimeKey = key;
    //    }

    //    /**
    //     * Using an object to specify the last update time.
    //     *
    //     * @param object
    //     */
    //    public void setLastUpdateTimeRelateObject(Object object) {
    //        setLastUpdateTimeKey(object.getClass().getName());
    //    }

    //    private void buildAnimation() {
    //        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    //        mFlipAnimation.setInterpolator(new LinearInterpolator());
    //        mFlipAnimation.setDuration(mRotateAniTime);
    //        mFlipAnimation.setFillAfter(true);
    //
    //        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    //        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
    //        mReverseFlipAnimation.setDuration(mRotateAniTime);
    //        mReverseFlipAnimation.setFillAfter(true);
    //    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

        //        mShouldShowLastUpdate = true;
        //        tryUpdateLastUpdateTime();

    }

    public int getVisiableHeight() {
        return mHeaderViewContent.getHeight();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        //        mShouldShowLastUpdate = true;
        //        tryUpdateLastUpdateTime();
        //        mLastUpdateTimeUpdater.start();
        //        mTitleTextView.setVisibility(VISIBLE);
        //        if (frame.isPullToRefresh()) {
        //            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        //        } else {
        //            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down));
        //        }
    }

    /**
     * 停止播放
     */
    //    public void animstop() {
    //        if (frameAnim != null && frameAnim.isRunning()) {
    //            frameAnim.stop();
    //        }
    //    }
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        //        mShouldShowLastUpdate = false;
        //        hideRotateView();
        //        mTitleTextView.setVisibility(VISIBLE);
        //        mTitleTextView.setText(R.string.cube_ptr_refreshing);
        //        tryUpdateLastUpdateTime();
        //        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        //        animstop();
        //        hideRotateView();
        //        mTitleTextView.setVisibility(VISIBLE);
        //        mTitleTextView.setText(getResources().getString(R.string.cube_ptr_refresh_complete));
        //        // update last update time
        //        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        //        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
        //            mLastUpdateTime = new Date().getTime();
        //            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        //        }
    }

    //    private void tryUpdateLastUpdateTime() {
    //        if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
    //            // mLastUpdateTextView.setVisibility(GONE);
    //        } else {
    //            String time = getLastUpdateTime();
    //            if (TextUtils.isEmpty(time)) {
    //                // mLastUpdateTextView.setVisibility(GONE);
    //            } else {
    //                //  mLastUpdateTextView.setVisibility(VISIBLE);
    //                // mLastUpdateTextView.setText(time);
    //            }
    //        }
    //    }

    private String getLastUpdateTime() {

        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.cube_ptr_last_update));

        if (seconds < 60) {
            sb.append(seconds + getContext().getString(R.string.cube_ptr_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours + getContext().getString(R.string.cube_ptr_hours_ago));
                }

            } else {
                sb.append(minutes + getContext().getString(R.string.cube_ptr_minutes_ago));
            }
        }
        return sb.toString();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        //        int h = getVisiableHeight() / 8;//每个图片的拉伸距离
        //        if (currentPos >= getVisiableHeight()) {
        //            animstart();
        //        } else {
        //            setanimPos(currentPos / h);
        //        }
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                //                if (mRotateView != null) {
                //                    mRotateView.clearAnimation();
                //                    mRotateView.startAnimation(mReverseFlipAnimation);
                //                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                //                if (mRotateView != null) {
                //                    mRotateView.clearAnimation();
                //                    mRotateView.startAnimation(mFlipAnimation);
                //                }
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        //        if (!frame.isPullToRefresh()) {
        //            mTitleTextView.setVisibility(VISIBLE);
        //            mTitleTextView.setText(R.string.cube_ptr_release_to_refresh);
        //        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        //        mTitleTextView.setVisibility(VISIBLE);
        //        if (frame.isPullToRefresh()) {
        //            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        //        } else {
        //            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down));
        //        }
    }

    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            //   tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}
