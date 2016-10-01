package com.kt.ktball.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kt.ktball.entity.Users;
import com.ktfootball.app.R;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/12.
 */
public class RankingAdapter extends BaseAdapter {

    ArrayList<Users> data;
    Context context;
    LayoutInflater inflater;

    public RankingAdapter(Context context, ArrayList<Users> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Users getItem(int position) {
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
            convertView = inflater.inflate(R.layout.ranking_list_item,parent,false);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.imageView49);
            holder.rank = (TextView) convertView.findViewById(R.id.textView52);
            holder.fight = (TextView) convertView.findViewById(R.id.textView53);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Users users = getItem(position);
        String url = "http://www.ktfootball.com" + users.avatar;
        Glide.with(context).load(url).into(holder.avatar);
        holder.fight.setText("战斗力：" + users.power);
        holder.rank.setText("排名：" + (position+1));
        return convertView;
    }

    class ViewHolder{
        ImageView avatar;
        TextView rank;
        TextView fight;
    }
}
