package priv.zl.mycommon.utils;


public class CacheUtils {

    /**
     * 设置缓存
     *
     * @param url
     * @param json
     */
    public static void setCache(String url, String json) {
        PrefUtils.setString(url, json);

    }


    /**
     * 获取缓存
     *
     * @param url
     * @return 没有则返回null
     */
    public static String getCache(String url) {
        return PrefUtils.getString(url, null);
    }
}
