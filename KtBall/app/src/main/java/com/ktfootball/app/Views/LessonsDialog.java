package com.ktfootball.app.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.PhoneUtils;
import com.kt.ktball.adapter.LessonsAdapter;
import com.ktfootball.app.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil Zheng on 11/23.
 */
public class LessonsDialog extends Dialog {

    List<Integer> list = new ArrayList<>();
    private Activity activity;
    private RecyclerView rc;
    private LessonsAdapter adapter;

    public LessonsDialog(Activity activity, int themeResId,int code) {
        super(activity, themeResId);
        this.activity = activity;
        if(code == 0){
            list.add(R.drawable.icon_less1_1);
            list.add(R.drawable.icon_less1_2);
            list.add(R.drawable.icon_less1_3);
        }else{
            list.add(R.drawable.icon_less2_1);
            list.add(R.drawable.icon_less1_2);
            list.add(R.drawable.icon_less1_3);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);
        init();
    }

    private void init() {
        rc = (RecyclerView) findViewById(R.id.layout_recyclerview_rv);
        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        rc.setPadding(0,0,PhoneUtils.dip2px(getContext(),45),PhoneUtils.dip2px(getContext(),45));
        LinearLayoutManager ll = new LinearLayoutManager(activity);
        adapter = new LessonsAdapter(rc,list);
        rc.setAdapter(adapter);
        rc.setLayoutManager(ll);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        params.horizontalMargin = PhoneUtils.dip2px(getContext(),45);
//        params.verticalMargin = PhoneUtils.dip2px(getContext(),45);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
        setCanceledOnTouchOutside(true);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        if(adapter != null){
            adapter.setOnItemClickListener(listener);
        }
    }
}
