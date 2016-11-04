package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.entity.EditGameData;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Utils.MD5;
import com.ktfootball.app.Views.SharedDialog;
import com.ktfootball.app.R;

import org.json.JSONObject;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;

public class GamesDetailsActivity extends BaseActivity {

    public static final String EXTRA_LOCATION = "location";
    @Bind(R.id.textView117)
    TextView textViewName;
    @Bind(R.id.imageView36)
    ImageView imageView;
    @Bind(R.id.textView119)
    TextView textViewJieShao;
    @Bind(R.id.textView122)
    TextView textViewTime;
    @Bind(R.id.textView125)
    TextView textViewPlace;
    @Bind(R.id.textView128)
    TextView textViewMoney;

    long gameId;
    long userId;
    String location;
    private SharedDialog sharedDialog;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_games_details);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        initView();
    }

    private void initView() {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        Intent intent = getIntent();
        gameId = intent.getLongExtra(GameMatchActivity.EXTRA_GAME_ID, 0);
        String url = Constants.HOST +"games/edit?game_id="
                + gameId + "&user_id=" + userId +
                "&authenticity_token="+ MD5.getToken(Constants.HOST+"games/edit");
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Log.d("=========", jsonObject.toString());
                        EditGameData editGameData = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<EditGameData>() {
                                }.getType());
                        Log.d("=========", editGameData.toString());
                        init(editGameData);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("=========", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    private void init(EditGameData editGameData) {
        textViewName.setText(editGameData.name);
        textViewJieShao.setText(editGameData.introduction.isEmpty() ? getString(R.string.no_jiesao) : editGameData.introduction);
        textViewTime.setText(editGameData.date_start + getString(R.string.to)+ editGameData.date_end);
        textViewPlace.setText(editGameData.place);
        textViewMoney.setText("$ " + editGameData.enter_ktb + ".00");
        location = editGameData.location;
        String uri = Constants.HEAD_HOST + editGameData.avatar;
        BitmapManager.getInstance().displayUserLogo(imageView, uri);
    }

    public void doFenXing(View view) {//分享
        showDialog();
    }

    public void doAddress(View view) {//点击查看地址
        if (location == null) {
//            Toast.makeText(this, "经纬度为空", Toast.LENGTH_SHORT).show();
        } else {
//            Intent intent = new Intent(this,LookOverAddressActivity.class);
//            intent.putExtra(EXTRA_LOCATION,location);
//            startActivity(intent);
        }
    }

    public void doBack(View view) {
        finish();
    }

    private void showDialog() {
        if (sharedDialog == null) {
            sharedDialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            sharedDialog.setTitleUrl(Constants.HOST + "app_share/game?game_id=" + gameId);
            String title = getString(R.string.signed_up) + textViewTime.getText().toString() + textViewPlace.getText().toString();
            sharedDialog.setTitle(title);
            sharedDialog.setText(getString(R.string.kt_game_top));
        }
        sharedDialog.show();
        dimActivity(sharedDialog, 0.6f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
