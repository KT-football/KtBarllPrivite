package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.kt.ktball.db.SideAandBDaoHelper;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Entity.PostLeague;
import com.ktfootball.app.Entity.PostResult;
import com.ktfootball.app.Entity.ScanUser;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.PostResultRequest;
import com.ktfootball.www.dao.SideAandB;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.File;

import butterknife.Bind;

public class UploadingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.textView40)
    TextView textViewA;//第1个头像和名称
    @Bind(R.id.textView41)
    TextView textViewB;//第2个头像和名称
    @Bind(R.id.textView42)
    TextView textViewC;//第3个头像和名称
    @Bind(R.id.textView43)
    TextView textViewD;//第4个头像和名称
    @Bind(R.id.textView44)
    TextView textViewE;//第5个头像和名称
    @Bind(R.id.textView45)
    TextView textViewF;//第6个头像和名称

    @Bind(R.id.imageView57)
    ImageView addLeftBall;//左加
    @Bind(R.id.imageView58)
    ImageView addLeftPass;
    @Bind(R.id.imageView59)
    ImageView addLeftX;
    @Bind(R.id.imageView63)
    ImageView minusLeftBall;//左减
    @Bind(R.id.imageView64)
    ImageView minusLeftPass;
    @Bind(R.id.imageView65)
    ImageView minusLeftX;
    @Bind(R.id.imageView66)
    ImageView addRightBall;//右加
    @Bind(R.id.imageView67)
    ImageView addRightPass;
    @Bind(R.id.imageView68)
    ImageView addRightX;
    @Bind(R.id.imageView72)
    ImageView minusRightBall;//右减
    @Bind(R.id.imageView73)
    ImageView minusRihgtPass;
    @Bind(R.id.imageView74)
    ImageView minusRightX;

    @Bind(R.id.textView54)
    TextView leftBallTextView;//左计分
    @Bind(R.id.textView55)
    TextView leftPassTextView;
    @Bind(R.id.textView56)
    TextView leftXTextView;
    @Bind(R.id.textView57)
    TextView rightBallTextView;//右计分
    @Bind(R.id.textView58)
    TextView rightPassTextView;
    @Bind(R.id.textView59)
    TextView rightXTextView;

    @Bind(R.id.imageView53)
    ImageView leftKtImageView;//左KT键
    @Bind(R.id.imageView54)
    ImageView rightKtImageView;//右KT键

    @Bind(R.id.imageView55)
    ImageView submitImageView;//提交比分
    @Bind(R.id.imageView56)
    ImageView noSubmitImageView;//放弃提交比分

    @Bind(R.id.imageView2)
    MyCircleImageView imageViewAA;
    @Bind(R.id.imageView81)
    MyCircleImageView imageViewBB;
    @Bind(R.id.imageView82)
    MyCircleImageView imageViewCC;
    @Bind(R.id.imageView85)
    MyCircleImageView imageViewDD;
    @Bind(R.id.imageView84)
    MyCircleImageView imageViewEE;
    @Bind(R.id.imageView83)
    MyCircleImageView imageViewFF;

    boolean leftWin = false;
    boolean rightWin = false;

    String path;//SD卡根目录
    private GamePlace.Games games;
    private String code;
    private String picturePath;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_uploading);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        path = Environment.getExternalStorageDirectory().toString();
        userA = (ScanUser) getIntent().getSerializableExtra(Constants.USER_A);
        userB = (ScanUser) getIntent().getSerializableExtra(Constants.USER_B);
        userC = (ScanUser) getIntent().getSerializableExtra(Constants.USER_C);
        userD = (ScanUser) getIntent().getSerializableExtra(Constants.USER_D);
        userE = (ScanUser) getIntent().getSerializableExtra(Constants.USER_E);
        userF = (ScanUser) getIntent().getSerializableExtra(Constants.USER_F);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initViewClik();
    }

    private void initViewClik() {
        addLeftBall.setOnClickListener(this);
        addLeftPass.setOnClickListener(this);
        addLeftX.setOnClickListener(this);
        minusLeftBall.setOnClickListener(this);
        minusLeftPass.setOnClickListener(this);
        minusLeftX.setOnClickListener(this);
        addRightBall.setOnClickListener(this);
        addRightPass.setOnClickListener(this);
        addRightX.setOnClickListener(this);
        minusRightBall.setOnClickListener(this);
        minusRihgtPass.setOnClickListener(this);
        minusRightX.setOnClickListener(this);
        leftKtImageView.setOnClickListener(this);
        rightKtImageView.setOnClickListener(this);
        submitImageView.setOnClickListener(this);
        noSubmitImageView.setOnClickListener(this);
    }

    private ScanUser userA;
    private ScanUser userB;
    private ScanUser userC;
    private ScanUser userD;
    private ScanUser userE;
    private ScanUser userF;
    private PostLeague league1;
    private PostLeague league2;

    String leftBall;
    String leftPall;
    String leftX;
    String rightBall;
    String rightPass;
    String rightX;

    int lBall;
    int lPass;
    int lX;
    int rBall;
    int rPass;
    int rX;

    Intent intent;
    int team;
    String VCRpath;
    private String battle_id;

    private void initView() {//初始化视图
        intent = getIntent();
        team = intent.getIntExtra(Constants.USER_TEAM, 0);
        int ktCode = intent.getIntExtra(Constants.KT_CODE, 0);
        picturePath = intent.getStringExtra(Constants.PICTUREPATH);
        VCRpath = intent.getStringExtra(Constants.VCR_PATH);
        league1 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE1);
        league2 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE2);
        games = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
        code = intent.getStringExtra(Constants.QICHANG_CODE);
        battle_id = intent.getStringExtra(Constants.BATTLE_ID);
