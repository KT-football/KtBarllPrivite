package com.ktfootball.app.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ktfootball.app.UI.Activity.setting.UserinfoChangeActivity;
import com.ktfootball.app.R;

/**
 * Created by Neil Zheng on 11/23.
 */
public class SelectSexDialog extends Dialog {

    private UserinfoChangeActivity activity;
    private Button captureButton;
    private Button galleryButton;
    private Button cancelButton;
    private View.OnClickListener OnCaptureClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setSex(activity.getString(R.string.man));
            dismiss();
        }
    };
    private View.OnClickListener OnGalleryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setSex(activity.getString(R.string.gril));
            dismiss();
        }
    };
    private View.OnClickListener OnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public SelectSexDialog(UserinfoChangeActivity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    public SelectSexDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sex_choose);
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
}
