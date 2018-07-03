package us.mifeng.banner.banner;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;
/**
 * Created by su on 2016/2/19.
 * 自定义轮播点数的布局
 */

/**
 * Created by howzhi on 14-8-8.
 */
public class ViewPagerBanner extends RelativeLayout {

    private static final String TAG = "EduSohoViewPager";
    private CarouselPointLayout mPointLayout;
    private Context             mContext;
    private HackyViewPager      mHackyViewPager;
    private SchoolBannerAdapter mAdapter;

    private int mLastIndex;
    private int mTopIndex;
    private int mCurrent;

    private Handler workHandler = new Handler();

    private Runnable mAutoPlayRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentItem(mCurrent + 1, true);
            workHandler.postDelayed(this, 3000);
        }
    };

    public ViewPagerBanner(Context context) {
        super(context);
        mContext = context;
    }

    public ViewPagerBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void update(List<Integer> schoolBanners) {
        mAdapter.setItems(schoolBanners);
        mAdapter.wrapContent();
        mAdapter.notifyDataSetChanged();
        mPointLayout.updatePointImages(schoolBanners.size());
    }

    public SchoolBannerAdapter getAdapter() {
        return mAdapter;
    }

    private void initView() {
        mHackyViewPager = new HackyViewPager(mContext);
        mPointLayout = new CarouselPointLayout(mContext);
        mPointLayout.setViewPaper(mHackyViewPager);

        addView(mHackyViewPager);
        addView(mPointLayout);

        LayoutParams layoutParams = (LayoutParams) mPointLayout.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 8));
        mPointLayout.setLayoutParams(layoutParams);
    }

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = (SchoolBannerAdapter) adapter;
        mAdapter.wrapContent();

        int count = adapter.getCount();
        mTopIndex = 1;
        mLastIndex = count - 2;

        mHackyViewPager.setAdapter(adapter);
        mHackyViewPager.setOffscreenPageLimit(count);

        mPointLayout.addPointImages(count);
        mHackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    moveToIndex(mLastIndex);
                } else if (position == mAdapter.getCount() - 1) {
                    moveToIndex(mTopIndex);
                }

                mPointLayout.refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setupAutoPlay() {
        workHandler.postDelayed(mAutoPlayRunnable, 3000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        workHandler.removeCallbacks(mAutoPlayRunnable);
    }

    private void moveToIndex(final int index) {
        workHandler.postAtTime(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(index, false);
            }
        }, SystemClock.uptimeMillis() + 500);
    }

    public void setCurrentItem(int index, boolean smoothScroll) {
        mCurrent = index;
        mHackyViewPager.setCurrentItem(index, smoothScroll);
    }

    public int getCurrentIndex() {
        return mCurrent;
    }

    //布局
    class CarouselPointLayout extends PointLayout {
        public CarouselPointLayout(Context context) {
            super(context);
            setPadding(mPadding, mPadding, mPadding, mPadding);
        }

        public void clear() {
            removeAllViews();
        }

        public void updatePointImages(int count) {
            clear();
            addPointImages(count);
        }

        @Override
        public void addPointImages(int count) {
            mCount = count;
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(mContext);
                if (i == 0 || i == count - 1) {
                    imageView.setVisibility(INVISIBLE);
                }
                addView(imageView);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.bottomMargin = mPadding;
                layoutParams.topMargin = mPadding;
                layoutParams.leftMargin = mPadding;
                layoutParams.rightMargin = mPadding;
                imageView.setLayoutParams(layoutParams);
            }
            refresh();
        }
    }
}
