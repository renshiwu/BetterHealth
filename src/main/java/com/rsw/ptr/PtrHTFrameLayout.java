package com.rsw.ptr;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Gallery;

/**
 * Created by RSW on 2017/10/23.
 * com.bulaitesi.bdhr.ptr
 */

public class PtrHTFrameLayout extends PtrFrameLayout {// 解决嵌套viewpager出现滑动冲突的问题
    private PtrClassicDefaultHeader mPtrClassicHeader;
    private float                   startY;
    private float                   startX;
    // 记录viewPager是否拖拽的标记
    private boolean                 mIsHorizontalMove;
    // 记录事件是否已被分发
    private boolean                 isDeal;
    private Gallery                 MyGallery;
    private int                     mTouchSlop;

    public PtrHTFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrHTFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrHTFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }


    public void setGallery(Gallery mallery) {
        this.MyGallery = mallery;
        if (MyGallery == null) {
            throw new IllegalArgumentException("MyGallery can not be null");
        }
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        mTouchSlop = ViewConfigurationCompat
                .getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MyGallery == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsHorizontalMove = false;
                isDeal = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果已经判断出是否由横向还是纵向处理，则跳出
                if (isDeal) {
                    break;
                }
                /** 拦截禁止交给Ptr的 dispatchTouchEvent处理 **/
                mIsHorizontalMove = true;
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX != distanceY) {
                    // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                    if (distanceX > distanceY) {
                        mIsHorizontalMove = true;
                        isDeal = true;
                    } else {
                        mIsHorizontalMove = false;
                        isDeal = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 下拉刷新状态时如果滚动了viewpager 此时mIsHorizontalMove为true
                // 会导致PtrFrameLayout无法恢复原位
                // 初始化标记,
                mIsHorizontalMove = false;
                isDeal = false;
                break;
        }
        if (mIsHorizontalMove) {
            return dispatchTouchEventSupper(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
