package us.mifeng.banaer;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Integer>mImgs = new ArrayList<>();
    private Handler mHandler = new Handler();
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findView();



    }

    private void findView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mImgs.add(R.mipmap.t0);
        mImgs.add(R.mipmap.t1);
        mImgs.add(R.mipmap.t2);

        mImgs.add(mImgs.size(),R.mipmap.t0);
        mImgs.add(0,R.mipmap.t2);
        MyAdapter adapter = new MyAdapter(mImgs,this);
        mViewPager.setAdapter(adapter);
        setUpAutoPlay();
        onPageListener();
    }

    private void onPageListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                Log.i("tag", "onPageSelected: ======"+position);
                if (position == 0){
                    position = mImgs.size()-2;
                }else if (position == mImgs.size()-1){
                    position = 1;
                    moveToIndexOrLast(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void moveToIndexOrLast(final int position) {
        mHandler.postAtTime(new Runnable() {
            @Override
            public void run() {
                current = position;
                mViewPager.setCurrentItem(current,false);
            }
        }, SystemClock.uptimeMillis() + 500);

    }

    public void setUpAutoPlay(){
        mHandler.postDelayed(bannderRunnable,2000);
    }
    private Runnable bannderRunnable = new Runnable() {
        @Override
        public void run() {
            current += 1;
            mViewPager.setCurrentItem(current);
            mHandler.postDelayed(this,2000);
        }
    };

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(bannderRunnable);
    }
}
