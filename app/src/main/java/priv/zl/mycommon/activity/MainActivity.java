package priv.zl.mycommon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import priv.zl.mycommon.R;
import priv.zl.mycommon.fragment.LeftFragment;
import priv.zl.mycommon.fragment.MainFragment;
import priv.zl.mycommon.utils.UIUtils;


public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.activity_left_menu);

        SlidingMenu slidingMenu = getSlidingMenu();

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        slidingMenu.setBehindOffset(UIUtils.getDisplayWidthPx() * 2 / 3);

        initFragment();


    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fm_left, new LeftFragment(), "left");
        fragmentTransaction.replace(R.id.fm_main, new MainFragment(), "main");

        fragmentTransaction.commit();


    }

    /**
     * 获取侧边栏Fragment对象，从而可以让Activity和Fragment联系起来
     *
     * @return Fragment
     */
    public Fragment getLeftFragment() {
        return getSupportFragmentManager().findFragmentByTag("left");
    }

    /**
     * MainFragment
     *
     * @return Fragment
     */
    public Fragment getMainFragment() {
        return getSupportFragmentManager().findFragmentByTag("main");
    }


}
