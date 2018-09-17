package priv.zl.mycommon.utils;

import android.content.SharedPreferences;

import priv.zl.mycommon.application.MyApplication;


/**
 * SharePreference封装
 */

public class PrefUtils {

    /**
     * 获取初始化配置Boolean
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


    /**
     * 设置初始化配置Boolean
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setBoolean(String key, boolean value) {

        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.edit().putBoolean(key, value).commit();
    }


    /**
     * 获取初始化配置Boolean
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


    /**
     * 设置初始化配置Boolean
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(String key, String value) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.edit().putString(key, value).commit();
    }


    /**
     * 获取初始化配置int
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }


    /**
     * 设置初始化配置int
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(String key, int value) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE);
        return sp.edit().putInt(key, value).commit();
    }


}
