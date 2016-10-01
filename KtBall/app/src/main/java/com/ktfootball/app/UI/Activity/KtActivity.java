package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Entity.PostLeague;
import com.ktfootball.app.Entity.ScanUser;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.PostLeagueRequest;
import com.ktfootball.app.Request.ScanUserRequest;
import com.ktfootball.app.Views.SelectAvatarDialog;
import com.ktfootball.app.zxing.android.CaptureActivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class KtActivity extends BaseActivity {

    private ScanUser userA;
    private ScanUser userB;
    private ScanUser userC;
    private ScanUser userD;
    private ScanUser userE;
    private ScanUser userF;

    private PostLeague league1;
    private PostLeague league2;

    @Bind(R.id.game_avatar1_imageView)
    MyCircleImageView imageViewAvatarA;
    @Bind(R.id.game_avatar2_imageView)
    MyCircleImageView imageViewAvatarB;
    @Bind(R.id.game_avatar3_imageView)
    MyCircleImageView imageViewAvatarC;
    @Bind(R.id.game_avatar4_imageView)
    MyCircleImageView imageViewAvatarD;
    @Bind(R.id.game_avatar5_imageView)
    MyCircleImageView imageViewAvatarE;
    @Bind(R.id.game_avatar6_imageView)
    MyCircleImageView imageViewAvatarF;

    @Bind(R.id.textView40)
    TextView textViewA;
    @Bind(R.id.textView41)
    TextView textViewB;
    @Bind(R.id.textView42)
    TextView textViewC;
    @Bind(R.id.textView43)
    TextView textViewD;
    @Bind(R.id.textView44)
    TextView textViewE;
    @Bind(R.id.textView45)
    TextView textViewF;

    @Bind(R.id.zandouli_num_A)
    TextView zandouli_A;
    @Bind(R.id.boom_A)
    ImageView boom_A;
    @Bind(R.id.relativelayout_info_A)
    RelativeLayout relativelayout_A;
    @Bind(R.id.dengji_A)
    ImageView dengji_A;

    @Bind(R.id.zandouli_num_B)
    TextView zandouli_B;
    @Bind(R.id.boom_B)
    ImageView boom_B;
    @Bind(R.id.relativelayout_info_B)
    RelativeLayout relativelayout_B;
    @Bind(R.id.dengji_B)
    ImageView dengji_B;

    @Bind(R.id.zandouli_num_C)
    TextView zandouli_C;
    @Bind(R.id.boom_C)
    ImageView boom_C;
    @Bind(R.id.relativelayout_info_C)
    RelativeLayout relativelayout_C;
    @Bind(R.id.dengji_C)
    ImageView dengji_C;

    @Bind(R.id.zandouli_num_D)
    TextView zandouli_D;
    @Bind(R.id.boom_D)
    ImageView boom_D;
    @Bind(R.id.relativelayout_info_D)
    RelativeLayout relativelayout_D;
    @Bind(R.id.dengji_D)
    ImageView dengji_D;

    @Bind(R.id.zandouli_num_E)
    TextView zandouli_E;
    @Bind(R.id.boom_E)
    ImageView boom_E;
    @Bind(R.id.relativelayout_info_E)
    RelativeLayout relativelayout_E;
    @Bind(R.id.dengji_E)
    ImageView dengji_E;

    @Bind(R.id.zandouli_num_F)
    TextView zandouli_F;
    @Bind(R.id.boom_F)
    ImageView boom_F;
    @Bind(R.id.relativelayout_info_F)
    RelativeLayout relativelayout_F;
    @Bind(R.id.dengji_F)
    ImageView dengji_F;

    @Bind(R.id.activity_kt_camare)
    ImageView camare;
    @Bind(R.id.activity_kt_ready)
    ImageView ready;
    @Bind(R.id.activity_kt_rg)
    RadioGroup rg;

    int team;
    String gameid;
    GamePlace.Games game;
    private SelectAvatarDialog dialog;
    private int vedioCode = 1;

    //拍照
    private File imageFile;
    private String picturePath;

    /**
     * 存储用户的 Map
     */
    private Map<Integer, String> userMap;
    private String code;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_kt);
    }

    @Override
    protected void setListener() {
        camare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVSActivity();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_kt_smooth :
                        vedioCode = 1;
                        break;
                    case R.id.activity_kt_hdtv :
                        vedioCode = 2;
                        break;
                    case R.id.activity_kt_very_high :
                        vedioCode = 3;
                        break;
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    private void showDialog() {
        imageFile = getFile();
        if (dialog == null) {
            dialog = new SelectAvatarDialog(this, R.style.transparentFrameWindowStyle, imageFile);
        }
        dialog.show();
        dimActivity(dialog, 0.6f);
    }

    private File getFile() {
        return new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
    }

    //初始化视图
    private void initView() {
        userMap = new HashMap<>();
        Intent intent = getIntent();
        team = intent.getIntExtra(Constants.KT_CODE, 1);
        game = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
        code = intent.getStringExtra(Constants.QICHANG_CODE);
        gameid = game.id + "";

        switch (team) {
            case 1:
                imageViewAvatarB.setVisibility(View.GONE);
                imageViewAvatarC.setVisibility(View.GONE);
                imageViewAvatarD.setVisibility(View.GONE);
                imageViewAvatarE.setVisibility(View.GONE);
                userMap.put(1, "");
                userMap.put(6, "");
                break;
            case 2:
                imageViewAvatarC.setVisibility(View.GONE);
                imageViewAvatarD.setVisibility(View.GONE);
                userMap.put(1, "");
                userMap.put(2, "");
                userMap.put(5, "");
                userMap.put(6, "");
                break;
            case 3:
                userMap.put(1, "");
                userMap.put(2, "");
                userMap.put(3, "");
                userMap.put(4, "");
                userMap.put(5, "");
                userMap.put(6, "");
                break;
        }
    }

    //扫描用户二维码
    public void doScan(View view) {

        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra(Constants.CAPTURE_CODE, 2);

        //判断点击的是哪张图片
        switch (view.getId()) {
            case R.id.game_avatar1_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 1);
                break;
            case R.id.game_avatar2_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 2);
                break;
            case R.id.game_avatar3_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 3);
                break;
            case R.id.game_avatar4_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 4);
                break;
            case R.id.game_avatar5_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 5);
                break;
            case R.id.game_avatar6_imageView:
                intent.putExtra(Constants.IMAGEVIEW_CODE, 6);
                break;
        }
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_CAMERA) {
            intentToCrop();
        } else if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_GALLERY) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            camare.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KT足球/" + System.currentTimeMillis() + ".png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
