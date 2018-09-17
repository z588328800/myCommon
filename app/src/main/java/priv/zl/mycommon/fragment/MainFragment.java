package priv.zl.mycommon.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import priv.zl.mycommon.R;
import priv.zl.mycommon.activity.MainActivity;
import priv.zl.mycommon.pager.BasePager;
import priv.zl.mycommon.pager.main.GovAffairsPager;
import priv.zl.mycommon.pager.main.HomePager;
import priv.zl.mycommon.pager.main.NewsPager;
import priv.zl.mycommon.pager.main.SettingPager;
import priv.zl.mycommon.pager.main.SmartServicePager;
import priv.zl.mycommon.view.NoScrollViewPager;

public class MainFragment extends BaseFragment {
    NoScrollViewPager vpOne;
    RadioGroup rgBottom;
    List<BasePager> pagers = new ArrayList<>();

    @Override
    protected void initView(View rootView) {
        vpOne = rootView.findViewById(R.id.vp_one);
        rgBottom = rootView.findViewById(R.id.rg_bottom);

    }

    @Override
    public int theFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initData() {
        pagers.add(new HomePager(mActivity)); //使用viewPager对页面进行切换
        pagers.add(new NewsPager(mActivity));
        pagers.add(new SmartServicePager(mActivity));
        pagers.add(new GovAffairsPager(mActivity));
        pagers.add(new SettingPager(mActivity));

        vpOne.setAdapter(new MyAdapter());
        pagers.get(0).initData();

        //以下为设置viewPager监听，当切换到指定页目才会初始化这个页的数据 ，每次切换都会初始化数据
        //因为所加载的数据都会进入缓存，所以此处不必考虑每次初始化消耗网络资源的问题
        vpOne.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) { // 页面展现时对数据进行刷新,而且每次展示都会重新刷新
                pagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpOne.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        vpOne.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        vpOne.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        vpOne.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        vpOne.setCurrentItem(4, false);
                        break;
                }
            }
        });

    }

    /**
     * 开启或禁用侧边栏
     *
     * @param enable
     */
    protected void setSlidingMenuEnable(boolean enable) {
        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            View view = pager.mRootView;
            container.addView(pager.mRootView);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


}
