package com.ktfootball.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AddToUserAppCartoons;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.AddToUserAppCartoonsRequest;
import com.ktfootball.app.UI.Activity.train.TrainDetailsActivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/18.
 */
public class ItemAddClass extends RecyclerView.Adapter<ItemAddClass.ViewHolder> {
    private List<AppCartoons.Cartoons> mList = new ArrayList<>();
    private Context mContext;

    public ItemAddClass(Context mContext, List<AppCartoons.Cartoons> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_add_class, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTitle.setText(mList.get(position).name);
        if (mList.get(position).is_added.equals("1")) {
            holder.mCheck.setImageResource(R.mipmap.is_selected);
        } else {
            holder.mCheck.setImageResource(R.mipmap.no_selected);
            holder.mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToUserAppCartoons(mList.get(position),position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private ImageView mCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mCheck = (ImageView) itemView.findViewById(R.id.image_check);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TrainDetailsActivity.class);
                    intent.putExtra(Constants.APP_CARTOON_ID,mList.get(getPosition()).id);
                    intent.putExtra(Constants.SUB_NAME,mList.get(getPosition()).sub_name);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void addToUserAppCartoons(AppCartoons.Cartoons appCartoon, final int position) {
        ((BaseActivity)mContext).showLoadingDiaglog();
        final com.yolanda.nohttp.rest.Request<AddToUserAppCartoons> request = new AddToUserAppCartoonsRequest(Constants.ADD_TO_USER_APP_CARTOONS, RequestMethod.POST);
        request.add("user_id", App.getUserLogin().user_id);
        request.add("app_cartoon_id", appCartoon.id);
        CallServer.getRequestInstance().add((BaseActivity) mContext, 0, request, new HttpListener<AddToUserAppCartoons>() {
            @Override
            public void onSucceed(int what, Response<AddToUserAppCartoons> response) {
                if (((BaseActivity)mContext).isSuccess(response.get().response)) {
                    mList.get(position).is_added = "1";
                    notifyDataSetChanged();
                }
                ((BaseActivity)mContext).closeLoadingDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                ((BaseActivity)mContext).closeLoadingDialog();
            }
        }, false, false);
    }
}
