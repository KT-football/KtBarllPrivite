package com.ktfootball.app.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
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
public class SuperStarRecommendAdapter extends RecyclerView.Adapter<SuperStarRecommendAdapter.SuperStarViewHolder> {

    public BaseActivity activity;
    public List<CommentedVideos.Videos> videoses;

    public SuperStarRecommendAdapter(BaseActivity activity, List<CommentedVideos.Videos> videoses) {
        super();
        this.videoses = videoses;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return videoses.size();
    }

    @Override
    public SuperStarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SuperStarViewHolder viewHolder = null;
        viewHolder = new SuperStarViewHolder(View.inflate(activity, R.layout.layout_superstardetails_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SuperStarViewHolder holder, final int position) {
        BitmapManager.getInstance().displayKTItem(((SuperStarViewHolder) holder).img, Constants.HOST + videoses.get(position).picture);
        ((SuperStarViewHolder) holder).time.setText(videoses.get(position).upload_date);
        ((SuperStarViewHolder) holder).name.setText(videoses.get(position).place);
//        if(videoses.get(position).is_commented == 1){
//            holder.new_star.setVisibility(View.GONE);
//        }else{
//            holder.new_star.setVisibility(View.VISIBLE);
//        }
        ((SuperStarViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentedVideos.Videos videos = videoses.get(position);
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_VIDEOS_ID, videos.id);
                intent.putExtra(Constants.EXTRA_SCORES, videos.scores);
                activity.startActivity(intent);
            }
        });
    }

    public class SuperStarViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name;
        public TextView time;
//        public ImageView new_star;

        public SuperStarViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.layout_superstardetails_img);
            name = (TextView) itemView.findViewById(R.id.layout_superstardetails_name);
            time = (TextView) itemView.findViewById(R.id.layout_superstardetails_time);
//            new_star = (ImageView) itemView.findViewById(R.id.layout_superstardetails_item_new);
        }
    }
}


