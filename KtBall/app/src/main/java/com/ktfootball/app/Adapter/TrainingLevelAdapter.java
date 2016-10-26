package com.ktfootball.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.view.ColorArcProgressBar;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.UserAppCartoonsStudy;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.CommonUtils;

import java.util.List;

/**
 * Created by jy on 16/6/15.
 */
public class TrainingLevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<UserAppCartoonsStudy.AppCartoons> list;

    public TrainingLevelAdapter(List<UserAppCartoonsStudy.AppCartoons> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new TrainHolder(layoutInflater(context, R.layout.item_train_default, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrainHolder trainHolder = (TrainHolder) holder;
        final UserAppCartoonsStudy.AppCartoons appCarToons = list.get(position);
        trainHolder.name.setText(appCarToons.name);
//        trainHolder.name_2.setText(appCarToons.sub_name);
        trainHolder.bar.setCurrentValues(Float.parseFloat(appCarToons.now_level_progress));
        int[] colcr = CommonUtils.getTrainColor(appCarToons.now_level_color);
        trainHolder.bar.setColors(colcr);
        trainHolder.bar.setHintString(appCarToons.now_level_name);
        trainHolder.bar.setHintPaintColor(colcr[0]);
        BitmapManager.getInstance().display(trainHolder.img, Constants.HEAD_HOST + appCarToons.avatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public View layoutInflater(Context context, int resd, ViewGroup root, boolean b) {
        return LayoutInflater.from(context).inflate(resd, root, b);
    }

    public class TrainHolder extends RecyclerView.ViewHolder {
        public TextView name;
//        public TextView name_2;
        public ImageView img;
        public ColorArcProgressBar bar;

        public TrainHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_train_default_name);
//            name_2 = (TextView) itemView.findViewById(R.id.item_train_default_name_2);
            img = (ImageView) itemView.findViewById(R.id.item_train_default_img);
            bar = (ColorArcProgressBar) itemView.findViewById(R.id.item_train_default_bar);
        }
    }
}
