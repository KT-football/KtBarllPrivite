package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Entity.PostLeague;
import com.ktfootball.app.Entity.PostResult;
import com.ktfootball.app.Entity.ScanUser;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

public class GameResultActivity extends BaseActivity {

    @Bind(R.id.textView60)
    TextView textViewLeftGrade;//左分
    @Bind(R.id.textView61)
    TextView textViewRightGrade;//右分

    @Bind(R.id.imageView78)
    ImageView imageViewLeftKT;//左KT
    @Bind(R.id.imageView79)
    ImageView imageViewRightKT;//右KT
    @Bind(R.id.imageView76)
    ImageView imageViewLeftWin;//左赢
    @Bind(R.id.imageView77)
    ImageView imageViewRightWin;//右赢

    @Bind(R.id.textView62)
    TextView textViewLeftUser;//左用户
    @Bind(R.id.textView63)
    TextView textViewRightUser;//右用户
    @Bind(R.id.textView65)
    TextView textViewShang;//上地址
    @Bind(R.id.textView64)
    TextView textViewZhong;//中时间
    @Bind(R.id.textView66)
    TextView textViewXia;//下地址

    @Bind(R.id.view)
    MyCircleImageView view;//下地址
    @Bind(R.id.view2)
    MyCircleImageView view2;//下地址

    @Bind(R.id.activity_game_result_left_1)
    TextView chanceWinningA;//胜率a
    @Bind(R.id.activity_game_result_left_2)
    TextView chanceWinningB;//胜率b
    @Bind(R.id.activity_game_result_left_3)
    TextView chanceWinningC;//胜率c

    @Bind(R.id.activity_game_result_left_user_a_ll)
    LinearLayout chanceWinningA_ll;//胜率a
    @Bind(R.id.activity_game_result_left_user_b_ll)
    LinearLayout chanceWinningB_ll;//胜率b
    @Bind(R.id.activity_game_result_left_user_c_ll)
    LinearLayout chanceWinningC_ll;//胜率c

    @Bind(R.id.activity_game_result_left_user_a)
    TextView userA_zdl;
    @Bind(R.id.activity_game_result_left_user_b)
    TextView userB_zdl;
    @Bind(R.id.activity_game_result_left_user_c)
    TextView userC_zdl;

    @Bind(R.id.activity_game_result_left_user_a_num)
    TextView userA_zdl_num;
    @Bind(R.id.activity_game_result_left_user_b_num)
    TextView userB_zdl_num;
    @Bind(R.id.activity_game_result_left_user_c_num)
    TextView userC_zdl_num;

    @Bind(R.id.activity_game_result_left_4)
    TextView chanceWinningD;//胜率a
    @Bind(R.id.activity_game_result_left_5)
    TextView chanceWinningE;//胜率b
    @Bind(R.id.activity_game_result_left_6)
    TextView chanceWinningF;//胜率c

    @Bind(R.id.activity_game_result_left_user_d_ll)
    LinearLayout chanceWinningD_ll;//胜率a
    @Bind(R.id.activity_game_result_left_user_e_ll)
    LinearLayout chanceWinningE_ll;//胜率b
    @Bind(R.id.activity_game_result_left_user_f_ll)
    LinearLayout chanceWinningF_ll;//胜率c

    @Bind(R.id.activity_game_result_left_user_d)
    TextView userD_zdl;
    @Bind(R.id.activity_game_result_left_user_e)
    TextView userE_zdl;
    @Bind(R.id.activity_game_result_left_user_f)
    TextView userF_zdl;

    @Bind(R.id.activity_game_result_left_user_d_num)
    TextView userD_zdl_num;
    @Bind(R.id.activity_game_result_left_user_e_num)
    TextView userE_zdl_num;
    @Bind(R.id.activity_game_result_left_user_f_num)
    TextView userF_zdl_num;


