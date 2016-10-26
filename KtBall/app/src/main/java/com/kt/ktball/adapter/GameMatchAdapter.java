package com.kt.ktball.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kt.ktball.entity.Games;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/20.
 */
public class GameMatchAdapter extends BaseAdapter {

    ArrayList<Games> data;
    Context context;
    LayoutInflater inflater;

    public GameMatchAdapter(ArrayList<Games> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Games getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.game_match_list_item,parent,false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView89);
            holder.place = (TextView) convertView.findViewById(R.id.textView115);
            holder.name = (TextView) convertView.findViewById(R.id.textView116);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Games games = getItem(position);
        String uri = Constants.HEAD_HOST + games.avatar;
        Glide.with(context).load(uri).error(R.drawable.result_btnkt).into(holder.imageView);
        holder.place.setText(games.place);
        holder.name.setText(games.name);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView place;
        TextView name;
    }
}
