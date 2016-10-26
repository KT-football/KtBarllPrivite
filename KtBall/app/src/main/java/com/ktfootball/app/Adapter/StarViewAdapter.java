package com.ktfootball.app.Adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.kt.ktball.App;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.StarUsers;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.R;

import java.util.List;

/**
 * Created by jy on 16/7/21.
 */
public class StarViewAdapter extends BaseRecyclerViewAdapter<StarUsers.Star, StarViewAdapter.StarViewhoder> {

    public StarViewAdapter(RecyclerView recyclerView, List<StarUsers.Star> stars) {
        super(recyclerView, stars);
    }

    @Override
    protected void bindView(final StarViewhoder holder, int position, StarUsers.Star model) {
        Glide.with(mContext).load(Constants.HEAD_HOST + model.avatar).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.header.setImageBitmap(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                holder.header.setImageResource(R.drawable.result_btnkt);
            }
        });
        holder.name.setText(model.nickname);
    }

    @Override
    public StarViewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        StarViewhoder starViewhoder = new StarViewhoder(getRecyclerView(), getLayoutInflater(getContext(), R.layout.item_starlist, parent, false), this);
        return starViewhoder;
    }

    public class StarViewhoder extends BaseViewHolder {

        public ImageView header;
        public TextView name;

        public StarViewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            header = (ImageView) rootView.findViewById(R.id.item_staritem_header);
            name = (TextView) rootView.findViewById(R.id.item_staritem_name);
        }
    }
}
