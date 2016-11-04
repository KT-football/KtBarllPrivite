package com.ktfootball.app.UI.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.adapter.GameMatchAdapter;
import com.kt.ktball.entity.GamePlace;
import com.kt.ktball.entity.Games;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.BlockBook.MyOrderActivity;
import com.ktfootball.app.UI.Activity.GamesDetailsActivity;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.Utils.MD5;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by leo on 16/10/13.
 */

public class GameMatchFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String EXTRA_GAME_ID = "game_id";
    private double latitude;//纬度
    private double longitude;//经度
    long userId;
    MyListView listView1;
    MyListView listView2;
    GameMatchAdapter gameMatchAdapter1;
    GameMatchAdapter gameMatchAdapter2;
    private TextView baocahng;
    private BGARefreshLayout mRefreshLayout;
    private String url;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game_match);
        initRefreshLayout();
        listView1 = getViewById(R.id.listView9);
        listView2 = getViewById(R.id.listView11);
        baocahng = getViewById(R.id.activity_game_match_to_baochang);
    }

    private void initRefreshLayout() {
        mRefreshLayout = getViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new MyBGARefreshViewHolder(getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }


    @Override
    protected void setListener() {
        baocahng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        openGPSSettings();//打开GPS定位
    }

    @Override
    protected void onUserVisible() {

    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            getLocation();//获取经纬度
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.open_gps));
            builder.setPositiveButton(getString(R.string.right), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                    startActivityForResult(intent, 0);
                }
            });
            builder.setNegativeButton(getString(R.string.cancle), null);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * 获取位置
     */
    private void getLocation() {
        mLocationClient = new LocationClient(getThis());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }


    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);

    }

    /**
     * 定位监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            userId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
            mRefreshLayout.beginRefreshing();
        }

    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        url = Constants.HOST + "games/list?user_id=" +
                userId + "&lon=" + longitude + "&lat=" + latitude +
                "&authenticity_token=" + MD5.getToken(Constants.HOST + "games/list");
        initData();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }




    private void initData(){
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        mRefreshLayout.endRefreshing();
                        GamePlace gamePlace = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<GamePlace>() {
                                }.getType());
                        ArrayList<Games> data = gamePlace.games;
                        final ArrayList<Games> data1 = new ArrayList<>();
                        data1.add(data.get(0));
                        ArrayList<Games> data2 = new ArrayList<>();
                        for (int i =  1; i < data.size(); i++) {
                            data2.add(data.get(i));
                        }
                        gameMatchAdapter1 = new GameMatchAdapter(data1, getActivity());
                        listView1.setAdapter(gameMatchAdapter1);
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                long gameId = data1.get(0).id;
                                Intent intent = new Intent(getActivity(), GamesDetailsActivity.class);
                                intent.putExtra(EXTRA_GAME_ID, gameId);
                                startActivity(intent);
                            }
                        });
                        gameMatchAdapter2 = new GameMatchAdapter(data2, getActivity());
                        listView2.setAdapter(gameMatchAdapter2);
                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Games games = gameMatchAdapter2.getItem(position);
                                long gameId = games.id;
                                Intent intent = new Intent(getActivity(), GamesDetailsActivity.class);
                                intent.putExtra(EXTRA_GAME_ID, gameId);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                mRefreshLayout.endRefreshing();
            }
        }
        );
        VolleyUtil.getInstance(getActivity()).addRequest(jsonRequest);
    }


}
