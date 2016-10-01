package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

//import com.amap.api.maps.AMap;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.MarkerOptions;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ktfootball.app.R;
import com.kt.ktball.entity.LocationData;
import com.kt.ktball.myclass.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFootPrintActivity extends AppCompatActivity {

    long userId;
//    MapView m;
//    AMap a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot_print);
//        m= (MapView) findViewById(R.id.mapView);
//        a=m.getMap();
//        m.onCreate(savedInstanceState);
        initView();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        m.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        m.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        m.onPause();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        m.onLowMemory();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        m.onDestroy();
//    }

    private void initView() {
        Intent intent = getIntent();
        userId = intent.getLongExtra(UserProfiles.EXTRA_ME_OR_HE_USER_ID, 0);
        String url = "http://www.ktfootball.com/apiv2/users/kt_track?user_id="+
                userId + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("===========",jsonObject.toString());
                        LocationData data = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<LocationData>(){}.getType());
                        Log.d("===========",data.toString());

//                        ArrayList<MarkerOptions> options=new ArrayList<>();
//
//                        for (int i=0;i<data.games.size();i++){
//                            String[] s=data.games.get(i).location.split(",");
//                            Log.d("location",data.games.get(i).location);
//                                if (s!=null &&s.length==2){
//                                    LatLng ll;
//                                    if (Double.parseDouble(s[1]) > 90){
//                                        ll=new LatLng(Double.parseDouble(s[0]),Double.parseDouble(s[1]));
//                                    } else {
//                                        ll = new LatLng(Double.parseDouble(s[1]), Double.parseDouble(s[0]));
//                                    }
//                                    Log.d("String[]", s[1]+","+s[0]);
//                                MarkerOptions temp=new MarkerOptions()
////                                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(R.drawable.location)))
//                                        .position(ll);
//                                options.add(temp);
//                                }
//                        }
//
//                        //true 表示是否移到屏幕中央
//                        a.addMarkers(options,true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
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
}
