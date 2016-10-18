package com.ktfootball.app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.kt.ktball.App;
import com.kt.ktball.entity.Comments;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.StarComment;
import com.ktfootball.app.Entity.SubComment;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.UI.Activity.VideoDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jy on 16/7/12.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private VideoDetailsActivity context;
    private List<StarComment> group_list;
    private List<List<SubComment>> item_list;
    LayoutInflater inflater;

    public MyExpandableListViewAdapter(VideoDetailsActivity context, List<StarComment> group_list, List<List<SubComment>> item_list) {
        this.context = context;
        this.group_list = group_list;
        this.item_list = item_list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return group_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return item_list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item_list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comments_list_item, parent, false);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.imageView63);
            holder.name = (TextView) convertView.findViewById(R.id.textView84);
            holder.comments = (TextView) convertView.findViewById(R.id.textView85);
            holder.delete = (TextView) convertView.findViewById(R.id.textView86);
            holder.num = (TextView) convertView.findViewById(R.id.comments_list_item_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final StarComment comments = getItem(groupPosition);
        String uri = Constants.HOST + comments.avatar;
        Glide.with(context).load(uri).transform(new GlideCircleTransform(context)).into(holder.avatar);
        holder.name.setText(comments.nickname+":");
        holder.comments.setText(comments.content);
        holder.num.setText(item_list.get(groupPosition).size()+"");
        if("1".equals(comments.is_star)){
            holder.delete.setVisibility(View.VISIBLE);
            holder.name.setTextColor(Color.parseColor("#FFD700"));
            holder.comments.setTextColor(Color.parseColor("#FFD700"));
            holder.num.setTextColor(Color.parseColor("#FFD700"));

        }else{
            holder.delete.setVisibility(View.GONE);
            holder.name.setTextColor(0xff333333);
            holder.comments.setTextColor(0xff333333);
            holder.num.setTextColor(0xff333333);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.doComment(v,comments.id+"");
            }
        });
        return convertView;
    }

    public StarComment getItem(int position) {
        return group_list.get(position);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.avatar = (ImageView) convertView.findViewById(R.id.imageView63);
            itemHolder.name = (TextView) convertView.findViewById(R.id.textView84);
            itemHolder.comments = (TextView) convertView.findViewById(R.id.textView85);

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        final SubComment comments = item_list.get(groupPosition).get(childPosition);
        String uri = Constants.HOST + comments.avatar;
        Glide.with(context).load(uri).transform(new GlideCircleTransform(context)).into(itemHolder.avatar);
        itemHolder.name.setText(comments.nickname+":");
        itemHolder.comments.setText(comments.content);
        if(comments.is_star == null || !"1".equals(comments.is_star)){
            itemHolder.name.setTextColor(0xff333333);
            itemHolder.comments.setTextColor(0xff333333);
        }else{
            itemHolder.name.setTextColor(Color.parseColor("#FFD700"));
            itemHolder.comments.setTextColor(Color.parseColor("#FFD700"));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

class ViewHolder {
    ImageView avatar;
    TextView name;
    TextView comments;
    TextView delete;
    TextView num;
}

class ItemHolder {
    ImageView avatar;
    TextView name;
    TextView comments;
}
