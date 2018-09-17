package priv.zl.mycommon.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //事件拦截 onInterceptTouchEvent方法在触摸时是从父控件到子控件的方向传递，如果返回true，表示事件到这里就拦截住了，这时onTouchEvent事件就从这个控件开始往外传播，不会进入子控件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false; //设置为false表示不拦截子控件的事件
    }

    //重写触摸方法，在滑动时什么也不做，返回true表示事件继续往外传播
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