    private ScanUser userA;
    private ScanUser userB;
    private ScanUser userC;
    private ScanUser userD;
    private ScanUser userE;
    private ScanUser userF;
    private PostLeague league1;
    private PostLeague league2;
    private int team;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int ktCode = intent.getIntExtra(Constants.KT_CODE, 0);
//        String leftUser = intent.getStringExtra(Constants.LEFT_USER);
//        String rightUser = intent.getStringExtra(Constants.RIGHT_USER);
        String leftUser = "";
        String rightUser = "";
        int leftGrade = intent.getIntExtra(Constants.LEFT_GRADE, 0);
        int rightGrade = intent.getIntExtra(Constants.RIGHT_GRADE, 0);
        GamePlace.Games games = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
        PostResult postResult = (PostResult) intent.getSerializableExtra(Constants.POSTRESULT_BEAN);
//        showDialogToast(postResult.user1_power+"..."+postResult.user2_power+"..."+postResult.user3_power+"..."+
//                postResult.user4_power+"..."+postResult.user5_power+"..."+postResult.user6_power+"...");
        if(league1 != null){
            BitmapManager.getInstance().displayUserLogo(view, App.getImageUrl(league1.league_avatar));
        }else if(userA != null){
            BitmapManager.getInstance().displayUserLogo(view, App.getImageUrl(userA.avatar));
        }
        if(league2 != null){
            BitmapManager.getInstance().displayUserLogo(view, App.getImageUrl(league2.league_avatar));
        }else if(userF != null){
            BitmapManager.getInstance().displayUserLogo(view, App.getImageUrl(userF.avatar));
        }
        userA = (ScanUser) getIntent().getSerializableExtra(Constants.USER_A);
        userB = (ScanUser) getIntent().getSerializableExtra(Constants.USER_B);
        userC = (ScanUser) getIntent().getSerializableExtra(Constants.USER_C);
        userD = (ScanUser) getIntent().getSerializableExtra(Constants.USER_D);
        userE = (ScanUser) getIntent().getSerializableExtra(Constants.USER_E);
        userF = (ScanUser) getIntent().getSerializableExtra(Constants.USER_F);
        league1 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE1);
        league2 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE2);
        team = intent.getIntExtra(Constants.USER_TEAM, 0);
        textViewLeftGrade.setText(String.valueOf(leftGrade));
        textViewRightGrade.setText(String.valueOf(rightGrade));
        switch (ktCode) {
            case 1:
                imageViewLeftKT.setVisibility(View.VISIBLE);
                imageViewLeftWin.setVisibility(View.VISIBLE);
                break;
            case 2:
                imageViewRightKT.setVisibility(View.VISIBLE);
                imageViewRightWin.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (team == 1) {
            leftUser = userA.nickname;
            rightUser = userF.nickname;

            chanceWinningA.setText(postResult.user1_winrate + "%");
            chanceWinningA_ll.setVisibility(View.VISIBLE);
            chanceWinningB_ll.setVisibility(View.INVISIBLE);
            chanceWinningC_ll.setVisibility(View.INVISIBLE);
            userA_zdl.setText(getString(R.string.fighting_capacity)+" " + postResult.user1_power);
            if(postResult.user1_change_power <0){
                userA_zdl_num.setTextColor(Color.GREEN);
            }else{
                userA_zdl_num.setTextColor(Color.RED);
            }
            userA_zdl_num.setText("(" +postResult.user1_change_power +")");

            chanceWinningD.setText(postResult.user2_winrate + "%");
            chanceWinningD_ll.setVisibility(View.VISIBLE);
            chanceWinningE_ll.setVisibility(View.INVISIBLE);
            chanceWinningF_ll.setVisibility(View.INVISIBLE);
            userD_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user2_power);
            if(postResult.user2_change_power <0){
                userD_zdl_num.setTextColor(Color.GREEN);
            }else{
                userD_zdl_num.setTextColor(Color.RED);
            }
            userD_zdl_num.setText("(" +postResult.user2_change_power +")");
        } else if (team == 2) {
            leftUser = league1.league_name;
            rightUser = league2.league_name;
            chanceWinningA.setText(postResult.user1_winrate + "%");
            chanceWinningB.setText("," + postResult.user2_winrate + "%");
            chanceWinningA_ll.setVisibility(View.VISIBLE);
            chanceWinningB_ll.setVisibility(View.VISIBLE);
            chanceWinningC_ll.setVisibility(View.INVISIBLE);
            userA_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user1_power);
            userB_zdl.setText(" "+getString(R.string.fighting_capacity)+" "  + postResult.user2_power);
            if(postResult.user1_change_power <0){
                userA_zdl_num.setTextColor(Color.GREEN);
            }else{
                userA_zdl_num.setTextColor(Color.RED);
            }
            userA_zdl_num.setText("(" +postResult.user1_change_power +")");
            if(postResult.user2_change_power <0){
                userB_zdl_num.setTextColor(Color.GREEN);
            }else{
                userB_zdl_num.setTextColor(Color.RED);
            }
            userB_zdl_num.setText("(" +postResult.user2_change_power +")");

            chanceWinningD.setText(postResult.user3_winrate + "%");
            chanceWinningE.setText("," + postResult.user4_winrate + "%");
            chanceWinningD_ll.setVisibility(View.VISIBLE);
            chanceWinningE_ll.setVisibility(View.VISIBLE);
            chanceWinningF_ll.setVisibility(View.INVISIBLE);
            userD_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user3_power);
            userE_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user4_power);
            if(postResult.user3_change_power <0){
                userD_zdl_num.setTextColor(Color.GREEN);
            }else{
                userD_zdl_num.setTextColor(Color.RED);
            }
            userD_zdl_num.setText("(" +postResult.user3_change_power +")");
            if(postResult.user4_change_power <0){
                userE_zdl_num.setTextColor(Color.GREEN);
            }else{
                userE_zdl_num.setTextColor(Color.RED);
            }
            userE_zdl_num.setText("(" +postResult.user4_change_power +")");
        } else {
            leftUser = league1.league_name;
            rightUser = league2.league_name;
            chanceWinningA.setText(postResult.user1_winrate + "%");
            chanceWinningB.setText("," + postResult.user2_winrate + "%");
            chanceWinningC.setText("," + postResult.user3_winrate + "%");
            chanceWinningA_ll.setVisibility(View.VISIBLE);
            chanceWinningB_ll.setVisibility(View.VISIBLE);
            chanceWinningC_ll.setVisibility(View.VISIBLE);
            userA_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user1_power);
            userB_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user2_power);
            userC_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user3_power);
            if(postResult.user1_change_power <0){
                userA_zdl_num.setTextColor(Color.GREEN);
            }else{
                userA_zdl_num.setTextColor(Color.RED);
            }
            userA_zdl_num.setText("(" +postResult.user1_change_power +")");
            if(postResult.user2_change_power <0){
                userB_zdl_num.setTextColor(Color.GREEN);
            }else{
                userB_zdl_num.setTextColor(Color.RED);
            }
            userB_zdl_num.setText("(" +postResult.user2_change_power +")");
            if(postResult.user3_change_power <0){
                userC_zdl_num.setTextColor(Color.GREEN);
            }else{
                userC_zdl_num.setTextColor(Color.RED);
            }
            userC_zdl_num.setText("(" +postResult.user3_change_power +")");

            chanceWinningD.setText(postResult.user4_winrate + "%");
            chanceWinningE.setText("," + postResult.user5_winrate + "%");
            chanceWinningF.setText("," + postResult.user6_winrate + "%");
            chanceWinningF_ll.setVisibility(View.VISIBLE);
            chanceWinningD_ll.setVisibility(View.VISIBLE);
            chanceWinningE_ll.setVisibility(View.VISIBLE);
            userF_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user6_power);
            userD_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user5_power);
            userE_zdl.setText(getString(R.string.fighting_capacity)+" "  + postResult.user4_power);
            if(postResult.user6_change_power <0){
                userF_zdl_num.setTextColor(Color.GREEN);
            }else{
                userF_zdl_num.setTextColor(Color.RED);
            }
            userF_zdl_num.setText("(" +postResult.user6_change_power +")");
            if(postResult.user5_change_power <0){
                userE_zdl_num.setTextColor(Color.GREEN);
            }else{
                userE_zdl_num.setTextColor(Color.RED);
            }
            userE_zdl_num.setText("(" +postResult.user5_change_power +")");
            if(postResult.user4_change_power <0){
                userD_zdl_num.setTextColor(Color.GREEN);
            }else{
                userD_zdl_num.setTextColor(Color.RED);
            }
            userD_zdl_num.setText("(" +postResult.user4_change_power +")");
        }
        textViewLeftUser.setText(leftUser);
        textViewRightUser.setText(rightUser);
        String gamePlace = games.name;
        textViewShang.setText(gamePlace);
        textViewXia.setText(gamePlace);
        textViewZhong.setText(refFormatNowDate());
    }

    public String refFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("MM-dd HH:mm");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    public void doBack(View view) {
        finish();
    }
}