//        if (league1 == null || league2 == null || games == null) {
//            showDialogToast("aaaa");
//        }
        switch (ktCode) {
            case 1://左KT
                leftKtImageView.setImageResource(R.drawable.score_kt2x);
                leftWin = true;
                break;
            case 2://右KT
                rightKtImageView.setImageResource(R.drawable.score_kt2x);
                rightWin = true;
                break;
            default:
                break;
        }

        leftBall = intent.getStringExtra(Constants.LEFT_BALL_GRADE);
        leftPall = intent.getStringExtra(Constants.LEFT_PASS_GRADE);
        leftX = intent.getStringExtra(Constants.LEFT_X_GRADE);
        rightBall = intent.getStringExtra(Constants.RIGHT_BALL_GRADE);
        rightPass = intent.getStringExtra(Constants.RIGHT_PASS_GRADE);
        rightX = intent.getStringExtra(Constants.RIGHT_X_GRADE);

        lBall = Integer.parseInt(leftBall);
        lPass = Integer.parseInt(leftPall);
        lX = Integer.parseInt(leftX);
        rBall = Integer.parseInt(rightBall);
        rPass = Integer.parseInt(rightPass);
        rX = Integer.parseInt(rightX);

        leftBallTextView.setText(leftBall);
        leftPassTextView.setText(leftPall);
        leftXTextView.setText(leftX);
        rightBallTextView.setText(rightBall);
        rightPassTextView.setText(rightPass);
        rightXTextView.setText(rightX);

        textViewA.setText(userA.nickname);
        textViewF.setText(userF.nickname);
        BitmapManager.getInstance().displayUserLogo(imageViewAA, Constants.HOST + userA.avatar);
        BitmapManager.getInstance().displayUserLogo(imageViewFF, Constants.HOST + userF.avatar);
        switch (team) {
            case 1:
                break;
            case 2:
                textViewB.setText(userB.nickname);
                textViewE.setText(userE.nickname);
                BitmapManager.getInstance().displayUserLogo(imageViewBB, Constants.HOST + userB.avatar);
                BitmapManager.getInstance().displayUserLogo(imageViewEE, Constants.HOST + userE.avatar);
                break;
            case 3:
                textViewC.setText(userC.nickname);
                textViewD.setText(userD.nickname);
                textViewB.setText(userB.nickname);
                textViewE.setText(userE.nickname);
                BitmapManager.getInstance().displayUserLogo(imageViewBB, Constants.HOST + userB.avatar);
                BitmapManager.getInstance().displayUserLogo(imageViewEE, Constants.HOST + userE.avatar);
                BitmapManager.getInstance().displayUserLogo(imageViewCC, Constants.HOST + userC.avatar);
                BitmapManager.getInstance().displayUserLogo(imageViewDD, Constants.HOST + userD.avatar);
                break;
        }
    }

    String user_a;
    String user_b;
    int grade_a;
    int grade_b;
    int leftBallAddPass;
    int rightBallAddPass;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView57://左Ball加
                lBall += 1;
                leftBallTextView.setText(String.valueOf(lBall));
                break;
            case R.id.imageView58://左Pass加
                lPass += 1;
                leftPassTextView.setText(String.valueOf(lPass));
                break;
            case R.id.imageView59://左X加
                lX += 1;
                leftXTextView.setText(String.valueOf(lX));
                break;
            case R.id.imageView63://左Ball减
                if (lBall > 0) {
                    lBall -= 1;
                    leftBallTextView.setText(String.valueOf(lBall));
                }
                break;
            case R.id.imageView64://左Pall减
                if (lPass > 0) {
                    lPass -= 1;
                    leftPassTextView.setText(String.valueOf(lPass));
                }
                break;
            case R.id.imageView65://左X减
                if (lX > 0) {
                    lX -= 1;
                    leftXTextView.setText(String.valueOf(lX));
                }
                break;
            case R.id.imageView66://右Ball加
                rBall += 1;
                rightBallTextView.setText(String.valueOf(rBall));
                break;
            case R.id.imageView67://右Pass加
                rPass += 1;
                rightPassTextView.setText(String.valueOf(rPass));
                break;
            case R.id.imageView68://右X加
                rX += 1;
                rightXTextView.setText(String.valueOf(rX));
                break;
            case R.id.imageView72://右Ball减
                if (rBall > 0) {
                    rBall -= 1;
                    rightBallTextView.setText(String.valueOf(rBall));
                }
                break;
            case R.id.imageView73://右Pass减
                if (rPass > 0) {
                    rPass -= 1;
                    rightPassTextView.setText(String.valueOf(rPass));
                }
                break;
            case R.id.imageView74://右X减
                if (rX > 0) {
                    rX -= 1;
                    rightXTextView.setText(String.valueOf(rX));
                }
                break;
            case R.id.imageView53://左KT键
                leftWin = !leftWin;
                leftKtImageView.setImageResource(leftWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                rightWin = !leftWin;
                rightKtImageView.setImageResource(rightWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                break;
            case R.id.imageView54://右KT键
                rightWin = !rightWin;
                rightKtImageView.setImageResource(rightWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                leftWin = !rightWin;
                leftKtImageView.setImageResource(leftWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                break;
            case R.id.imageView55://提交比分
                submitImageView.setClickable(false);

                switch (team) {
                    case 1:
                        user_a = userA.user_id + "";
                        user_b = userF.user_id + "";
                        break;
                    case 2:
                        user_a = userA.user_id + "|" + userB.user_id;
                        user_b = userF.user_id + "|" + userE.user_id;
                        break;
                    case 3:
                        user_a = userA.user_id + "|" + userB.user_id + "|" + userC.user_id;
                        user_b = userF.user_id + "|" + userE.user_id + "|" + userD.user_id;
                        break;
                }
                leftBallAddPass = lBall * 2 + lPass;
                rightBallAddPass = rBall * 2 + rPass;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doSavaToken();
                submit();
                break;
            case R.id.imageView56://放弃提交比分
                File file = new File(VCRpath);
                if (file.exists()) {
                    file.delete();
                }
                finish();
                break;
        }
    }

    private void submit() {
        String game_type = (team - 1) + "";
        String judge_type = "1";
        String result = result_a + "";
        String uid1;
        String uid2;
        if (team == 1) {
            uid1 = userA.user_id + "";
            uid2 = userF.user_id + "";
        } else {
            uid1 = league1.league_id + "";
            uid2 = league2.league_id + "";
        }

        String game_id = games.id;
        String goals1 = lBall + "";
        String goals2 = rBall + "";
        String fouls1 = lX + "";
        String fouls2 = rX + "";
        String flagrant_fouls1 = "0";
        String flagrant_fouls2 = "0";
        String pannas1 = lPass + "";
        String pannas2 = rPass + "";
        String panna_ko1 = isKT_a + "";
        String panna_ko2 = isKT_b + "";
        String abstained1 = "0";
        String abstained2 = "0";
        PostResult(game_type, judge_type, result, uid1, uid2,
                game_id,
                code, goals1, goals2, fouls1, fouls2,
                flagrant_fouls1, flagrant_fouls2, pannas1, pannas2,
                panna_ko1, panna_ko2, abstained1, abstained2,
                battle_id);
    }

    private void PostResult(String game_type, String judge_type, String result, String uid1, String uid2,
                            String game_id,
                            String code, String goals1, String goals2, String fouls1, String fouls2,
                            String flagrant_fouls1, String flagrant_fouls2, String pannas1, String pannas2,
                            String panna_ko1, String panna_ko2, String abstained1, String abstained2,
                            String battle_id) {
        showLoadingDiaglog();
        PostResultRequest request = new PostResultRequest(Constants.POST_RESULT, RequestMethod.POST);
        request.add("game_type", game_type);
        request.add("judge_type", judge_type);
        request.add("result", result);
        request.add("uid1", uid1);
        request.add("uid2", uid2);
        request.add("game_id", game_id);
        request.add("code", code);
        request.add("goals1", goals1);
        request.add("goals2", goals2);
        request.add("fouls1", fouls1);
        request.add("fouls2", fouls2);
        request.add("flagrant_fouls1", flagrant_fouls1);
        request.add("flagrant_fouls2", flagrant_fouls2);
        request.add("pannas1", pannas1);
        request.add("pannas2", pannas2);
        request.add("panna_ko1", panna_ko1);
        request.add("panna_ko2", panna_ko2);
        request.add("abstained1", abstained1);
        request.add("abstained2", abstained2);
        request.add("battle_id", battle_id);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<PostResult>() {
            @Override
            public void onSucceed(int what, Response<PostResult> response) {
                closeLoadingDialog();
                if (response.get().response.equals("success")) {
                    Intent intentUp = new Intent(UploadingActivity.this, GameResultActivity.class);
                    intentUp.putExtra(Constants.POSTRESULT_BEAN, response.get());
                    intentUp.putExtra(Constants.GAME, games);
                    int i = 0;
                    if (leftWin) {
                        i = 1;
                    } else if (rightWin) {
                        i = 2;
                    } else {
                        i = 0;
                    }
                    intentUp.putExtra(Constants.KT_CODE, i);
                    intentUp.putExtra(Constants.LEFT_USER, user_a);
                    intentUp.putExtra(Constants.RIGHT_USER, user_b);
                    intentUp.putExtra(Constants.LEFT_GRADE, leftBallAddPass);
                    intentUp.putExtra(Constants.RIGHT_GRADE, rightBallAddPass);
                    intentUp.putExtra(Constants.USER_A, userA);
                    intentUp.putExtra(Constants.USER_B, userB);
                    intentUp.putExtra(Constants.USER_C, userC);
                    intentUp.putExtra(Constants.USER_D, userD);
                    intentUp.putExtra(Constants.USER_E, userE);
                    intentUp.putExtra(Constants.USER_F, userF);
                    intentUp.putExtra(Constants.USER_TEAM, team);
                    intentUp.putExtra(Constants.LEAGUE1, league1);
                    intentUp.putExtra(Constants.LEAGUE2, league2);
                    startActivity(intentUp);
                    finish();
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    int result_a;
    int result_b;
    int isKT_a = 0;
    int isKT_b = 0;

    private void doSavaToken() {
        int gradeA = doGetAddScores_a();
        int gradeB = doGetAddScores_b();
        if (leftWin) {
            result_a = 1;
            result_b = -1;
            isKT_a = 1;
            isKT_b = 0;
        } else if (rightWin) {
            result_a = -1;
            result_b = 1;
            isKT_a = 0;
            isKT_b = 1;
        } else if (leftWin == false && rightWin == false) {
            if (leftBallAddPass > rightBallAddPass) {
                result_a = 1;
                result_b = -1;
            } else if (leftBallAddPass < rightBallAddPass) {
                result_a = -1;
                result_b = 1;
            } else {
                result_a = result_b = 0;
            }
        }
        String time = intent.getStringExtra(Constants.CURRENT_VCR_TIME);
        SideAandB side = new SideAandB();
        side.setUsers(user_a);
        side.setAdd_scores(gradeA);
        side.setResult(result_a);
        side.setGoals(lBall);
        side.setPannas(lPass);
        side.setFouls(lX);
        side.setFlagrant_fouls(0);
        side.setPanna_ko(isKT_a);
        side.setAbstained(0);
        side.setUsers_b(user_b);
        side.setAdd_scores_b(gradeB);
        side.setResult_b(result_b);
        side.setGoals_b(rBall);
        side.setPannas_b(rPass);
        side.setFouls_b(rX);
        side.setFlagrant_fouls_b(0);
        side.setPanna_ko_b(isKT_b);
        side.setAbstained_b(0);
        side.setPath(VCRpath);
        side.setTime(time);
        side.setGame_type(team - 1);
        side.setGame_id(games.id);
        side.setUser_id(App.getUserLogin().user_id+"");
        if (userA != null) {
            side.setUser1_id(userA.user_id + "");
        } else {
            side.setUser1_id("");
        }
        if (userF != null) {
            side.setUser2_id(userF.user_id + "");
        } else {
            side.setUser2_id("");
        }
        if (league1 != null) {
            side.setLeague1_id(league1.league_id);
        } else {
            side.setLeague1_id("");
        }
        if (league2 != null) {
            side.setLeague1_id(league2.league_id);
        } else {
            side.setLeague1_id("");
        }
        side.setPicture(picturePath);
        side.setBattle_id(battle_id);
        SideAandBDaoHelper.getInstance().addData(side);
    }

    private int doGetAddScores_b() {
        int add_scores_b = 0;
        if (grade_b <= grade_a) {
            if (rightWin) {
                add_scores_b = (int) Math.floor((grade_a - grade_b) * 1.5 + 15);
            } else if (leftWin) {
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 1.5 + 15) / 3);
            }
        } else if (grade_b - grade_a <= 2) {
            if (rightWin) {
                add_scores_b = (int) Math.floor((grade_b - grade_a) * 3 + 15);
            } else if (leftWin) {
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 3 + 15) / 3);
            }
        } else if (grade_b - grade_a > 2) {
            if (rightWin) {
                add_scores_b = (int) Math.floor((grade_b - grade_a) * 2 + 13);
            } else if (leftWin) {
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 2 + 13) / 3);
            }
        }
        return add_scores_b;
    }


    private int doGetAddScores_a() {
        int add_scores_a = 0;
        if (grade_a <= grade_b) {
            if (leftWin) {
                add_scores_a = (int) Math.floor((grade_b - grade_a) * 1.5 + 15);
            } else if (rightWin) {
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_b - grade_a) * 1.5 + 15) / 3);
            }
        } else if (grade_a - grade_b <= 2) {
            if (leftWin) {
                add_scores_a = (int) Math.floor((grade_a - grade_b) * 3 + 15);
            } else if (rightWin) {
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_a - grade_b) * 3 + 15) / 3);
            }
        } else if (grade_a - grade_b > 2) {
            if (leftWin) {
                add_scores_a = (int) Math.floor((grade_a - grade_b) * 2 + 13);
            } else if (rightWin) {
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_a - grade_b) * 2 + 13) / 3);
            }
        }
        return add_scores_a;
    }
}
