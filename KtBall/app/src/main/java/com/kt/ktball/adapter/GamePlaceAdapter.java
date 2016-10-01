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
import com.ktfootball.app.R;

import java.util.ArrayList;

/**
 * Created by ww on 2016/4/4.
 */
public class GamePlaceAdapter extends BaseAdapter {

    ArrayList<Games> data;
    Context context;
    LayoutInflater inflater;

    public GamePlaceAdapter(Context context, ArrayList<Games> data) {
        this.context = context;
        this.data = data;
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
            convertView = inflater.inflate(R.layout.game_place_list_item,parent,false);
            holder = new ViewHolder();
            holder.imageViewAvatar = (ImageView) convertView.findViewById(R.id.imageView13);
            holder.textViewLine = (TextView) convertView.findViewById(R.id.textView26);
            holder.imageViewTo = (ImageView) convertView.findViewById(R.id.imageView15);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textView25);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Games games = getItem(position);
        String url = "http://www.ktfootball.com" + games.avatar;
        Glide.with(context).load(url).into(holder.imageViewAvatar);
        holder.textViewName.setText(games.name);
        return convertView;
    }

    class ViewHolder{
        ImageView imageViewAvatar;
        TextView textViewLine;
        ImageView imageViewTo;
        TextView textViewName;
    }
}
