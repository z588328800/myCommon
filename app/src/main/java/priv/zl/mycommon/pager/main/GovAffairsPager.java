package priv.zl.mycommon.pager.main;

import android.app.Activity;
import android.widget.TextView;

import priv.zl.mycommon.pager.BasePager;


public class GovAffairsPager extends BasePager {
    public GovAffairsPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        //要给帧布局填充布局对象
        TextView textView = new TextView(mActivity);
        textView.setText("政务");
        tvTitle.setText("政务");
        flContent.addView(textView);
    }
}
