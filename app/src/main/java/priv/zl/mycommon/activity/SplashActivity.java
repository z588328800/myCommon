package priv.zl.mycommon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import priv.zl.mycommon.R;
import priv.zl.mycommon.utils.MyTextUtils;
import priv.zl.mycommon.utils.PrefUtils;
import priv.zl.mycommon.utils.UIUtils;


public class SplashActivity extends Activity {

    ImageView ivSplash;
    LinearLayout llSplash;


    TextView tvDialogContent;
    Button btnNoagreed;
    Button btnAgreed;


    public static final String 隐私声明内容 = "电饭锅电饭锅第三方给对方色调分公《隐私声明》司电饭锅电饭锅第三方给对方色调分公司电饭锅电饭锅第三方给对方色调分公司电饭锅电饭锅第三方给对方色调";
    public static final String 链接内容 = "《隐私声明》";
    public static final String 链接url = "https://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化页面
        ivSplash = findViewById(R.id.iv_splash);
        llSplash = findViewById(R.id.ll_splash);
        Glide.with(this).load(R.drawable.giftest).into(ivSplash);


        //如果已经同意隐私条款
        if (PrefUtils.getBoolean("ifAgreedPrivacyStatement", true)) {
            showDialog();
        } else {
            gotoNextActivity();
        }

    }

    /**
     * 弹出隐私声明对话框
     */

    private void showDialog() {
        View view = View.inflate(this, R.layout.dialog_splash, null);
        tvDialogContent = view.findViewById(R.id.tv_dialog_content);
        btnNoagreed = view.findViewById(R.id.btn_noagreed);
        btnAgreed = view.findViewById(R.id.btn_agreed);


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
        final AlertDialog dialog = builder.setView(view).show();
        btnAgreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同意，关闭此窗口，一秒后进入主界面
                System.out.println("同意了");
                PrefUtils.setBoolean("ifAgreedPrivacyStatement", false);
                dialog.cancel();

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

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
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
}
