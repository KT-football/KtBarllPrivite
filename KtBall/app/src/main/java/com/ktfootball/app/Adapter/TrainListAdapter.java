package com.ktfootball.app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.R;

import java.util.List;

/**
 * Created by jy on 16/6/17.
 */
public class TrainListAdapter extends BaseRecyclerViewAdapter<AppCartoons.Cartoons,TrainListAdapter.TrainListViewHolder> {

    public TrainListAdapter(RecyclerView recyclerView, List<AppCartoons.Cartoons> appCartoonses) {
        super(recyclerView, appCartoonses);
    }

    @Override
    protected void bindView(TrainListViewHolder holder, int position, AppCartoons.Cartoons model) {
        BitmapManager.getInstance().display(holder.img, Constants.HEAD_HOST+model.avatar);
        holder.name.setText(model.name);
        holder.name_2.setText(model.sub_name);
        if("0".equals(model.is_added)){
            holder.tag.setVisibility(View.GONE);
        }else{
            holder.tag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public TrainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrainListViewHolder trainListViewHolder = new TrainListViewHolder(getRecyclerView(),getLayoutInflater(getContext(), R.layout.item_trainlist,parent,false),this);
        return trainListViewHolder;
    }

    public class TrainListViewHolder extends BaseViewHolder{

        public ImageView img;
        public TextView name;
        public ImageView tag;
        public TextView name_2;

        public TrainListViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            img = (ImageView) rootView.findViewById(R.id.item_trainlist_img);
            name = (TextView) rootView.findViewById(R.id.item_trainlist_name);
            tag = (ImageView) rootView.findViewById(R.id.item_trainlist_tag);
            name_2 = (TextView) rootView.findViewById(R.id.item_trainlist_name_2);
        }
    }
}
