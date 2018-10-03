package priv.zl.mycommon.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;

import priv.zl.mycommon.AppNetConfig;
import priv.zl.mycommon.R;
import priv.zl.mycommon.domain.VersionMessage;
import priv.zl.mycommon.utils.AppUtils;
import priv.zl.mycommon.utils.MyTextUtils;
import priv.zl.mycommon.utils.PermissionUtils;
import priv.zl.mycommon.utils.PrefUtils;
import priv.zl.mycommon.utils.UIUtils;
import priv.zl.mycommon.utils.xutils.http.DownloadCallback;
import priv.zl.mycommon.utils.xutils.http.GetJsonToBeanCallback;
import priv.zl.mycommon.utils.xutils.http.HttpUtils;

public class SplashActivity extends AppCompatActivity {

    ImageView ivSplash;
    RelativeLayout llSplash;
    TextView tvVersionCode;


    public static final String 隐私声明内容 = "电饭锅电饭锅第三方给对方色调分公《隐私声明》司电饭锅电饭锅第三方给对方色调分公司电饭锅电饭锅第三方给对方色调分公司电饭锅电饭锅第三方给对方色调";
    public static final String 链接内容 = "《隐私声明》";
    public static final String 链接url = "https://www.baidu.com/";

    public static final String APP_VERSION = AppNetConfig.BASEURL + "/GetJsonNewsCenter1?json_path=version.json";

    AlertDialog ifUpdateDialog;

    DownloadCallback downloadCallback = null;
    public static final int REQUESTCODE_SETUP = 8;
    private Callback.Cancelable cancelable;
    private Button btnUpdate;
    private Button btnNoUpdate;
    private ProgressBar pbLoading;
    private String downPath;
    /**
     * SD卡读写权限请求码
     */
    private static final int REQUESTCODE_WRITE_EXTERNAL_STORAGE = 1;
    private VersionMessage versionMessage;
    private AlertDialog ifAgreedPrivateDecaleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();


        HttpUtils.getJsonToBean(APP_VERSION, new GetJsonToBeanCallback<VersionMessage>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("失败" + ex.getMessage());
                //如果已经同意隐私条款
                showDialogIfAgreedPrivateDecale();
            }

