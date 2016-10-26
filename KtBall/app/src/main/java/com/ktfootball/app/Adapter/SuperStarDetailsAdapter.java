package com.ktfootball.app.Adapter;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.entity.Videos;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.CommentedVideos;
import com.ktfootball.app.Entity.StarUserProfile;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.VideoDetailsActivity;

import java.util.List;

/**
 * Created by jy on 16/7/21.
 */
public class SuperStarDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public StarUserProfile starUserProfile;
    public BaseActivity activity;
    public List<CommentedVideos.Videos> videoses;
    public final int HEADER = 1;
    public final int ITEM = 2;

    public SuperStarDetailsAdapter(BaseActivity activity, List<CommentedVideos.Videos> videoses, StarUserProfile starUserProfile) {
        super();
        this.starUserProfile = starUserProfile;
        this.videoses = videoses;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
            return ITEM;
    }

    @Override
    public int getItemCount() {
        return videoses.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEADER) {
            viewHolder = new SuperStarHeaderViewHolder(View.inflate(activity, R.layout.layout_superstardetails, null));
        } else {
            viewHolder = new SuperStarViewHolder(View.inflate(activity, R.layout.layout_superstardetails_item, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof SuperStarHeaderViewHolder){
            ((SuperStarHeaderViewHolder) holder).details.setText(starUserProfile.star_intro);
            ((SuperStarHeaderViewHolder) holder).name.setText(starUserProfile.nickname);
            BitmapManager.getInstance().displayUserLogo(((SuperStarHeaderViewHolder) holder).header, Constants.HEAD_HOST+starUserProfile.avatar);
        }else{
            BitmapManager.getInstance().displayKTItem(((SuperStarViewHolder) holder).img, Constants.HOST+videoses.get(position).picture);
            ((SuperStarViewHolder) holder).time.setText(videoses.get(position).upload_date);
            ((SuperStarViewHolder) holder).name.setText(videoses.get(position).place);
//            ((SuperStarViewHolder) holder).new_item.setVisibility(View.GONE);
            ((SuperStarViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentedVideos.Videos videos = videoses.get(position);
                    Intent intent = new Intent(activity,VideoDetailsActivity.class);
                    intent.putExtra(Constants.EXTRA_VIDEOS_ID,videos.id);
                    intent.putExtra(Constants.EXTRA_SCORES,videos.scores);
                    activity.startActivity(intent);
                }
            });
        }
    }

    public class SuperStarViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
//        public ImageView new_item;
        public TextView name;
        public TextView time;

        public SuperStarViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.layout_superstardetails_img);
//            new_item = (ImageView) itemView.findViewById(R.id.layout_superstardetails_item_new);
            name = (TextView) itemView.findViewById(R.id.layout_superstardetails_name);
            time = (TextView) itemView.findViewById(R.id.layout_superstardetails_time);
        }
    }

    public class SuperStarHeaderViewHolder extends RecyclerView.ViewHolder {

        public MyCircleImageView header;
        public TextView name;
        public TextView details;

        public SuperStarHeaderViewHolder(View itemView) {
            super(itemView);
            header = (MyCircleImageView) itemView.findViewById(R.id.layout_superstardetasil_header);
            name = (TextView) itemView.findViewById(R.id.layout_superstardetasil_name);
            details = (TextView) itemView.findViewById(R.id.layout_superstardetasil_details);
        }
    }
}
