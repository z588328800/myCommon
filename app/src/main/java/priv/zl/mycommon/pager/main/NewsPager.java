package priv.zl.mycommon.pager.main;

import android.app.Activity;
import android.widget.TextView;


import priv.zl.mycommon.pager.BasePager;

public class NewsPager extends BasePager {
    public NewsPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        //要给帧布局填充布局对象
        TextView textView = new TextView(mActivity);
        textView.setText("新闻");
        tvTitle.setText("新闻");
        flContent.addView(textView);
    }


}
