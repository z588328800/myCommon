package priv.zl.mycommon.pager.main;

import android.app.Activity;
import android.widget.TextView;

import priv.zl.mycommon.activity.MainActivity;
import priv.zl.mycommon.fragment.LeftFragment;
import priv.zl.mycommon.pager.BasePager;


public class HomePager extends BasePager {
    public HomePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        //要给帧布局填充布局对象
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        flContent.addView(textView);
        tvTitle.setText("首页");
        LeftFragment leftFragment = (LeftFragment) ((MainActivity) mActivity).getLeftFragment();
    }
}
