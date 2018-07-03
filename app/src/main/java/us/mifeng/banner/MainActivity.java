package us.mifeng.banner;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import us.mifeng.banner.banner.SchoolBannerAdapter;
import us.mifeng.banner.banner.ViewPagerBanner;
import us.mifeng.banner.bean.UserBean;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer>mImgs = new ArrayList<>();
    private String result;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StringBuffer sbs = new StringBuffer();
        AssetManager manater = getAssets();
        try {
            InputStream json = manater.open("json");
            byte[]by = new byte[2048*4];
            int len = 0;
            while ((len = json.read(by))!=-1){
                sbs.append(new String(by,0,len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        result = sbs.toString();
        //findView();

        //Log.i("tag", "onCreate: =========="+result);
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(result, UserBean.class);
        int code = userBean.getCode();
        Log.i("tag", "onCreate: ===ss======="+result);

    }

    private void findView() {
        ViewPagerBanner banner = (ViewPagerBanner) findViewById(R.id.bannerViewPager);

        mImgs.add(R.mipmap.t0);
        mImgs.add(R.mipmap.t1);
        mImgs.add(R.mipmap.t2);
        mImgs.add(R.mipmap.t3);
        mImgs.add(R.mipmap.t4);
        SchoolBannerAdapter adapter = new SchoolBannerAdapter(this,mImgs);
        banner.setAdapter(adapter);
        banner.setCurrentItem(1,false);
        //banner.update(mImgs);
        banner.setupAutoPlay();
    }
}
