package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.entity.LocationData;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyBaiDuFootPrintActivity extends AppCompatActivity {

    long userId;
    private BaiduMap mBaiduMap;
    MapView mMapView;
    private Marker mMarker;

    BitmapDescriptor location = BitmapDescriptorFactory
            .fromResource(R.mipmap.zhuji);
    private LatLng ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybaidu_foot_print);
        mMapView = (MapView) findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomBy(-5.0f);
        mBaiduMap.animateMapStatus(msu);
        initView();
    }


    private void initView() {
        Intent intent = getIntent();
        userId = intent.getLongExtra(UserProfiles.EXTRA_ME_OR_HE_USER_ID, 0);
        String url = Constants.HOST +"users/kt_track?user_id=" +
                userId + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("===========", jsonObject.toString());
                        LocationData data = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<LocationData>() {
                                }.getType());
                        Log.d("===========", data.toString());
                        LatLngBounds latLngBounds = null;
                        for (int i = 0; i < data.games.size(); i++) {
                            String[] s = data.games.get(i).location.split(",");
                            Log.d("location", data.games.get(i).location);
                            if (s != null && s.length == 2) {
                                if (Double.parseDouble(s[1]) > 90) {
                                    ll = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                                } else {
                                    ll = new LatLng(Double.parseDouble(s[1]), Double.parseDouble(s[0]));
                                }
                                Log.d("String[]", s[1] + "," + s[0]);

                                MarkerOptions oo = new MarkerOptions().position(ll).icon(location)
                                        .zIndex(9).draggable(true);
                                //掉下动画
                                oo.animateType(MarkerOptions.MarkerAnimateType.drop);
                                mMarker = (Marker) (mBaiduMap.addOverlay(oo));
                                latLngBounds = new LatLngBounds.Builder().include(ll).build();
                            }
                        }
                        if(latLngBounds != null){
                            mBaiduMap.setMapStatusLimits(latLngBounds);
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
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        location.recycle();
    }
}