//                updataLeagueAvatar(league_id + "");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            picturePath = f.getPath();
        }

        if (requestCode == 2) {
            if (resultCode == Constants.CAPTUREACTIVITY_RESULT_OK) {
                //取值
                Bundle bundle = data.getBundleExtra("bundle");
                String userScanResult = bundle.getString(Constants.DECODED_CONTENT_KEY);
                if(userScanResult.contains("=")){
                    userScanResult = userScanResult.substring(userScanResult.indexOf("=")+1);
                }
                int imageViewCode = bundle.getInt(Constants.TWO_IMAGEVIEW_CODE);
                if (userScanResult.equals("null")) {
                    showDialogToast("请重新扫描");
                } else {
                    if (team == 1) {
                        LogUtils.e(userMap.get(1) + "......" + userScanResult);
                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            showDialogToast("用户已存在");
                            return;
                        }

                    } else if (team == 2) {

                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(2).equals(userScanResult)
                                || userMap.get(5).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            showDialogToast("用户已存在");
                            return;
                        }

                    } else if (team == 3) {
                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(2).equals(userScanResult)
                                || userMap.get(3).equals(userScanResult)
                                || userMap.get(4).equals(userScanResult)
                                || userMap.get(5).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            showDialogToast("用户已存在");
                            return;
                        }
                    }

                    getUserInfo(userScanResult, gameid, imageViewCode);
                }
            }
        }
    }

    private void initUser(String userScanResult,int imageViewCode){
        userMap.put(imageViewCode, userScanResult);
        LogUtils.e("imageViewCode = " + imageViewCode + "...." + userScanResult);
        boolean check = true;

        if (team == 1) {
            if (userMap.get(1).equals("")) check = false;
            if (userMap.get(6).equals("")) check = false;
        } else if (team == 2) {
            if (userMap.get(1).equals("")) check = false;
            if (userMap.get(2).equals("")) check = false;
            if (userMap.get(5).equals("")) check = false;
            if (userMap.get(6).equals("")) check = false;
        } else if (team == 3) {
            if (userMap.get(1).equals("")) check = false;
            if (userMap.get(2).equals("")) check = false;
            if (userMap.get(3).equals("")) check = false;
            if (userMap.get(4).equals("")) check = false;
            if (userMap.get(5).equals("")) check = false;
            if (userMap.get(6).equals("")) check = false;
        }

        if (check) {
            ready.setVisibility(View.VISIBLE);
        }
    }

    private void startVSActivity() {
        Intent intent = new Intent(KtActivity.this, VSActivity.class);
        intent.putExtra(Constants.USER_TEAM, team);
        intent.putExtra(Constants.VEDIOCODE, vedioCode);
        intent.putExtra(Constants.LEAGUE1, league1);
        intent.putExtra(Constants.LEAGUE2, league2);
        intent.putExtra(Constants.GAME, game);
        intent.putExtra(Constants.QICHANG_CODE, code);
        intent.putExtra(Constants.PICTUREPATH, picturePath);

        intent.putExtra(Constants.USER_A, userA);
        intent.putExtra(Constants.USER_F, userF);
        switch (team) {
            case 2:
                intent.putExtra(Constants.USER_B, userB);
                intent.putExtra(Constants.USER_E, userE);
                break;
            case 3:
                intent.putExtra(Constants.USER_C, userC);
                intent.putExtra(Constants.USER_D, userD);
                break;
        }
        startActivity(intent);
        finish();
    }

    private void getUserInfo(final String userId, String gameId, final int imageViewCode) {

        ScanUserRequest request = new ScanUserRequest(Constants.SCAN_USER, RequestMethod.GET);
        request.add("user_id", userId);
        request.add("game_id", gameId);
        request.add("game_mode", "1");
        showLoadingDiaglog();
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<ScanUser>() {
            @Override
            public void onSucceed(int what, Response<ScanUser> response) {
                if (response.get().response.equals("success")) {
                    loadUserInfo(response.get(), imageViewCode);
                    initUser(userId,imageViewCode);
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

    private void loadUserInfo(ScanUser user, int imageViewCode) {
        switch (imageViewCode) {
            case 1:
                initScanUser(user, relativelayout_A, imageViewAvatarA, textViewA, zandouli_A, boom_A, dengji_A, true);
                userA = user;
                getLeftLeagueInfo();
                break;
            case 2:
                initScanUser(user, relativelayout_B, imageViewAvatarB, textViewB, zandouli_B, boom_B, dengji_B, true);
                userB = user;
                getLeftLeagueInfo();
                break;
            case 3:
                initScanUser(user, relativelayout_C, imageViewAvatarC, textViewC, zandouli_C, boom_C, dengji_C, true);
                userC = user;
                getLeftLeagueInfo();
                break;
            case 4:
                initScanUser(user, relativelayout_D, imageViewAvatarD, textViewD, zandouli_D, boom_D, dengji_D, false);
                userD = user;
                getRightLeagueInfo();
                break;
            case 5:
                initScanUser(user, relativelayout_E, imageViewAvatarE, textViewE, zandouli_E, boom_E, dengji_E, false);
                userE = user;
                getRightLeagueInfo();
                break;
            case 6:
                initScanUser(user, relativelayout_F, imageViewAvatarF, textViewF, zandouli_F, boom_F, dengji_F, false);
                userF = user;
                getRightLeagueInfo();
                break;
        }
    }

    private void getLeftLeagueInfo(){
        if(team == 2){
            if(userA != null && userB != null){
                get2v2LeagueInfo(userA,userB,true);
            }
        }else if(team == 3){
            if(userA != null && userB != null && userC != null){
                get3v3LeagueInfo(userA,userB,userC,true);
            }
        }
    }

    private void getRightLeagueInfo(){
        if(team == 2){
            if(userE != null && userF != null){
                get2v2LeagueInfo(userE,userF,false);
            }
        }else if(team == 3){
            if(userD != null && userE != null && userF != null){
                get3v3LeagueInfo(userD,userE,userF,false);
            }
        }
    }

    private void get2v2LeagueInfo(ScanUser user1, ScanUser user2, final boolean isLeft){
        showLoadingDiaglog("正在获取战队信息");
        PostLeagueRequest request = new PostLeagueRequest(Constants.POST_LEAGUE, RequestMethod.POST);
        request.add("user1_id", user1.user_id+"");
        request.add("user2_id", user2.user_id+"");
        request.add("league_name", user1.nickname+","+user2.nickname);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<PostLeague>() {
            @Override
            public void onSucceed(int what, Response<PostLeague> response) {
                if(response.get().response.equals("success")){
                    if(isLeft){
                        league1 = response.get();
                    }else{
                        league2 = response.get();
                    }
                }else{
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

    private void get3v3LeagueInfo(ScanUser user1, ScanUser user2, ScanUser user3,final boolean isLeft){
        showLoadingDiaglog("正在获取战队信息");
        PostLeagueRequest request = new PostLeagueRequest(Constants.POST_LEAGUE_3V3, RequestMethod.POST);
        request.add("user1_id", user1.user_id+"");
        request.add("user2_id", user2.user_id+"");
        request.add("user3_id", user3.user_id+"");
        request.add("league_name", user1.nickname+","+user2.nickname+","+user3.nickname);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<PostLeague>() {
            @Override
            public void onSucceed(int what, Response<PostLeague> response) {
                if(response.get().response.equals("success")){
                    if(isLeft){
                        league1 = response.get();
                    }else{
                        league2 = response.get();
                    }
                }else{
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

    public void initScanUser(ScanUser user, RelativeLayout relativeLayout, MyCircleImageView imageViewAvatar, TextView nicknameTv, TextView zandouliTv, ImageView boom, ImageView dengji, boolean left) {
        String avatar = user.avatar;
        String nickname = user.nickname;
        String path1 = Constants.HOST + avatar;
        BitmapManager.getInstance().displayUserLogo(imageViewAvatar, path1);
        nicknameTv.setText(nickname);
        nicknameTv.setTag(path1);
        zandouliTv.setText(user.power + "");
        if (user.power >= 0 && user.power < 1000) {
            if (left) {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_1));
            } else {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_1_2));
            }
        } else if (user.power >= 1000 && user.power < 2000) {
            if (left) {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_2));
            } else {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_2_2));
            }
        } else if (user.power >= 2000) {
            if (left) {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_3));
            } else {
                dengji.setImageDrawable(getResources().getDrawable(R.drawable.icon_bao_3_2));
            }
        }
        relativeLayout.setVisibility(View.VISIBLE);

        int shoTime = 40;
        AnimationDrawable frameAnim = new AnimationDrawable();
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom1), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom2), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom3), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom4), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom5), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom6), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom7), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom8), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom9), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom10), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom11), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom12), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom13), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom14), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom15), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom16), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom17), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom18), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom19), shoTime);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.boom20), shoTime);
        frameAnim.setOneShot(true);
        // 设置ImageView的背景为AnimationDrawable
        boom.setBackgroundDrawable(frameAnim);
        frameAnim.start();
    }

    public void doBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 1; i <= userMap.size(); i++) {
            userMap.remove(i);
        }
        userMap.clear();
    }

    private void intentToCrop() {
        if (imageFile == null) {
            imageFile = getFile();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, SelectAvatarDialog.PHOTO_REQUEST_GALLERY);
    }
}
