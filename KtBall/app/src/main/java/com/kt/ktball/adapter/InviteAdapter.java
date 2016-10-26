package com.kt.ktball.adapter;

import android.content.Context;
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
import com.ktfootball.app.UI.Activity.InviteActivity;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.kt.ktball.entity.Users;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.MyDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ARTHUR on 2016/4/18.
 */
public class InviteAdapter extends BaseAdapter {

    ArrayList<Users> data;
    Context context;
    LayoutInflater inflater;
    MyDialog myDialog;
    long userId;

    public InviteAdapter(ArrayList<Users> data, Context context) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.invite_list_item, parent, false);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.imageView53);
            holder.name = (TextView) convertView.findViewById(R.id.textView58);
            holder.zhandouli = (TextView) convertView.findViewById(R.id.textView59);
            holder.invite = (TextView) convertView.findViewById(R.id.textView105);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Users users = getItem(position);
        String url = Constants.HEAD_HOST + users.avatar;
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).error(R.drawable.result_btnkt).into(holder.avatar);
        holder.name.setText(users.nickname);
        holder.zhandouli.setText("战斗力：" + users.power);
        myDialog = new MyDialog(context, "正在提交");
        userId = PreferenceManager.getDefaultSharedPreferences(context).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        final String game_type = InviteActivity.game_type;
        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if (game_type.equals("2")) {
                    url = Constants.HOST +"users/invite_league3v3";
                } else {
                    url = Constants.HOST +"users/invite_league";
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user_id", userId);
                    jsonObject.put("to_user_id", users.user_id);
                    jsonObject.put("league_id", InviteActivity.league_id);
                    jsonObject.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject object) {
                                try {
                                    MyAlertDialog myAlertDialog = new MyAlertDialog(context);
                                    JSONObject jsonObject1 = new JSONObject(object.toString());
                                    String response = jsonObject1.getString("response");
                                    if (response.equals("success")) {
                                        holder.invite.setText("已邀请");
                                        myAlertDialog.doAlertDialog("发送邀请成功");
                                    } else if (response.equals("error")) {
                                        String msg = jsonObject1.getString("msg");
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
        return convertView;
    }

    class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView zhandouli;
        TextView invite;
    }
}
