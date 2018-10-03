package priv.zl.mycommon.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.xutils.x;


public class MyApplication extends Application {
    public static Context context = null;
    /**
     * 主线程handler
     */
    public static Handler handler = null;
    /**
     * 主线程
     */
    public static Thread mainThread = null;
    /**
     * 主线程ID
     */
    public static int mainThreadId = 0;


    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取Handler
     *
     * @return
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public static Thread getMainThread() {
        return mainThread;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler();
        context = getApplicationContext();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid(); //返回当前线程ID，因为MyApplication运行在主线程中，所以返回的是主线程ID

        // CrashHandler.getInstance().init(this); //启动全局异常处理类

        x.Ext.init(this);
    }
}
