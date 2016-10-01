package com.ktfootball.app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.R;

import java.util.List;

/**
 * Created by jy on 16/7/1.
 */
public class GamePlaceAdapter extends BaseRecyclerViewAdapter<GamePlace.Games,GamePlaceAdapter.GamePlaceViewHolder> {

    public GamePlaceAdapter(RecyclerView recyclerView, List<GamePlace.Games> games) {
        super(recyclerView, games);
    }

    @Override
    protected void bindView(GamePlaceViewHolder holder, int position, GamePlace.Games model) {
        BitmapManager.getInstance().display(holder.imageViewAvatar, Constants.HOST+model.avatar);
        holder.textViewName.setText(model.name);
    }

    @Override
    public GamePlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GamePlaceViewHolder gamePlaceViewHolder = new GamePlaceViewHolder(getRecyclerView(),getLayoutInflater(getContext(), R.layout.game_place_list_item,parent,false),this);
        return gamePlaceViewHolder;
    }

    public class GamePlaceViewHolder extends BaseViewHolder {

        public ImageView imageViewAvatar;
        public ImageView imageViewTo;
        public TextView textViewName;

        public GamePlaceViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            imageViewAvatar = (ImageView) rootView.findViewById(R.id.imageView13);
            imageViewTo = (ImageView) rootView.findViewById(R.id.imageView15);
            textViewName = (TextView) rootView.findViewById(R.id.textView25);
        }
    }
}
