package com.ktfootball.app.UI.Fragment.Rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.RankingData;
import com.kt.ktball.entity.Users;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Event.RankEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.RankingListActivity;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/15.
 */

public class StartFragment extends BaseFragment {
    private ImageView mImage1, mImage2, mImage3;
    private TextView mTv1, mTv2, mTv3;
    private GridView mGridView;
    private List<Users> mList = new ArrayList<>();
    private long mUser_id1,mUser_id2,mUser_id3;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_start_list);
        mImage1 = getViewById(R.id.image_first);
        mImage2 = getViewById(R.id.image_second);
        mImage3 = getViewById(R.id.image_third);
        mTv1 = getViewById(R.id.tv_1);
        mTv2 = getViewById(R.id.tv_2);
        mTv3 = getViewById(R.id.tv_3);
        mGridView = getViewById(R.id.gridview);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    private void initView() {
        final String url = Constants.HOST +"users/range1v1_power_top100?user_id=" +
                App.getUserLogin().user_id + "&authenticity_token="+ MD5.getToken(Constants.HOST +"users/range1v1_power_top100");
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")) {
                                mList.clear();
                                RankingData rankingData = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<RankingData>() {
                                        }.getType());
                                ArrayList<Users> data = new ArrayList<>();
                                data = rankingData.users;
                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).user_id == App.getUserLogin().user_id) {
                                        EventBus.getDefault().post(new RankEvent((i + 1) + ""));
                                    }
                                    switch (i) {
                                        case 0:
                                            Glide.with(getThis()).load(Constants.HEAD_HOST+ data.get(i).avatar).error(R.drawable.result_btnkt).into(mImage1);
                                            mTv1.setText(data.get(i).nickname);
                                            mUser_id1 = data.get(i).user_id;
                                            mImage1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    goDetail(mUser_id1);
                                                }
                                            });
                                            break;
                                        case 1:
                                            Glide.with(getThis()).load(Constants.HEAD_HOST + data.get(i).avatar).error(R.drawable.result_btnkt).into(mImage2);
                                            mTv2.setText(data.get(i).nickname);
                                            mUser_id2 = data.get(i).user_id;
                                            mImage2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    goDetail(mUser_id2);
                                                }
                                            });
                                            break;
                                        case 2:
                                            Glide.with(getThis()).load(Constants.HEAD_HOST+ data.get(i).avatar).error(R.drawable.result_btnkt).into(mImage3);
                                            mTv3.setText(data.get(i).nickname);
                                            mUser_id3 = data.get(i).user_id;
                                            mImage3.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    goDetail(mUser_id3);
                                                }
                                            });
                                            break;
                                        default:
                                            mList.add(data.get(i));
                                            break;
                                    }
                                    mGridView.setAdapter(new StartAdapter());


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("=========", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);
    }

    private class StartAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHoder mViewHoder;
            if (convertView == null) {
                mViewHoder = new ViewHoder();
                convertView = LayoutInflater.from(getThis()).inflate(R.layout.item_start_list, null);
                mViewHoder.image = (ImageView) convertView.findViewById(R.id.image);
                mViewHoder.mTv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(mViewHoder);

            } else {
                mViewHoder = (ViewHoder) convertView.getTag();
            }
            Glide.with(getThis()).load(Constants.HEAD_HOST + mList.get(position).avatar).error(R.drawable.result_btnkt).into(mViewHoder.image);
            mViewHoder.mTv.setText("NO." + (position + 4) +" "+ mList.get(position).nickname);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDetail(mList.get(position).user_id);
                }
            });
            return convertView;
        }

        class ViewHoder {
            TextView mTv;
            ImageView image;
        }
    }


    private void goDetail(long user_id){
        Intent intent = new Intent(getThis(),UserProfiles.class);
        intent.putExtra(UserProfiles.USERID,user_id);
        startActivity(intent);
    }
}
