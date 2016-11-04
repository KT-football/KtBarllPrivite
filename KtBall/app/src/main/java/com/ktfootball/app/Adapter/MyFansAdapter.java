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
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MyFasnBean;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.UserProfiles;

import java.util.List;

/**
 * Created by leo on 16/10/20.
 */

public class MyFansAdapter extends RecyclerView.Adapter<MyFansAdapter.ViewHoder> {
    private Context mContext;
    private List<MyFasnBean.FansBean> mList;

    public MyFansAdapter(Context mContext, List<MyFasnBean.FansBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item_my_fans, null));
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        Glide.with(mContext).load(Constants.HEAD_HOST+mList.get(position).getAvatar()).transform(new GlideCircleTransform(mContext)).error(R.drawable.result_btnkt).into(holder.mHead);
        holder.mName.setText(mList.get(position).getNickname());
        holder.mZhan.setText(mContext.getString(R.string.fighting_capacity)+":"+mList.get(position).getPower());
        holder.mCancle.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<MyFasnBean.FansBean> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView mHead;
        private TextView mName, mZhan,mCancle;

        public ViewHoder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.image_head);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mZhan = (TextView) itemView.findViewById(R.id.tv_zandou);
            mCancle = (TextView) itemView.findViewById(R.id.tv_cancle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserProfiles.class);
                    intent.putExtra(UserProfiles.USERID, mList.get(getPosition()).getUser_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
