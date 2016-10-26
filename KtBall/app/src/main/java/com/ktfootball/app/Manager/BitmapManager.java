package com.ktfootball.app.Manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kt.ktball.App;
import com.ktfootball.app.Manager.bitmap.GlideHelper;
import com.ktfootball.app.Manager.bitmap.MyBitmapImageViewTarget;
import com.ktfootball.app.Manager.bitmap.PlaceHolder;
import com.ktfootball.app.R;

import java.io.File;

/**
 * 位图管理器
 */
public class BitmapManager {
    /**
     * 加载图片的线程数量
     */
    private final int BITMAP_THREADPOOL_SIZE = 5;
    /**
     * 占内存百分比
     */
    private final float memoryCachePercent = 0.5f;
    /**
     * 最大缓存占用量
     */
    private final int diskCacheSize = 1024 * 1024 * 1000;

    private static BitmapManager instance = new BitmapManager();

    private BitmapManager() {
    }

    public static BitmapManager getInstance() {
        return instance;
    }


    /**
     * 加载不含配置的图片
     *
     * @param container
     * @param uri
     */
    public void display(ImageView container, String uri) {
        GlideHelper.display(App.getContext(), container, uri);
    }

    public void displayWaterFallPic(ImageView container, String uri) {
        GlideHelper.display(App.getContext(), container, uri, new MyBitmapImageViewTarget(container));
    }

    public void displayWaterFallPic(Activity activity, ImageView container, String uri) {
        GlideHelper.display(activity, container, uri, new MyBitmapImageViewTarget(container));
    }

    public void displayUserLogo(ImageView container, String uri) {
        GlideHelper.display(App.getContext(), container, uri, PlaceHolder.placeHolder_userlogo);
    }

    public void displayKTItem(ImageView container, String uri) {
        GlideHelper.display(App.getContext(), container, uri, PlaceHolder.placeHolder_item);
    }


    public void display(ImageView container, String uri, final ImageLoadState ls) {
        GlideHelper.display(App.getContext(), container, uri, new BitmapImageViewTarget(container) {
            @Override
            public void onStop() {
                ls.onStop();
            }
        });
    }

    public void loadImageRotated(ImageView container, String uri, BitmapTransformation... transformations) {
        GlideHelper.loadImageRotated(App.getContext(), container, uri, transformations);
    }

    public File getPhotoCacheDir(Context context, String cacheName) {
       return GlideHelper.getPhotoCacheDir(context,cacheName);
    }

    /**
     * 清理缓存
     *
     * @param uri
     */
    public void clearCache(String uri) {
        GlideHelper.clearCache();
    }

    /**
     * 自动根据屏幕尺寸适配图片高度
     *
     * @param view
     * @param uri
     */
    public void displayAutoResizeView(ImageView view, String uri,
                                      final int view_width) {
        Glide.with(App.getContext())
                .load(uri)
                .asBitmap()
                .error(R.drawable.result_btnkt)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        scalePic(view, resource, view_width);
                    }
                });
    }



    public void scalePic(View view, Bitmap bitmap, int view_width) {
        view.getLayoutParams().height = (int) (bitmap.getHeight() / (bitmap
                .getWidth() * 1.0 / view_width));
        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    /**
     * 自动根据屏幕尺寸适配图片高度
     *
     * @param view
     * @param uri
     */
    public void setAutoResizeView2(ImageView view, String uri,
                                   final int view_width, int view_w, int view_h) {
        scalePic(view, uri, view_width, view_w, view_h);
    }

    public void scalePic(ImageView view, String uri, int view_width, int view_w, int view_h) {
        view.getLayoutParams().height = (int) (view_h / (view_w * 1.0 / view_width));
        GlideHelper.display(App.getContext(), view, uri);
    }

    public void displayWebImageView(ImageView view, String uri, final ProgressBar pb) {
        GlideHelper.display(App.getContext(), view, uri, new BitmapImageViewTarget(view) {
            @Override
            public void onStart() {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStop() {
                pb.setVisibility(View.GONE);
            }
        });
    }

    public interface ImageLoadState {
        void onStop();
    }
}
