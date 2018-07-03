package us.mifeng.banner.banner;

/**
 * Created by su on 2016/2/19.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
/**
 * Created by howzhi on 14-8-10.
 */
public class SchoolBannerAdapter extends PagerAdapter {

    private Context            mContext;
    private List<Integer> mSchoolBanners;

    public SchoolBannerAdapter(Context context, List<Integer> schoolBanners) {
        mContext = context;
        mSchoolBanners = schoolBanners;
    }

    @Override
    public int getCount() {
        Log.i("tag", "setItems: =======2====="+mSchoolBanners.size());
        if (mSchoolBanners != null) {
            return mSchoolBanners.size();
        } else {
            return 0;
        }
    }

    public void setItems(List<Integer> schoolBanners) {
        this.mSchoolBanners.clear();
        this.mSchoolBanners.addAll(schoolBanners);
        notifyDataSetChanged();
    }

    public void wrapContent() {
        if (mSchoolBanners != null && mSchoolBanners.size() > 0) {
            Integer top = mSchoolBanners.get(0);
            Integer last = mSchoolBanners.get(mSchoolBanners.size() - 1);
            mSchoolBanners.add(mSchoolBanners.size(), top);
            mSchoolBanners.add(0, last);
        }
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        Integer banner = mSchoolBanners.get(position);
        ImageView photoView = new ImageView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Log.i("tag", "setAdapter: ======ssss===========");
       /* if ("localRes".equals(banner.url)) {
            //photoView.setImageBitmap(cacheBitmap);
        } else {
            ImageLoader.getInstance().displayImage(mSchoolBanners.get(position).url, photoView);
        }

        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       *//*发现页 搜索教室或者按钮的按钮*//*
       photoViewClick();*/
        photoView.setImageResource(mSchoolBanners.get(position));
        photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    private void photoViewClick() {
        /* photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final SchoolBanner banner = mSchoolBanners.get(position);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("index", String.format("第%d张轮播图", position));
                    map.put("type", banner.action);
                    MobclickAgent.onEvent(mContext, "find_topPoster", map);
                    if ("webview".equals(banner.action)) {
                        Pattern CLASSROOM_PAT = Pattern.compile("/classroom/(\\d+)", Pattern.DOTALL);
                        Matcher matcher = CLASSROOM_PAT.matcher(banner.params);
                        if (matcher.find()) {
                            if (matcher.groupCount() >= 1) {
                                try {
                                    final int classroomId = Integer.parseInt(matcher.group(1));

                                    *//*如果搜索的是教室 根据Activity的版本来决定要跳转的教室的activity*//*

                                    CoreEngine.create(mContext).runNormalPlugin((AppUtil.isX3Version() ? "X3" : "") + "ClassroomActivity", mContext, new PluginRunCallback() {
                                        @Override
                                        public void setIntentDate(Intent startIntent) {
                                            startIntent.putExtra(Const.CLASSROOM_ID, classroomId);
                                        }
                                    });
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else {
                            //主要是通过代码进行调用已有或者未有的浏览器进行打开相应的网页进行浏览
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(completeWithHttp(banner.params));
                            intent.setData(content_url);
                            mContext.startActivity(intent);
                        }

                        *//**
         * 如果搜索的是课程，同样是根据版本，如果是x3进入到X3CourseActivity.java中，
         * 如果不是x3版本，则进入到CourseProjectActivity.java中
         * *//*
                    } else if ("course".equals(banner.action)) {
                        int courseId = Integer.parseInt(banner.params);
                        if (AppUtil.isX3Version()) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(Const.COURSE_ID, courseId);
                            CoreEngine.create(mContext).runNormalPluginWithBundle("X3CourseActivity", mContext, bundle);
                        } else {
                            *//**否则启动CourseProjectActivity.java*//*
                            CourseProjectActivity.launch(mContext, courseId);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });*/
    }

    private String completeWithHttp(String url) {
        if (url.startsWith("http://")) {
            return url;
        }
        return "http://" + url;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}