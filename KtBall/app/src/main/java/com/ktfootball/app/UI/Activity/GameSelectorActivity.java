package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.R;

public class GameSelectorActivity extends BaseToolBarActivity2 {

    GamePlace.Games games;
    private TextView one;
    private TextView two;
    private TextView three;
    private String code;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_game_selector);
        one = (TextView) findViewById(R.id.textView39);
        two = (TextView) findViewById(R.id.textView40);
        three = (TextView) findViewById(R.id.textView41);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("赛事选择");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void setListener() {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUserScan(1);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUserScan(2);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUserScan(3);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        games = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
        code = intent.getStringExtra(Constants.QICHANG_CODE);
    }

    private void doUserScan(final int i) {//1v1，2v2,3v3 扫描
        Intent intent = new Intent(GameSelectorActivity.this, KtActivity.class);
        intent.putExtra(Constants.KT_CODE, i);
        intent.putExtra(Constants.GAME, games);
        intent.putExtra(Constants.QICHANG_CODE, code);
        startActivity(intent);
    }
}
