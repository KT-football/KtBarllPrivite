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
import com.kt.ktball.entity.Leagues;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.TeamDetailsActivity;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/15.
 */

public class CorpsAdapter extends RecyclerView.Adapter<CorpsAdapter.CorpsView> implements StickyRecyclerHeadersAdapter<CorpsAdapter.CorpsHeadView> {
    private List<String> mHeadList = new ArrayList<>();
    private ArrayList<Leagues> leagues2 = new ArrayList<>();
    private Context mContext;

    public CorpsAdapter(Context mContext) {
        this.mContext = mContext;
        mHeadList.add("2 V 2");
        mHeadList.add("3 V 3");

    }

    public void setData(ArrayList<Leagues> leagues2) {
        this.leagues2 = leagues2;
        notifyDataSetChanged();
    }

    @Override
    public long getHeaderId(int position) {
        return leagues2.get(position).code;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CorpsView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CorpsView(LayoutInflater.from(mContext).inflate(R.layout.item_corps, null));
    }

    @Override
    public void onBindViewHolder(CorpsView holder, int position) {
        if (leagues2.get(position).usera_id == 0) {
//            holder.view.setVisibility(View.GONE);
            holder.tv_empty_content.setVisibility(View.VISIBLE);
            holder.mName.setVisibility(View.INVISIBLE);
            holder.mHead.setVisibility(View.INVISIBLE);
        }else {
            holder.tv_empty_content.setVisibility(View.GONE);
            holder.mName.setText(leagues2.get(position).name);
            Glide.with(mContext).load("http://www.ktfootball.com" + leagues2.get(position).usera_avatar).transform(new GlideCircleTransform(mContext)).into(holder.mHead);
        }
    }


    @Override
    public int getItemCount() {
        return leagues2.size();
    }

    public class CorpsView extends RecyclerView.ViewHolder {
        TextView mName,tv_empty_content;
        ImageView mHead;
        View view;

        public CorpsView(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            view = itemView;
            mHead = (ImageView) itemView.findViewById(R.id.tv_head);
            tv_empty_content = (TextView) itemView.findViewById(R.id.tv_empty_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_empty_content.getVisibility() ==View.GONE) {
                        Intent intent = new Intent(mContext, TeamDetailsActivity.class);
                        intent.putExtra("team_id", leagues2.get(getPosition()).league_id);
                        intent.putExtra("game_type", leagues2.get(getPosition()).game_type);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public class CorpsHeadView extends RecyclerView.ViewHolder {
        private ImageView mAdd;
        private TextView mTitle;

        public CorpsHeadView(View itemView) {
            super(itemView);
            mAdd = (ImageView) itemView.findViewById(R.id.image_add);
            mTitle = (TextView) itemView.findViewById(R.id.tv_titile);
        }
    }


    @Override
    public CorpsHeadView onCreateHeaderViewHolder(ViewGroup parent) {
        return new CorpsHeadView(LayoutInflater.from(mContext).inflate(R.layout.item_corps_head, null));
    }


    @Override
    public void onBindHeaderViewHolder(CorpsHeadView holder, int position) {
        switch (leagues2.get(position).game_type) {
            case "2":
                holder.mTitle.setText(mHeadList.get(1));
                break;
            default:
                holder.mTitle.setText(mHeadList.get(0));
                break;
        }

    }
}
