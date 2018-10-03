package priv.zl.mycommon.utils.xutils.http;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by wyouflf on 15/11/10.
 */
/*package*/ public abstract class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Cancelable {
    @Override
    public void cancel() {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onWaiting() {

    }
}
