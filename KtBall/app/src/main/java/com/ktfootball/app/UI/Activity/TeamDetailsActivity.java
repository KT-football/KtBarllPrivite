package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ktfootball.app.Constants;
import com.kt.ktball.entity.TeamDetailData;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.Views.SelectAvatarDialog;
import com.ktfootball.app.R;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;

public class TeamDetailsActivity extends BaseActivity {


    @Bind(R.id.imageView66)
    ImageView imageViewAvatar;//头像
    @Bind(R.id.textView90)
    TextView textViewJiFen;//比分
    @Bind(R.id.textView36)
    TextView textViewTitle;//标题
    @Bind(R.id.textView93)
    TextView textViewName;//我的战队
    @Bind(R.id.imageView69)
    ImageView imageViewA;
    @Bind(R.id.imageView70)
    ImageView imageViewB;
    @Bind(R.id.imageView71)
    ImageView imageViewC;

    @Bind(R.id.textView94)
    TextView textViewNameA;
    @Bind(R.id.textView96)
    TextView textViewNameB;
    @Bind(R.id.textView98)
    TextView textViewNameC;

    @Bind(R.id.textView95)
    TextView textViewZhanA;
    @Bind(R.id.textView97)
    TextView textViewZhanB;
    @Bind(R.id.textView99)
    TextView textViewZhanC;
    @Bind(R.id.relativeLayout37)
    RelativeLayout myTeam;

