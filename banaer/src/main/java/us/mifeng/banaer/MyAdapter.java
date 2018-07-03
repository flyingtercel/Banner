package us.mifeng.banaer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 黑夜之火 on 2018/6/24.
 */

public class MyAdapter extends PagerAdapter {

    ArrayList<Integer>mImgs;
    private Context context;

    public MyAdapter(ArrayList<Integer> mImgs, Context context) {
        this.mImgs = mImgs;
        this.context = context;
    }

    @Override
    public int getCount() {
            return mImgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(mImgs.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
