package com.kt.ktball.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.ktfootball.app.Constants;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.kt.ktball.entity.Comments;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class CommentsAdapter extends BaseAdapter {

    ArrayList<Comments> data;
    Context context;
    LayoutInflater inflater;

    public CommentsAdapter(ArrayList<Comments> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Comments getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comments_list_item, parent, false);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.imageView63);
            holder.name = (TextView) convertView.findViewById(R.id.textView84);
            holder.comments = (TextView) convertView.findViewById(R.id.textView85);
            holder.delete = (TextView) convertView.findViewById(R.id.textView86);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comments comments = getItem(position);
        String uri = Constants.HEAD_HOST + comments.avatar;
        Glide.with(context).load(uri).transform(new GlideCircleTransform(context)).into(holder.avatar);
        holder.name.setText(comments.nickname);
        holder.comments.setText(comments.content);
        holder.delete.setText(comments.can_delete == 0 ? "" : "删除");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("是否删除该条评论");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = Constants.HOST + "videos/delete_comment";
                        JSONObject jsonObject = new JSONObject();
                        long userId = PreferenceManager.getDefaultSharedPreferences(context).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
                        try {
                            jsonObject.put("user_id", userId);
                            jsonObject.put("game_video_comment_id", comments.game_video_comment_id);
                            jsonObject.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, uri, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try {
                                            JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                                            String response = jsonObject11.getString("response");
                                            if (response.equals("success")) {
                                                data.remove(position);
                                                notifyDataSetChanged();
                                            } else if (response.equals("error")) {
                                                String msg = jsonObject11.getString("msg");
                                                MyAlertDialog myAlertDialog = new MyAlertDialog(context);
                                                myAlertDialog.doAlertDialog(msg);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Content-Type", "application/json; charset=UTF-8");
                                return headers;
                            }
                        };
                        VolleyUtil.getInstance(context).addRequest(jsonRequest);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView comments;
        TextView delete;
    }
}