    long league_id;
    String gameType;
    private SelectAvatarDialog dialog;
    private final int TO_CHANGETEAMACTIVITY = 100;
    /**
     * 单个文件上传监听的标志。
     */
    private final int WHAT_UPLOAD_SINGLE = 0x01;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_team_details);
    }

    @Override
    protected void setListener() {
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        myTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTeamNameActivity();
            }
        });
    }

    private void changeTeamNameActivity() {
        Intent intent = new Intent(getThis(), TeamNameChangeActivity.class);
        intent.putExtra("league_id",league_id+"");
        startActivityForResult(intent, TO_CHANGETEAMACTIVITY);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        league_id = intent.getLongExtra(MyTeamActivity.EXTRA_TEAM_ID, 0);
        gameType = intent.getStringExtra(MyTeamActivity.EXTRA_GAME_TYPE);
        String url = "http://www.ktfootball.com/apiv2/users/info_league?league_id=" +
                league_id + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
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
                        TeamDetailData teamDetailData = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<TeamDetailData>() {
                                }.getType());
                        init(teamDetailData);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    private void init(final TeamDetailData teamDetailData) {//获得战队详情
        String url = "http://www.ktfootball.com" + teamDetailData.avatar;
        BitmapManager.getInstance().displayUserLogo(imageViewAvatar, url);
        textViewJiFen.setText("" + teamDetailData.scores);
        textViewTitle.setText(teamDetailData.name);
        textViewName.setText(teamDetailData.name);
        if (gameType.equals("2")) {
            String uri1 = "http://www.ktfootball.com" + teamDetailData.usera_avatar;
            Glide.with(this).load(uri1).transform(new GlideCircleTransform(this)).into(imageViewA);
            textViewNameA.setText(teamDetailData.usera_nickname);
            textViewZhanA.setText("战斗力：" + teamDetailData.usera_power);
            if (teamDetailData.userb_avatar != null) {
                String uri2 = "http://www.ktfootball.com" + teamDetailData.userb_avatar;
                Glide.with(this).load(uri2).transform(new GlideCircleTransform(this)).into(imageViewB);
                textViewNameB.setText(teamDetailData.userb_nickname);
                textViewZhanB.setText("战斗力：" + teamDetailData.userb_power);

            } else {
                imageViewB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TeamDetailsActivity.this, InviteActivity.class);
                        intent.putExtra(MyTeamActivity.EXTRA_GAME_TYPE, gameType);
                        intent.putExtra(MyTeamActivity.EXTRA_TEAM_ID, league_id);
                        startActivity(intent);
                    }
                });
            }
            if (teamDetailData.userc_avatar != null) {
                String uri3 = "http://www.ktfootball.com" + teamDetailData.userc_avatar;
                Glide.with(this).load(uri3).transform(new GlideCircleTransform(this)).into(imageViewC);
                textViewNameC.setText(teamDetailData.userc_nickname);
                textViewZhanC.setText("战斗力：" + teamDetailData.userc_power);
            } else {
                imageViewC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TeamDetailsActivity.this, InviteActivity.class);
                        intent.putExtra(MyTeamActivity.EXTRA_GAME_TYPE, gameType);
                        intent.putExtra(MyTeamActivity.EXTRA_TEAM_ID, league_id);
                        startActivity(intent);
                    }
                });
            }
        } else {
            imageViewC.setVisibility(View.GONE);
            String uri1 = "http://www.ktfootball.com" + teamDetailData.usera_avatar;
            Glide.with(this).load(uri1).transform(new GlideCircleTransform(this)).into(imageViewA);
            textViewNameA.setText(teamDetailData.usera_nickname);
            textViewZhanA.setText("战队力：" + teamDetailData.usera_power);
            if (teamDetailData.userb_avatar != null) {
                String uri2 = "http://www.ktfootball.com" + teamDetailData.userb_avatar;
                Glide.with(this).load(uri2).transform(new GlideCircleTransform(this)).into(imageViewB);
                textViewNameB.setText(teamDetailData.userb_nickname);
                textViewZhanB.setText("战队力：" + teamDetailData.userb_power);
            } else {
                imageViewB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TeamDetailsActivity.this, InviteActivity.class);
                        intent.putExtra(MyTeamActivity.EXTRA_GAME_TYPE, gameType);
                        intent.putExtra(MyTeamActivity.EXTRA_TEAM_ID, league_id);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public void doBack(View view) {
        finish();
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new SelectAvatarDialog(this, R.style.transparentFrameWindowStyle, imageFile);
        }
        dialog.show();
        dimActivity(dialog, 0.6f);
    }

    //拍照
    private File imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_CAMERA) {
            intentToCrop();
        } else if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_GALLERY) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            imageViewAvatar.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KT足球/NewGameAvatar.png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                updataLeagueAvatar(league_id + "");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(requestCode == TO_CHANGETEAMACTIVITY){
            if(resultCode == TeamNameChangeActivity.NAME_CHANGE){
                if(data!= null){
                    String name = data.getStringExtra("name");
                    textViewTitle.setText(name);
                    textViewName.setText(name);
                }
            }
            if(resultCode == TeamNameChangeActivity.GOOUT_CHANGE){
                finish();
            }

        }
    }

    private void updataLeagueAvatar(String league_id) {
        com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.CHANGE_LEAGUE_AVATAR, RequestMethod.POST);

        // 添加普通参数。
        request.add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        request.add("league_id", league_id);

        // 上传文件需要实现NoHttp的Binary接口，NoHttp默认实现了FileBinary、InputStreamBinary、ByteArrayBitnary、BitmapBinary。
        FileBinary fileBinary0 = new FileBinary(imageFile);
        /**
         * 监听上传过程，如果不需要监听就不用设置。
         * 第一个参数：what，what和handler的what一样，会在回调被调用的回调你开发者，作用是一个Listener可以监听多个文件的上传状态。
         * 第二个参数： 监听器。
         */
        fileBinary0.setUploadListener(WHAT_UPLOAD_SINGLE, mOnUploadListener);

        request.add("avatar", fileBinary0);// 添加1个文件
//            request.add("image1", fileBinary1);// 添加2个文件

        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
//                showMessageDialog(R.string.request_succeed, response.get());
//                showToast(R.string.request_succeed);
                LogUtils.e(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                showMessageDialog(R.string.request_failed, exception.getMessage());
            }
        }, false, true);
    }



    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
        }
    };

    private void intentToCrop() {
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
