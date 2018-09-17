package priv.zl.mycommon.pager.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import priv.zl.mycommon.AppNetConfig;
import priv.zl.mycommon.R;
import priv.zl.mycommon.activity.MainActivity;
import priv.zl.mycommon.domain.NewsMenu;
import priv.zl.mycommon.pager.BasePager;
import priv.zl.mycommon.pager.TabDetailPager;
import priv.zl.mycommon.utils.CacheUtils;

public class NewsPager extends BasePager {
    ArrayList<NewsMenu.NewsTabData> mTabDatas;  //页签网络数据
    private List<TabDetailPager> mPagers;  //页签页面集合
    ViewPager vp;
    TabPageIndicator indicator;
    ImageButton btn_next;
    AsyncHttpClient asyncHttpClient;
    NewsMenu newsMenu;

    public NewsPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");
        btnMenu.setVisibility(View.VISIBLE);

        //给flContent添加子控件
        View view = View.inflate(mActivity, R.layout.view_newspager, null);
        indicator = view.findViewById(R.id.indicator);
        vp = view.findViewById(R.id.vp_news_menu_detail);
        btn_next = view.findViewById(R.id.btn_next);

        flContent.addView(view);


        getDataFromServer();
    }


    //获取网络数据
    private void getDataFromServer() {
        asyncHttpClient = new AsyncHttpClient(); //在此初始化AsyncHttpClient，不能在类体重初始化，因为子类继承父类，先执行父类的构造函数，再初始化子类的成员变量
        String cache = CacheUtils.getCache(AppNetConfig.GetJsonNewsCenter1);
        if (cache != null) {
            System.out.println("获取到缓存的内容：" + cache);
            processData(cache);
        }

        asyncHttpClient.get(AppNetConfig.GetJsonNewsCenter1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                System.out.println("恢复---------------------------" + content);
                CacheUtils.setCache(AppNetConfig.GetJsonNewsCenter1, content);
                processData(content);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                System.out.println("获取失败" + content);
            }
        });

    }


    //处理网络数据
    private void processData(String response) {

        //1 解析数据
        Gson gson = new Gson();
        newsMenu = gson.fromJson(response, NewsMenu.class); //获取的信息转换成bean
        mTabDatas = newsMenu.getData().get(0).getChildren();

        //2 创建viewPager的页面
        mPagers = new ArrayList<>();
        for (int i = 0; i < mTabDatas.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity, mTabDatas.get(i));
            mPagers.add(tabDetailPager);
        }

        //3 给viewPager设置驱动
        vp.setAdapter(new MyPagerAdapter());
        indicator.setViewPager(vp);


        //4 设置监听
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagers.get(position).setCurrentVptopPosition(0); //当页面被选中时，里面的图片跳转到第一张图片的位置
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vp.getCurrentItem(); //获取当前itemm
                vp.setCurrentItem(currentItem + 1); //跳转下一个页面
            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabDatas.get(position).getTitle().toString();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            TabDetailPager pager = mPagers.get(position);
            pager.initData();
            View view = pager.mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
