package com.ktfootball.app.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.utils.SharedUtils;
import com.ktfootball.app.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by jy on 16/6/7.
 */
public class SharedDialog extends Dialog {

    private BaseActivity activity;
    private SharedUtils sharedUtils;
    private Button cancelButton;
    private LinearLayout wx;
    private LinearLayout pyq;
    private LinearLayout xlwb;
    private LinearLayout qq;
    private LinearLayout qzom;
    private LinearLayout shouchan;

    private View.OnClickListener OnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener wxClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isWeixinAvilible(activity)){
                activity.showToast("正在打开微信");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_wx(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.d("Shared_wx","onComplete");
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        Log.d("Shared_wx", "onError");
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        Log.d("Shared_wx","onCancel");
                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
            }else{
                activity.showToast("请安装微信");
            }
            dismiss();
        }
    };

    private View.OnClickListener qqClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isQQAvilible(activity)){
                ((BaseActivity)activity).showToast("正在打开QQ");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_qq(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.d("onComplete");
                        Log.d("pyqClick","onComplete");
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.d("onCancel");
                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
            }else{
                ((BaseActivity)activity).showToast("请安装QQ");
            }
            dismiss();
        }
    };


    private View.OnClickListener pyqClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isWeixinAvilible(activity)){
                ((BaseActivity)activity).showToast("正在打开微信");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_pyq(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.d("onComplete");
                        Log.d("pyqClick","onComplete");
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.d("onCancel");
                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
            }else{
                ((BaseActivity)activity).showToast("请安装微信");
            }
            dismiss();
        }
    };

    private View.OnClickListener wxsc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isWeixinAvilible(activity)){
                ((BaseActivity)activity).showToast("正在打开微信");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_wxsc(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.d("onComplete");
                        Log.d("pyqClick","onComplete");
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.d("onCancel");
                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
            }else{
                ((BaseActivity)activity).showToast("请安装微信");
            }
            dismiss();
        }
    };

    private View.OnClickListener xlwbClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(isXlwbAvilible(activity)){
                ((BaseActivity)activity).showToast("正在打开新浪微博");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_xlvb(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
//                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
            }else{
                ((BaseActivity)activity).showToast("请安装新浪微博");
            }
            dismiss();
        }
    };


    private View.OnClickListener qqZom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            if(isQzon(activity)){
                ((BaseActivity)activity).showToast("正在打开QQ空间");
                activity.showLoadingDiaglog();
                sharedUtils.Shared_qqZom(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        activity.showToast("分享成功~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e(i+""+throwable.toString());
                        activity.showToast("分享失敗~");
                        activity.closeLoadingDialog();
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        activity.showToast("分享取消~");
                        activity.closeLoadingDialog();
                    }
                });
//            、
            dismiss();
        }
    };


    public SharedDialog(BaseActivity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
        sharedUtils = new SharedUtils();
        BitmapDrawable bd = (BitmapDrawable) activity.getResources().getDrawable(R.mipmap.logo);
        setBitmap(bd.getBitmap());
    }

    public SharedDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_choose);
        wx = (LinearLayout) findViewById(R.id.dialog_shared_wx);
        pyq = (LinearLayout) findViewById(R.id.dialog_shared_pyq);
        xlwb = (LinearLayout) findViewById(R.id.dialog_shared_xlwb);
        qq = (LinearLayout) findViewById(R.id.dialog_shared_qq);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        qzom = (LinearLayout) findViewById(R.id.dialog_shared_qqzome);
        shouchan = (LinearLayout) findViewById(R.id.dialog_shared_shouchan);
        init();
    }

    private void init() {
        cancelButton.setOnClickListener(OnCancelClick);
        wx.setOnClickListener(wxClick);
        pyq.setOnClickListener(pyqClick);
        xlwb.setOnClickListener(xlwbClick);
        qq.setOnClickListener(qqClick);
        qzom.setOnClickListener(qqZom);
        shouchan.setOnClickListener(wxsc);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
    }

    public static boolean isAvilible(Context context,String packageNamestr) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageNamestr)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isWeixinAvilible(Context context) {
        return isAvilible(context,"com.tencent.mm");
    }

    public static boolean isQQAvilible(Context context) {
        return isAvilible(context,"com.tencent.mobileqq");
    }

    public static boolean isXlwbAvilible(Context context) {
        return isAvilible(context,"com.sina.weibo");
    }

    public static boolean isQzon(Context context) {
        return isAvilible(context,"com.qzone ");
    }


    public void setTitle(String title) {
        sharedUtils.title = title;
    }

    public void setTitleUrl(String titleUrl) {
        sharedUtils.titleUrl = titleUrl;
    }

    public void setText(String text) {
        sharedUtils.text = text;
    }

    public void setImageUrl(String imageUrl) {
        sharedUtils.imageUrl = imageUrl;
    }

    public void setSite(String site) {
        sharedUtils.site = site;
    }

    public void setSiteUrl(String siteUrl) {
        sharedUtils.siteUrl = siteUrl;
    }

    public void setBitmap(Bitmap bitmap) {
        sharedUtils.bitmap = bitmap;
    }
}
