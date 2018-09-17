package priv.zl.mycommon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class MyBitmapUtils {

    /**
     * 将gif载入到ImageView中
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void putGifToView(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    /**
     * 将gif载入到ImageView中
     *
     * @param context
     * @param drawable
     * @param imageView
     */
    public static void putGifToView(Context context, Drawable drawable, ImageView imageView) {
        Glide.with(context).load(drawable).into(imageView);
    }

    /**
     * 将gif载入到ImageView中
     *
     * @param context
     * @param bitmap
     * @param imageView
     */
    public static void putGifToView(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).into(imageView);
    }

    /**
     * 将图片载入到ImageView中，可以加载网络图片
     *
     * @param uri            图片地址
     * @param imageView      ImageView
     * @param dufaultImageId 默认图片的id
     * @param transformation 图片变化，不变化可以为null
     */
    public static void putPicToView(Uri uri, ImageView imageView, int dufaultImageId, Transformation transformation) {

        RequestCreator placeholder = Picasso.get().load(uri).placeholder(dufaultImageId);
        if (transformation != null) {
            placeholder.transform(transformation).into(imageView);
        } else {
            placeholder.into(imageView);
        }
    }


    /**
     * 将图片载入到ImageView中，可以加载网络图片
     *
     * @param uri
     * @param imageView
     * @param transformation 图片变化，不变化可以为null
     */
    public static void putPicToView(Uri uri, ImageView imageView, Transformation transformation) {

        RequestCreator placeholder = Picasso.get().load(uri);
        if (transformation != null) {
            placeholder.transform(transformation).into(imageView);
        } else {
            placeholder.into(imageView);
        }
    }


}
