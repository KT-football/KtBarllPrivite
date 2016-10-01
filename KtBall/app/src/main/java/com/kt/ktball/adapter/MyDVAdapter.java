package com.kt.ktball.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kt.ktball.entity.StrataExternum;
import com.kt.ktball.entity.User;
import com.kt.ktball.entity.Videos;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ARTHUR on 2016/4/13.
 */
public class MyDVAdapter extends BaseAdapter {

    StrataExternum strataExternum;
    ArrayList<Videos> data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public MyDVAdapter(StrataExternum strataExternum, Context context) {
        this.strataExternum = strataExternum;
        this.context = context;
        inflater = LayoutInflater.from(context);
        data = strataExternum.getVideos();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Videos getItem(int position) {
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
            convertView = inflater.inflate(R.layout.my_dv_list_item,parent,false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView52);
            holder.name = (TextView) convertView.findViewById(R.id.textView61);
            holder.address = (TextView) convertView.findViewById(R.id.textView62);
            holder.biFen = (TextView) convertView.findViewById(R.id.textView64);
            holder.time = (TextView) convertView.findViewById(R.id.textView63);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Videos videos = getItem(position);
        ArrayList<User> users = videos.getUsers();
//        holder.imageView.setImageResource(R.drawable.vcr_bg);
        BitmapManager.getInstance().displayKTItem( holder.imageView, Constants.HOST+videos.picture);
        holder.name.setText(users.get(0).getNickname() + "VS" + users.get(1).getNickname());
        holder.address.setText("@" + videos.getLocal());
        holder.biFen.setText(videos.getScores());
        String dataString = formatData("yyyy-MM-dd hh:mm:ss",Long.parseLong(videos.getVideo_time()));
        holder.time.setText(dataString);
        return convertView;
    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }

    class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView address;
        TextView biFen;
        TextView time;
    }
}
