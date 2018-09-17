package priv.zl.mycommon.pager;

import android.app.Activity;
import android.view.View;

public abstract class BaseNewsPagerPager {

    public Activity mActivity;
    public View mRootView;

    public BaseNewsPagerPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {
    }


}
