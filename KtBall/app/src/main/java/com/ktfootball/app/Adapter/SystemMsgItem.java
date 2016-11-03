package com.ktfootball.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MessageBean;
import com.ktfootball.app.Event.SystemMsgEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.VideoDetailsActivity;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/19.
 */
public class SystemMsgItem extends RecyclerView.Adapter<SystemMsgItem.SystemView> {
    private Context mContext;
    private List<MessageBean.UserMessagesBean> mList;

    public SystemMsgItem(Context mContext, List<MessageBean.UserMessagesBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SystemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SystemView(LayoutInflater.from(mContext).inflate(R.layout.item_system_msg, null));
    }

    @Override
    public void onBindViewHolder(SystemView holder, int position) {
        if (mList.get(position).getIs_read() == 1)
            holder.tv_content.setTextColor(0xff999999);
        else
            holder.tv_content.setTextColor(0xff333333);
        holder.tv_content.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SystemView extends RecyclerView.ViewHolder {
        private TextView tv_content;

        public SystemView(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(getPosition()).getIs_read() == 0)
                        readMessage(tv_content, getPosition());
                    else {
                        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                        intent.putExtra(Constants.EXTRA_VIDEOS_ID, mList.get(getPosition()).getGame_video_id());
                        intent.putExtra(Constants.EXTRA_SCORES, "");
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }


    /**
     * 读取消息
     *
     * @param tv_content
     */
    private void readMessage(final TextView tv_content, final int position) {
        ((BaseActivity) mContext).showLoadingDiaglog();
        String url = Constants.RED_MESSAGE + "?user_message_id="
                + mList.get(position).getUser_message_id() + "&authenticity_token="+ MD5.getToken(Constants.RED_MESSAGE);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        ((BaseActivity) mContext).closeLoadingDialog();
                        try {
                            if (jsonObject.getString("response").equals("success")) {
                                tv_content.setTextColor(0xff999999);
                                Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                                intent.putExtra(Constants.EXTRA_VIDEOS_ID, mList.get(position).getGame_video_id());
                                intent.putExtra(Constants.EXTRA_SCORES, "");
                                mContext.startActivity(intent);
                                EventBus.getDefault().post(new SystemMsgEvent());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ((BaseActivity) mContext).closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(mContext).addRequest(jsonRequest);
    }


}
