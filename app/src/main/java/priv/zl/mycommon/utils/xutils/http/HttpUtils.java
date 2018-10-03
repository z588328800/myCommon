package priv.zl.mycommon.utils.xutils.http;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;


public class HttpUtils {
    /**
     * 下载文件
     *
     * @param path              文件路径，例如： Environment.getExternalStorageDirectory()+"/mytest.apk"
     * @param url               下载地址，例如： http://10.0.2.2:8080/zhihuibeijing/huahuaban.apk
     * @param downloadCallback  回调函数
     * @param isAutoResume      是否断点续传
     * @param maxDownloadThread 最大线程数，建议填写2
     */
    public static Callback.Cancelable downLoad(String path, String url, DownloadCallback downloadCallback, boolean isAutoResume, int maxDownloadThread) {
        //设置最大线程数
        Executor executor = new PriorityExecutor(maxDownloadThread, true);

        //设置下载的参数
        RequestParams params = new RequestParams(url);
        params.setAutoResume(isAutoResume);
        params.setSaveFilePath(path);
        params.setExecutor(executor);
        params.setCancelFast(true);

        //开始下载
        Callback.Cancelable cancelable = x.http().get(params, downloadCallback);
        return cancelable;

    }


    /**
     * 上传文件
     *
     * @param url            上传的地址 例如 ：http://10.0.2.2:8080/zhihuibeijing
     * @param files          上传的文件,支持多个文件上传，把文件添加到Map中即可，key为请求内容的name  例如 ：File file=new File("/sdcard/test.jpg") ，然后把这个file添加到Map中
     * @param parameter
     * @param upLoadCallback 回调函数
     */
    public static Callback.Cancelable upLoad(String url, HashMap<String, File> files, HashMap<String, String> parameter, UpLoadCallback upLoadCallback) {

        //设置url
        RequestParams params = new RequestParams(url);

        //设置传递的域
        if (parameter != null) {
            Iterator<Map.Entry<String, String>> iterator1 = parameter.entrySet().iterator();
            while (iterator1.hasNext()) {
                Map.Entry entry = iterator1.next();
                params.addQueryStringParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        // 使用multipart表单上传文件（一种上传文件的方式）
        params.setMultipart(true);

        if (files != null) {
            // 以下为添加请求内容, 只有POST, PUT, PATCH, DELETE请求支持.
            //设置传递的文件
            Iterator<Map.Entry<String, File>> iterator2 = files.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry entry = iterator2.next();
                params.addBodyParameter((String) entry.getKey(), (File) entry.getValue());
            }
        }

        //开始上传
        Callback.Cancelable cancelable = x.http().post(params, upLoadCallback);

        return cancelable;
    }


    /**
     * 获取网络数据
     *
     * @param url            地址
     * @param commonCallback 回调
     * @param timeOut        超时时间
     * @return
     */
    public static Callback.Cancelable getString(String url, Callback.CommonCallback commonCallback, int timeOut) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(timeOut);
        params.setReadTimeout(2000);
        return x.http().get(params, commonCallback);
    }

    /**
     * 网络获取json并转换成bean
     *
     * @param url                   地址
     * @param getJsonToBeanCallback 回调
     * @param timeOut               超时时间
     * @return
     */
    public static Callback.Cancelable getJsonToBean(String url, GetJsonToBeanCallback getJsonToBeanCallback, int timeOut) {
        return getString(url, getJsonToBeanCallback, timeOut);
    }



}
