package com.ktfootball.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MyMsgBattleBean;
import com.ktfootball.app.Entity.MyMsgTeamBean;
import com.ktfootball.app.Event.SystemBattleMsgEvent;
import com.ktfootball.app.Event.SystemTeamMsgEvent;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/20.
 */
public class SystemBattleMsgItem extends RecyclerView.Adapter<SystemBattleMsgItem.SystemView> {
    private Context mContext;
    private List<MyMsgBattleBean.BattleInvitationsBean> mList;

    public SystemBattleMsgItem(Context mContext, List<MyMsgBattleBean.BattleInvitationsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SystemBattleMsgItem.SystemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SystemBattleMsgItem.SystemView(LayoutInflater.from(mContext).inflate(R.layout.item_team_msg, null));
    }

    @Override
    public void onBindViewHolder(SystemBattleMsgItem.SystemView holder, final int position) {
        Glide.with(mContext).load(Constants.HOST + mList.get(position).getAvatar()).transform(new GlideCircleTransform(mContext)).into(holder.mHead);
        holder.mName.setText(mList.get(position).getNickname());
        holder.mStatus.setText(mList.get(position).getGame_name());
        holder.mAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeMessage(position);
            }
        });
        holder.mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refuseMessage(position);
            }
        });
//        if (mList.get(position).getLeague_invitation_status() == -1) {
//            holder.mAgree.setVisibility(View.VISIBLE);
//            holder.mCancle.setVisibility(View.VISIBLE);
//            holder.tv_status1.setVisibility(View.GONE);
//        } else if (mList.get(position).getLeague_invitation_status() == 0) {
//            holder.mAgree.setVisibility(View.GONE);
//            holder.mCancle.setVisibility(View.GONE);
//            holder.tv_status1.setText("已拒绝");
//            holder.tv_status1.setVisibility(View.VISIBLE);
//        } else {
//            holder.mAgree.setVisibility(View.GONE);
//            holder.mCancle.setVisibility(View.GONE);
//            holder.tv_status1.setText("已接受");
//            holder.tv_status1.setTextColor(mContext.getResources().getColor(R.color.gold));
//            holder.tv_status1.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SystemView extends RecyclerView.ViewHolder {
        private TextView mCancle, mAgree, tv_status1;
        private ImageView mHead;
        private TextView mName, mStatus;

        public SystemView(View itemView) {
            super(itemView);
            mAgree = (TextView) itemView.findViewById(R.id.tv_agree);
            mCancle = (TextView) itemView.findViewById(R.id.tv_cancle);
            mStatus = (TextView) itemView.findViewById(R.id.tv_status);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mHead = (ImageView) itemView.findViewById(R.id.image_head);
            tv_status1 = (TextView) itemView.findViewById(R.id.tv_status1);
        }
    }


    /**
     * 统一消息
     */
    private void agreeMessage(final int position) {
        ((BaseActivity) mContext).showLoadingDiaglog();
        String url = Constants.USER_BATTLE_AGREE_MESSAGE + "?battle_invitation_id="
                + mList.get(position).getBattle_invitation_id() + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
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
                                EventBus.getDefault().post(new SystemBattleMsgEvent());
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


    /**
     * 拒絕消息
     */
    private void refuseMessage(final int position) {
        ((BaseActivity) mContext).showLoadingDiaglog();
        String url = Constants.USER_BATTLE_DISAAGREE_MESSAGE + "?battle_invitation_id="
                + mList.get(position).getBattle_invitation_id() + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
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
                               EventBus.getDefault().post(new SystemBattleMsgEvent());
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
