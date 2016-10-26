package com.ktfootball.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kt.ktball.entity.Videos;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.MyDVActivity;
import com.ktfootball.app.UI.Activity.VideoDetailsActivity;

import java.util.List;

/**
 * Created by leo on 16/10/14.
 */

public class BattleChildAdapter extends RecyclerView.Adapter<BattleChildAdapter.BattleView> {
    private Context mContext;
    private List<BattleBean.VideosBean> mList;

    public BattleChildAdapter(Context context, List<BattleBean.VideosBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public BattleView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BattleView(LayoutInflater.from(mContext).inflate(R.layout.item_battle_child,null));
    }

    @Override
    public void onBindViewHolder(BattleView holder, int position) {
        switch (mList.get(position).getGame_video_type()) {
            case 0:
                holder.mType.setBackgroundResource(R.mipmap.to1v1);
                holder.mType.setText("1v1");
                break;
            case 1:
                holder.mType.setBackgroundResource(R.mipmap.to2v2);
                holder.mType.setText("2v2");
                break;
            case 2:
                holder.mType.setBackgroundResource(R.mipmap.to3v3);
                holder.mType.setText("3v3");
                break;
        }
        holder.mAddress.setText(mList.get(position).getLocal());
        holder.mCont.setText(mList.get(position).getScores());
        holder.mNameLeft.setText(mList.get(position).getUsers().get(1).getNickname());
        holder.mNameRight.setText(mList.get(position).getUsers().get(0).getNickname());
        Glide.with(mContext).load(Constants.HEAD_HOST + mList.get(position).getUsers().get(1).getAvatar()).error(R.drawable.result_btnkt).transform(new GlideCircleTransform(mContext)).into(holder.mHeadLeft);
        Glide.with(mContext).load(Constants.HEAD_HOST + mList.get(position).getUsers().get(0).getAvatar()).error(R.drawable.result_btnkt).transform(new GlideCircleTransform(mContext)).into(holder.mHeadRight);
        if (!mList.get(position).getScores().equals("")){
            if ((Integer.valueOf(mList.get(position).getScores().split(":")[0]) > Integer.valueOf(mList.get(position).getScores().split(":")[1]))) {
                holder.mTab1.setVisibility(View.VISIBLE);
                holder.mTab2.setVisibility(View.GONE);
                holder.mHeadLeft.setBackgroundResource(R.drawable.oval_glod);
                holder.mHeadRight.setBackgroundResource(R.drawable.oval_grey);
            } else {
                holder.mTab2.setVisibility(View.VISIBLE);
                holder.mTab1.setVisibility(View.GONE);
                holder.mHeadRight.setBackgroundResource(R.drawable.oval_glod);
                holder.mHeadLeft.setBackgroundResource(R.drawable.oval_grey);
            }
    }
        holder.mTime.setText(mList.get(position).getTime());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BattleView extends RecyclerView.ViewHolder {
        TextView mNameLeft,mNameRight;
        ImageView mHeadLeft,mHeadRight;
        TextView mAddress,mTime,mCont,mType;
        TextView mTab1,mTab2;

        public BattleView(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mNameLeft = (TextView) itemView.findViewById(R.id.tv_left);
            mNameRight = (TextView) itemView.findViewById(R.id.tv_right);
            mHeadLeft = (ImageView) itemView.findViewById(R.id.image_left);
            mHeadRight = (ImageView) itemView.findViewById(R.id.image_right);
            mAddress = (TextView) itemView.findViewById(R.id.tv_address);
            mCont = (TextView) itemView.findViewById(R.id.tv_cont);
            mType = (TextView) itemView.findViewById(R.id.tv_type);
            mTab1 = (TextView) itemView.findViewById(R.id.tab1);
            mTab2 = (TextView) itemView.findViewById(R.id.tab2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,VideoDetailsActivity.class);
                    intent.putExtra(Constants.EXTRA_VIDEOS_ID,mList.get(getPosition()).getGame_video_id());
                    intent.putExtra(Constants.EXTRA_SCORES,mList.get(getPosition()).getScores());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public void setData(List<BattleBean.VideosBean> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
