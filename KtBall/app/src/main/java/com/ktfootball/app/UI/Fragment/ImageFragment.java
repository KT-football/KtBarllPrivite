package com.ktfootball.app.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Manager.bitmap.GlideHelper;
import com.ktfootball.app.R;

/**
 * Created by jy on 16/6/22.
 */
public class ImageFragment extends BaseFragment {

    private String path;
    private ImageView bg;
    private Bitmap bitmap;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_imagefragment);
        bg = getViewById(R.id.layout_imagefragment_iv);



    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle data = getArguments();
        path = data.getString("path");
        showToast(path);
        LogUtils.e(path);
        //        bitmap = BitmapFactory.decodeFile(path);
//        BitmapDrawable drawable =new BitmapDrawable(bitmap);
//        bg.setBackgroundDrawable(drawable);
//        Glide.with(this).load(path).into(bg);
        GlideHelper.display(this,bg,path);

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