            @Override
            public void onSucesss(VersionMessage result) {

                /**********测试**********/
                DbManager.DaoConfig dbVersionmessage = new DbManager.DaoConfig().setDbName("versionmessage").setDbVersion(3).setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        System.out.println("数据库创建好了");
                    }
                }).setDbUpgradeListener(null);
                DbManager db = x.getDb(dbVersionmessage);

                System.out.println("开始添加数据");


                /***********************/


                versionMessage = result;
                if (AppUtils.getAppVersionCode() < versionMessage.versionCode) { //如果此app版本小于服务器版本
                    //更新
                    System.out.println("更新" + versionMessage.toString());
                    showDialogIfUpdate(versionMessage);


                } else {
                    System.out.println("不需要更新");
                    showDialogIfAgreedPrivateDecale();
                }
            }
        }, 2000);

    }

    private void initView() {
        //初始化页面
        ivSplash = findViewById(R.id.iv_splash);
        llSplash = findViewById(R.id.ll_splash);
        tvVersionCode = findViewById(R.id.tv_versionCode);
        tvVersionCode.setText(AppUtils.getAppVersionName());
        Glide.with(this).load(R.drawable.giftest).into(ivSplash);
    }


    /**
     * 显示dialog选择是否需要更新
     *
     * @param versionMessage
     */
    private void showDialogIfUpdate(final VersionMessage versionMessage) {
        View view = View.inflate(this, R.layout.dialog_splash_ifupdate, null);
        TextView tvVersionDes = view.findViewById(R.id.tv_versionDes);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnNoUpdate = view.findViewById(R.id.btn_noupdate);
        pbLoading = view.findViewById(R.id.pb_loading);
        pbLoading.setMax(100);

        tvVersionDes.setText(versionMessage.versionDes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ifUpdateDialog = builder.setView(view).show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同意，关闭此窗口，一秒后进入主界面
                System.out.println("点击了更新按钮");

                //判断是否拥有SD卡权限，android7.0以后，安装apk默认是没有这个权限的，需要提示开启
                if (!PermissionUtils.lacksPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    //有权限
                    System.out.println("有权限");
                    beginDown(versionMessage);

                } else {
                    //没有权限 申请
                    System.out.println("没有权限");
                    //弹窗请求权限
                    PermissionUtils.requestPermissions(SplashActivity.this, REQUESTCODE_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }


            }
        });

        btnNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忽略更新，直接进入
                System.out.println("忽略更新");
                ifUpdateDialog.dismiss();
                showDialogIfAgreedPrivateDecale();
            }
        });

        //设置返回键内容
        ifUpdateDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });
    }


    /**
     * 开始下载APP
     *
     * @param versionMessage
     */
    private void beginDown(final VersionMessage versionMessage) {
        //开始下载
        downPath = Environment.getExternalStorageDirectory() + File.separator + versionMessage.versionName + ".apk";

        System.out.println("文件路径：" + downPath + " 下载地址：" + versionMessage.downloadUrl);

        cancelable = HttpUtils.downLoad(downPath, versionMessage.downloadUrl, downloadCallback = new DownloadCallback() {


            @Override
            public void onStarted() {
                System.out.println("已经开始下载了");
                btnUpdate.setClickable(true);
                btnUpdate.setText("暂停");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pbLoading.setProgress((int) (100 * current / total));

            }

            @Override
            public void onSuccess(File result) {
                System.out.println("下载成功");
                installApk(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("已经暂停了");
                btnUpdate.setClickable(true);
                btnUpdate.setText("继续下载");
            }


        }, true, 2);

        //开始下载后改变按钮的功能
        pbLoading.setVisibility(View.VISIBLE);
        btnNoUpdate.setText("取消下载");
        btnNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("停止下载");
                cancelable.cancel();
                ifUpdateDialog.dismiss();
                showDialogIfAgreedPrivateDecale();
            }

        });

        btnUpdate.setText("暂停");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            //按钮：暂停--继续 变化
            @Override
            public void onClick(View v) {
                if (btnUpdate.getText().toString().equals("暂停")) {

                    System.out.println("点击暂停下载");
                    cancelable.cancel();
                    btnUpdate.setClickable(false);


                } else {
                    System.out.println("点击继续下载");
                    cancelable = HttpUtils.downLoad(downPath, versionMessage.downloadUrl, downloadCallback, true, 2);
                    btnUpdate.setClickable(false);

                }
            }
        });
    }


    /**
     * 弹出隐私声明对话框,
     */

    private void showDialogIfAgreedPrivateDecale() {
        //如果已经同意隐私条款
        if (!PrefUtils.getBoolean("ifAgreedPrivacyStatement", true)) {
            gotoNextActivity();
            return;
        }
        View view = View.inflate(this, R.layout.dialog_splash_ifagreedprivatedecale, null);
        //修改这里
        TextView tvDialogContent = view.findViewById(R.id.tv_dialog_content);
        Button btnNoagreed = view.findViewById(R.id.btn_noagreed);
        Button btnAgreed = view.findViewById(R.id.btn_agreed);


        //设置内容链接
        SpannableString spanStr = MyTextUtils.getSpan(隐私声明内容, 链接内容, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //点击后跳转网页
                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                intent.putExtra("url", 链接url);
                intent.putExtra("ifShareVisiable", false);
                intent.putExtra("ifTextSizeVisiable", false);
                SplashActivity.this.startActivity(intent);

            }
        });


        tvDialogContent.setText(spanStr);
        tvDialogContent.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        tvDialogContent.setHighlightColor(UIUtils.getColor(R.color.Transparency)); //设置点击后的颜色变成透明


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ifAgreedPrivateDecaleDialog = builder.setView(view).show();
        btnAgreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同意，关闭此窗口，一秒后进入主界面 
                System.out.println("同意了");
                PrefUtils.setBoolean("ifAgreedPrivacyStatement", false);
                ifAgreedPrivateDecaleDialog.cancel();

                gotoNextActivity();


            }
        });

        btnNoagreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不同意，弹出提示，不关闭页面
                System.out.println("不同意了");
                Toast.makeText(SplashActivity.this, "需要您同意才能继续使用本软件。", Toast.LENGTH_SHORT).show();
            }
        });

        ifAgreedPrivateDecaleDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });
    }


    /**
     * 跳转下一个activity，这个方法只能由showDialogIfAgreedPrivateDecale方法调用
     */
    private void gotoNextActivity() {
        //页面动画设置
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.5f);
        alphaAnimation.setDuration(2000);
        llSplash.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //判断是否第一次进入app
                if (PrefUtils.getBoolean("isFistEnterApp", true)) {
                    PrefUtils.setBoolean("isFistEnterApp", false);
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));

                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 安装apk
     * 注意：在AndroidAPI 23以后, 部分权限要动态获取. 也就是说在下载前需要动态获取文件的读写权限 ，所以需要判断手机的版本来安装apk
     * 7.0安装apk方法： https://blog.csdn.net/qq_27512671/article/details/70224978
     *
     * @param file
     */
    protected void installApk(File file) {

        //判读版本是否在7.0或7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(this, "priv.zl.mycommon.fileprovider", file);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else {
            //7.0以前的启动方法
            //系统应用界面,源码,安装apk入口
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

            startActivityForResult(intent, REQUESTCODE_SETUP);
        }


    }

    //开启一个activity后,返回结果调用的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case REQUESTCODE_SETUP:  //安装请求返回
                System.out.println("安装结束");
                ifUpdateDialog.dismiss();
                showDialogIfAgreedPrivateDecale();
                return;
        }
    }


    //处理权限申请回调(写在Activity中)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //如果授权被允许
                    requestSdcardSuccess();
                } else {
                    requestSdcardFailed();
                }

                return;
        }

    }

    /**
     * 授权请求被允许
     */
    public void requestSdcardSuccess() {
        System.out.println("授权请求被允许");
        beginDown(versionMessage);
    }

    /**
     * 授权请求被拒绝
     */
    public void requestSdcardFailed() {
        System.out.println("授权请求被拒绝");
        ifUpdateDialog.dismiss();
        showDialogIfAgreedPrivateDecale();
    }
}
