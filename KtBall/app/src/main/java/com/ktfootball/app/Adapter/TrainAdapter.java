package com.ktfootball.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.view.ColorArcProgressBar;
import com.ktfootball.app.UI.Activity.train.AddClassActivity;
import com.ktfootball.app.UI.Activity.train.TrainListActivity;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.UI.Activity.train.TrainDetailsActivity;
import com.ktfootball.app.Entity.UserAppCartoons;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.CommonUtils;
import com.ktfootball.app.Views.CircleProgressView;
import com.ktfootball.app.Views.RingView;
import com.mob.tools.gui.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/6/15.
 */
public class TrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserAppCartoons.AppCarToons> list;
    private BaseActivity context;
    private UserAppCartoons userAppCartoons;
    private final int HEADER = 10;
    private final int DEFUL = 11;
    private List<SwipedState> mItemSwipedStates;

    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    public TrainAdapter(UserAppCartoons userAppCartoons, BaseActivity context) {
        list = userAppCartoons.app_cartoons;
        this.context = context;
        this.userAppCartoons = userAppCartoons;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i < userAppCartoons.app_cartoons.size()+1; i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEADER) {
            viewHolder = new TrainHeaderHolder(layoutInflater(context, R.layout.item_train_header, parent, false));
        } else {
//            viewHolder = new TrainHolder(layoutInflater(context, R.layout.item_train_default, parent, false));

            // Create a new view which is basically just a ViewPager in this case
            ViewPager v = (ViewPager) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_train_default, parent, false);
            ViewPagerAdapter adapter = new ViewPagerAdapter();

            ((ViewPager) v.findViewById(R.id.viewPager)).setAdapter(adapter);

            //Perhaps the first most crucial part. The ViewPager loses its width information when it is put
            //inside a RecyclerView. It needs to be explicitly resized, in this case to the width of the
            //screen. The height must be provided as a fixed value.
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            v.getLayoutParams().width = displayMetrics.widthPixels;
            v.requestLayout();

            viewHolder = new TrainHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == HEADER) {
            TrainHeaderHolder trainHeaderHolder = (TrainHeaderHolder) holder;
            BitmapManager.getInstance().display(trainHeaderHolder.header, Constants.HEAD_HOST + userAppCartoons.avatar);
            trainHeaderHolder.yxl.setText(userAppCartoons.total_finished_minutes);
            trainHeaderHolder.ywc.setText(userAppCartoons.total_finished_times+"min");
            trainHeaderHolder.lxxl.setText(userAppCartoons.study_days+""+context.getString(R.string.day));
            trainHeaderHolder.mytrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,AddClassActivity.class));
                }
            });
        } else {
            final TrainHolder trainHolder = (TrainHolder) holder;
            final UserAppCartoons.AppCarToons appCarToons = list.get(position - 1);
            trainHolder.name.setText(appCarToons.name);
//            trainHolder.name_2.setText(appCarToons.sub_name);
            trainHolder.bar.setAngle((int) Float.parseFloat(appCarToons.now_level_progress));
            int[] colcr = CommonUtils.getTrainColor(appCarToons.now_level_color);
            trainHolder.bar.setText(appCarToons.now_level_name);
            trainHolder.bar.setGravity(Gravity.CENTER);
            BitmapManager.getInstance().display(trainHolder.img, Constants.HEAD_HOST + appCarToons.avatar);
            trainHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrainDetailsActivity.class);
                    intent.putExtra(Constants.APP_CARTOON_ID,appCarToons.id);
                    intent.putExtra(Constants.SUB_NAME,appCarToons.sub_name);
                    context.startActivity(intent);
                }
            });
             trainHolder.item_wanc.setText(context.getString(R.string.is_ok)+(5*(int)Float.parseFloat(appCarToons.now_level_progress)));
             trainHolder.item_shenyu.setText(context.getString(R.string.distance_to_apprentice)+(500 -(5*(int)Float.parseFloat(appCarToons.now_level_progress)))+context.getString(R.string.train));
            ((ViewPager) trainHolder.mView).setCurrentItem(mItemSwipedStates.get(position).ordinal());
            trainHolder.delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(context.getString(R.string.prompt));
                    dialog.setMessage(context.getString(R.string.is_clear_class));
                    dialog.setNegativeButton(context.getString(R.string.right),new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(FileUtil.deleteFile(new File(FileUtil.getDecompressionDir(context)+appCarToons.name))){
                                context.showDialogToast(context.getString(R.string.delete_success));
                            }else{
                                context.showDialogToast(context.getString(R.string.delete_file_nofind));
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton(context.getString(R.string.cancle), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create().show();
                }
            });
            ((ViewPager) trainHolder.mView).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int previousPagePosition = 0;

                @Override
                public void onPageScrolled(int pagePosition, float positionOffset, int positionOffsetPixels) {
                    if (pagePosition == previousPagePosition)
                        return;

                    switch (pagePosition) {
                        case 0:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_PRIMARY_CONTENT);
                            break;
                        case 1:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_SECONDARY_CONTENT);
                            break;

                    }
                    previousPagePosition = pagePosition;

                    Log.i("MyAdapter", "PagePosition " + position + " set to " + mItemSwipedStates.get(position).ordinal());
                }

                @Override
                public void onPageSelected(int pagePosition) {
                    //This method keep incorrectly firing as the RecyclerView scrolls.
                    //Use the one above instead
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return DEFUL;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public View layoutInflater(Context context, int resd, ViewGroup root, boolean b) {
        return LayoutInflater.from(context).inflate(resd, root, b);
    }

    public class TrainHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;
        public RingView bar;
        public View mView;
        public Button delect;
        public RelativeLayout rl;
        private TextView item_wanc,item_shenyu;

        public TrainHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.item_train_default_name);
            rl = (RelativeLayout) itemView.findViewById(R.id.primaryContentCardView);
            img = (ImageView) itemView.findViewById(R.id.item_train_default_img);
            bar = (RingView) itemView.findViewById(R.id.item_train_default_bar);
            delect = (Button) itemView.findViewById(R.id.btn1);
            item_shenyu = (TextView) itemView.findViewById(R.id.item_shenyu);
            item_wanc = (TextView) itemView.findViewById(R.id.item_wanc);
        }
    }

    public class TrainHeaderHolder extends RecyclerView.ViewHolder {

        public MyCircleImageView header;
        public TextView yxl;
        public TextView ywc;
        public TextView mytrain;
        public TextView lxxl;

        public TrainHeaderHolder(View itemView) {
            super(itemView);
            header = (MyCircleImageView) itemView.findViewById(R.id.item_train_header_header);
            yxl = (TextView) itemView.findViewById(R.id.item_train_header_yxl_tv);
            mytrain = (TextView) itemView.findViewById(R.id.item_train_header_mytrain);
            ywc = (TextView) itemView.findViewById(R.id.item_train_header_ywc_tv);
            lxxl = (TextView) itemView.findViewById(R.id.item_train_header_lxxl_tv);
        }
    }
}
