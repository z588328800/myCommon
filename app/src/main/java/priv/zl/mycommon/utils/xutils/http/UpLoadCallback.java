package priv.zl.mycommon.utils.xutils.http;

import org.xutils.common.Callback;

import java.io.File;

public abstract class UpLoadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Cancelable {
}
