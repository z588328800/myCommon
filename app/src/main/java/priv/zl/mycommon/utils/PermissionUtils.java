package priv.zl.mycommon.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zhy.m.permission.MPermissions;

import priv.zl.mycommon.application.MyApplication;

public class PermissionUtils {

    /**
     * 判断是否缺少权限
     *
     * @param permission 单个权限 例如 ：Manifest.permission.WRITE_EXTERNAL_STORAGE
     * @return 如果缺少权限返回true
     */
    public static boolean lacksPermission(String permission) {
        System.out.println("判断是否缺少权限：" + permission);
        return ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) ==
                PackageManager.PERMISSION_DENIED;
    }


    /**
     * 先判断是拥有权限，如果没有就请求权限 单个
     *
     * @param activity
     * @param requestCode 请求码
     * @param permission  单个权限 例如 ：Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    public static void requestPermissions(Activity activity, int requestCode, String permission) {
        System.out.println("申请权限 " + permission);
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * 先判断是拥有权限，如果没有就请求权限 多个
     *
     * @param activity
     * @param requestCode 请求码
     * @param permission  多个权限 例如 ：Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    public static void requestPermissions(Activity activity, int requestCode, String[] permission) {
        System.out.println("申请权限 " + permission);
        ActivityCompat.requestPermissions(activity, permission, requestCode);
    }


    /**
     * 在Activity中写入回调函数：
     *
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1: { // 授权被允许
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("授权请求被允许");
//                } else {
//                    System.out.println("授权请求被拒绝");
//                }
//                return;
//            }
//        }
//    }


}
