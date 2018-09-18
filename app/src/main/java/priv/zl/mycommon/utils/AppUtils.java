package priv.zl.mycommon.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import priv.zl.mycommon.application.MyApplication;

public class AppUtils {


    /**
     * 获取app的版本号，获取不到返回0
     * 版本号是build.gradle中的versionCode变量
     *
     * @return 版本号
     */
    public static int getAppVersionCode() {
        PackageManager packageManager = MyApplication.getContext().getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取app的版本名称，获取不到返回null
     * 版本号是build.gradle中的versionName变量
     *
     * @return 版本名称
     */
    public static String getAppVersionName() {
        PackageManager packageManager = MyApplication.getContext().getPackageManager();
        String versionCode = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            versionCode = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
