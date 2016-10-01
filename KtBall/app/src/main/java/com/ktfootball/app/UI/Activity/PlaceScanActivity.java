package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.kt.ktball.App;
import com.ktfootball.app.Base.BaseEntity;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.BaseRequest;
import com.ktfootball.app.zxing.android.CaptureActivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

public class PlaceScanActivity extends BaseToolBarActivity2 {

    private GamePlace.Games games;
    private TextView scan;
    private String code;


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_place_scan);
        scan = (TextView) findViewById(R.id.textView27);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("比赛地点");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),
                        CaptureActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        games = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_SCAN && resultCode == Constants.CAPTUREACTIVITY_RESULT_OK) {
            if (data != null) {
                //取值
                Bundle bundle = data.getBundleExtra("bundle");
                code = bundle.getString(Constants.DECODED_CONTENT_KEY);
                if ("".equals(code)) {
                    return;
                } else {
                    doBagTrace(code);
                }
            }
        }
    }

    private void doBagTrace(final String code) {
        showLoadingDiaglog();
        BaseRequest request = new BaseRequest(Constants.BAG_TRACE, RequestMethod.POST);
        request.add("user_id", App.getUserLogin().user_id + "");
        request.add("game_id", games.id);
        request.add("code", code);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, Response<BaseEntity> response) {
                if (response.get().response.equals("success")) {
                    Intent intent = new Intent(PlaceScanActivity.this, GameSelectorActivity.class);
                    intent.putExtra(Constants.GAME, games);
                    intent.putExtra(Constants.QICHANG_CODE, code);
                    startActivity(intent);
                } else {
                    showDialogToast(response.get().msg);
                }
                closeLoadingDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }
}
