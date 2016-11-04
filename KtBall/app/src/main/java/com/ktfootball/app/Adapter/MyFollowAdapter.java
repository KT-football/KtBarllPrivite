package com.ktfootball.app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.kt.ktball.App;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MyFasnBean;
import com.ktfootball.app.Entity.MyFollowBean;
import com.ktfootball.app.Event.FollowEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/20.
 */

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHoder> {
    private Context mContext;
    private List<MyFollowBean.FollowersBean> mList;

    public MyFollowAdapter(Context mContext, List<MyFollowBean.FollowersBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyFollowAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item_my_fans, null));
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, final int position) {
        Glide.with(mContext).load(Constants.HEAD_HOST + mList.get(position).getAvatar()).transform(new GlideCircleTransform(mContext)).error(R.drawable.result_btnkt).into(holder.mHead);
        holder.mName.setText(mList.get(position).getNickname());
        holder.mZhan.setText(mContext.getString(R.string.fighting_capacity)+":" + mList.get(position).getPower());
        holder.mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.is_cancle_follow));
                builder.setPositiveButton(mContext.getString(R.string.right), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = Constants.HOST +"users/cancel_follow";
                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            jsonObject1.put("user_id", App.getUserId());
                            jsonObject1.put("follow_user_id", mList.get(position).getUser_id());
                            jsonObject1.put("authenticity_token", MD5.getToken(url));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                jsonObject1,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        Log.d("----------", jsonObject.toString());
                                        try {
                                            JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                                            String response = jsonObject11.getString("response");
                                            if (response.equals("success")) {
                                                EventBus.getDefault().post(new FollowEvent());
                                            } else if (response.equals("error")) {
                                                String msg = jsonObject11.getString("msg");
                                                MyAlertDialog myAlertDialog = new MyAlertDialog(mContext);
                                                myAlertDialog.doAlertDialog(msg);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                            }
                        }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Content-Type", "application/json; charset=UTF-8");
                                return headers;
                            }
                        };
                        VolleyUtil.getInstance(mContext).addRequest(jsonRequest);
                    }

                }).setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<MyFollowBean.FollowersBean> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView mHead;
        private TextView mName, mZhan, mCancle;

        public ViewHoder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.image_head);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mZhan = (TextView) itemView.findViewById(R.id.tv_zandou);
            mCancle = (TextView) itemView.findViewById(R.id.tv_cancle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserProfiles.class);
                    intent.putExtra(UserProfiles.USERID, mList.get(getPosition()).getUser_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }

}

