package com.kt.ktball.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kt.ktball.entity.Leagues;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.R;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/15.
 */
public class MyTeamAdapter extends BaseAdapter {

    ArrayList<Leagues> data;
    Context context;
    LayoutInflater inflater;

    public MyTeamAdapter(ArrayList<Leagues> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Leagues getItem(int position) {
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
            convertView = inflater.inflate(R.layout.my_team_list_item,parent,false);
            holder = new ViewHolder();
            holder.imageView  = (ImageView) convertView.findViewById(R.id.imageView64);
            holder.textView = (TextView) convertView.findViewById(R.id.textView88);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Leagues leagues = getItem(position);
        String url = "http://www.ktfootball.com" + leagues.usera_avatar;
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(holder.imageView);
        holder.textView.setText(leagues.name);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
