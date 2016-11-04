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
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/12.
 */
public class MyFirendAdapter extends BaseAdapter {

    ArrayList<Users> data;
    Context context;
    LayoutInflater inflater;

    public MyFirendAdapter(ArrayList<Users> data, Context context) {
        this.data = data;
        this.context = context;
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_firend_list_item, parent, false);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.imageView53);
            holder.name = (TextView) convertView.findViewById(R.id.textView58);
            holder.zhandouli = (TextView) convertView.findViewById(R.id.textView59);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Users users = getItem(position);
        String url = Constants.HEAD_HOST + users.avatar;
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).error(R.drawable.result_btnkt).into(holder.avatar);
        holder.name.setText(users.nickname);
        holder.zhandouli.setText(context.getString(R.string.fighting_capacity) + ":" + users.power);
        return convertView;
    }

    class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView zhandouli;
    }
}
