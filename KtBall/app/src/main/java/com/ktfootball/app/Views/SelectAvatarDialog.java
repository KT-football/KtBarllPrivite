package com.ktfootball.app.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Neil Zheng on 11/23.
 */
public class SelectAvatarDialog extends Dialog {

    public static final int UPDATE_AVATAR_RESULT = 144;
    public static final int PHOTO_REQUEST_CAMERA = 133;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 1333;// 从相册中选择
    public static final String PHOTO_FILE_NAME = "avatar.jpg";
    private File file;

    private Activity activity;
    private Uri imageUri;
    private Button captureButton;
    private Button galleryButton;
    private Button cancelButton;
    private View.OnClickListener OnCaptureClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (!getFile().exists()) getFile().mkdirs();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFile()));
                activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            } catch (Exception e) {
                ((BaseActivity)activity).showToast("请赋予应用使用照相机权限");
            }
            dismiss();
        }
    };
    private View.OnClickListener OnGalleryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (getFile().exists()) getFile().delete();

            try {
                getFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageUri = Uri.fromFile(getFile());
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 280);
            intent.putExtra("outputY", 280);
            intent.putExtra("return-data", true);

            activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            dismiss();
        }
    };
    private View.OnClickListener OnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public SelectAvatarDialog(Activity activity, int themeResId,File file) {
        super(activity, themeResId);
        this.activity = activity;
        this.file = file;
    }

    public SelectAvatarDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_avatar_choose);
        init();
    }

    private void init() {
        captureButton = (Button) findViewById(R.id.button_capture);
        galleryButton = (Button) findViewById(R.id.button_gallery);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        captureButton.setOnClickListener(OnCaptureClick);
        galleryButton.setOnClickListener(OnGalleryClick);
        cancelButton.setOnClickListener(OnCancelClick);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
