package priv.zl.mycommon.utils.xutils.http;


import com.google.gson.Gson;

import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;

/**
 * @param <T>
 */
public abstract class GetJsonToBeanCallback<T> implements Callback.CommonCallback<String> {


    /**
     * 返回类
     * @param result
     */
    public abstract void onSucesss(T result);

    /**
     * 返回string类型的json数据 ，不要重写这个方法
     * @param result
     */
    @Deprecated
    @Override
    public void onSuccess(String result) {
        //将Json转换成类
        Gson gson = new Gson();
        T o = gson.fromJson(result, (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        onSucesss(o);
    }


    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
