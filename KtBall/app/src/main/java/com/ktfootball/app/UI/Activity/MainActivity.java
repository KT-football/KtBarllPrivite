package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.ktfootball.app.UI.Activity.Main.SuperStarActivity;
import com.ktfootball.app.UI.Activity.train.TrainActivity;
import com.kt.ktball.views.IRoundBallViewScrollListener;
import com.kt.ktball.views.RoundBallView;
import com.ktfootball.app.R;

public class MainActivity extends BaseActivity implements IRoundBallViewScrollListener {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_ball);
        RoundBallView img = (RoundBallView) findViewById(R.id.layout_ball);
        if(App.getUserLogin().is_star){
            img.setModel(R.drawable.ball2);
            img.invalidate();
        }
//        showDialogToast(App.getUserLogin().is_star+"");
        img.setScrollListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void startActivity(int code) {
        Intent intent = null;
        switch (code) {
            case 0:
                intent = new Intent(this,GameMatchActivity.class);
                break;
            case 1:
                intent = new Intent(this,SuperStarActivity.class);
                break;
            case 2:
                long userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
                intent = new Intent(this,UserProfiles.class);
                intent.putExtra(UserProfiles.USERID,userId);
                break;
            case 3:
//                intent = null;
                intent = new Intent(getThis(),TrainActivity.class);
//                intent = new Intent(getThis(),TrainActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
