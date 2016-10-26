package com.ktfootball.app.UI.Activity.setting;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.UI.Activity.MyQRCodeActivity;
import com.kt.ktball.entity.UserInfo;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyCircleImageView;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.Views.SelectAvatarDialog;
import com.ktfootball.app.Views.SelectSexDialog;
import com.kt.ktball.views.wheelview.OnWheelScrollListener;
import com.kt.ktball.views.wheelview.WheelView;
import com.kt.ktball.views.wheelview.adapter.NumericWheelAdapter;
import com.ktfootball.app.R;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class UserinfoChangeActivity extends BaseActivity {

    @Bind(R.id.layout_userinfochange_header)
    MyCircleImageView header;

    @Bind(R.id.layout_userinfochange_nickname)
    TextView nickname;

    @Bind(R.id.layout_userinfochange_sex)
    TextView sex;

    @Bind(R.id.layout_userinfochange_brisday)
    TextView brisday;

//    @Bind(R.id.layout_userinfochange_changepassword)
//    RelativeLayout changepassword;
//
//    @Bind(R.id.layout_userinfochange_bingphone)
//    RelativeLayout bingphone;

    private SelectAvatarDialog dialog;
    private SelectSexDialog sexDialog;
    public static final String NICKNAME = "nickname";
    public static final int NICKNAME_RESULT = 1110;
    public static final int NICKNAME_START = 1111;
    private final int WHAT_UPLOAD_SINGLE = 0x01;
    private String userId;
    public static final String USER_ID = "userid";
    private UserInfo userInfo;
    @Bind(R.id.tv_version)
    TextView tv_version;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_userinfochange);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = getIntent().getStringExtra(USER_ID);
        getuserInfo(userId);
        BitmapManager.getInstance().displayUserLogo(header, Constants.HEAD_HOST + App.getUserLogin().avatar);
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            tv_version.setText("- 版本 "+info.versionName+" -"); // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.layout_userinfo)
    public void userinfoSubmit(View v) {
        updataUserInfo();
    }

    @OnClick(R.id.layout_userinfochange_rl_nickname)
    public void nicknameChange(View v) {
        Intent intent = new Intent(getThis(), ChangeNicknameActivity.class);
        startActivityForResult(intent, NICKNAME_START);
    }

    @OnClick(R.id.layout_userinfochange_rl_sex)
    public void sex(View v) {
        showSexDialog();
    }

    @OnClick(R.id.layout_userinfochange_rl_brisday)
    public void brisday(View v) {
        new BirthDialog(this).show();
    }

    @OnClick(R.id.layout_userinfochange_changeheader)
    public void headerChange(View v) {
        showDialog();
    }


    private void showDialog() {
        if (dialog == null) {
            dialog = new SelectAvatarDialog(this, R.style.transparentFrameWindowStyle, imageFile);
        }
        dialog.show();
        dimActivity(dialog, 0.6f);
    }

    private void showSexDialog() {
        if (sexDialog == null) {
            sexDialog = new SelectSexDialog(this, R.style.transparentFrameWindowStyle);
        }
        sexDialog.show();
        dimActivity(sexDialog, 0.6f);
    }

    public void setSex(String s) {
        sex.setText(s);
    }

    private void getuserInfo(String userid) {
        String url = Constants.GET_PROFILE + "?user_id=" +
                userid +
                "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        LogUtils.e(url);
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        LogUtils.e(jsonObject.toString());
                        userInfo = GsonTools.changeGsonToBean(jsonObject.toString(), UserInfo.class);
                        LogUtils.e(userInfo.country_cities.size() + "");
                        LogUtils.e(userInfo.country_cities.get(0).get("中国").size() + "");
                        closeLoadingDialog();
                        nickname.setText(userInfo.nickname);
                        String gender = userInfo.gender;
                        if ("GG".equals(gender)) {
                            sex.setText("男");
                        } else {
                            sex.setText("女");
                        }
                        brisday.setText(userInfo.birthday);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.e(error.toString());
                closeLoadingDialog();
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

    private void updataUserInfo() {
        showLoadingDiaglog();
        String url = Constants.PROFILE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", App.getUserLogin().user_id + "");
            jsonObject.put("nickname", nickname.getText().toString());
            if (sex.getText().toString().equals("男")) {
                jsonObject.put("gender", "GG");
            } else {
                jsonObject.put("gender", "MM");
            }
            jsonObject.put("birthday", brisday.getText().toString());
            jsonObject.put("football_age", userInfo.football_age);
            jsonObject.put("country", userInfo.country);
            jsonObject.put("city", userInfo.city);
            jsonObject.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtils.e(response.toString());
                        closeLoadingDialog();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeLoadingDialog();
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

    //拍照
    private File imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");

    private void updataLeagueAvatar(String userid) {
        com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.UPLOAD_AVATAR, RequestMethod.POST);

        // 添加普通参数。
        request.add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        request.add("user_id", userid);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NICKNAME_START && resultCode == NICKNAME_RESULT) {
            if (data != null) {
                nickname.setText(data.getStringExtra(NICKNAME));
            }
            return;
        }
        if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_CAMERA) {
            intentToCrop();
        } else if (requestCode == SelectAvatarDialog.PHOTO_REQUEST_GALLERY) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            header.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KT足球/NewGameAvatar.png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                updataLeagueAvatar(App.getUserLogin().user_id + "");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

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

    //生日
    class BirthDialog
            extends Dialog
            implements OnWheelScrollListener, View.OnClickListener {

        private WheelView year;
        private WheelView month;
        private WheelView day;

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            NumericWheelAdapter temp = new NumericWheelAdapter(getThis(), 1, getDay(year.getCurrentItem() + 1950, month.getCurrentItem() + 1));
            temp.setLabel("日");
            day.setViewAdapter(temp);
        }

        public BirthDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(getThis(), R.layout.dialog_wheel, null);

            year = (WheelView) view.findViewById(R.id.year);
            month = (WheelView) view.findViewById(R.id.month);
            day = (WheelView) view.findViewById(R.id.day);

            NumericWheelAdapter numericWheelAdapter1 =
                    new NumericWheelAdapter(getThis(), 1950, Calendar.getInstance().get(Calendar.YEAR) + 10);
            numericWheelAdapter1.setLabel("年");
            year.setViewAdapter(numericWheelAdapter1);
            year.setCyclic(true);
            year.setVisibleItems(7);
            year.setCurrentItem(Calendar.getInstance().get(Calendar.YEAR) - 1950);
            year.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter2 =
                    new NumericWheelAdapter(getThis(), 1, 12);
            numericWheelAdapter2.setLabel("月");
            month.setViewAdapter(numericWheelAdapter2);
            month.setCyclic(true);
            month.setCurrentItem(Calendar.getInstance().get(Calendar.MONTH));
            month.setVisibleItems(7);
            month.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter3 =
                    new NumericWheelAdapter(getThis(), 1, getDay(1996, 1));
            numericWheelAdapter3.setLabel("日");
            day.setViewAdapter(numericWheelAdapter3);
            day.setCyclic(true);
            day.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
            day.setVisibleItems(7);
            day.addScrollingListener(this);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        private int getDay(int year, int month) {
            int day = 30;
            boolean flag = false;
            switch (year % 4) {
                case 0:
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 2:
                    day = flag ? 29 : 28;
                    break;
                default:
                    day = 30;
                    break;
            }
            return day;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int y = year.getCurrentItem() + 1950;
                    int m = month.getCurrentItem() + 1;
                    int d = day.getCurrentItem() + 1;
                    String data = y + "-" + m + "-" + d;
                    brisday.setText(data);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    @OnClick(R.id.layout_set_question)
    public void toQurestionActivity(View v) {
        startActivity(new Intent(getThis(), QuestionActivity.class));
    }

    @OnClick(R.id.layout_set_about)
    public void toAboutActivity(View v) {
        startActivity(new Intent(getThis(), AboutActivity.class));
    }

    @OnClick(R.id.btn_goout)
    public void goout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.layout_settting)
    public void settig() {
        startActivity(new Intent(getThis(), SetActivity.class));
    }


//    提交个人信息(post)(用于提交个人信息页面)
//    接口地址: http://www.ktfootball.com/apiv2/users/profile
//    参数：
//    user_id:  用户ID
//    nickname: 昵称
//    gender: 性别(男: GG, 女: MM)
//    birthday: 生日
//    football_age: 球龄
//    country: 国家
//    city: 城市
//    参数例子:
//    {user_id: 39393, nickname: "昵称",gender: "GG", birthday: "1990-10-11", football_age: "三年以上",country: "中国", city: "上海"}
//    成功返回:
//    { response: "success" }
//    失败返回
//    { response: "error", msg: "错误信息" }


//    修改头像(post)
//    接口地址: http://www.ktfootball.com/apiv2/users/upload_avatar
//    参数：
//    user_id:  用户ID
//    avatar: 头像二进制数据
//    参数例子:
//    { user_id: 1, avatar: "二进制数据" }
//    成功返回：
//    { response: "success", url: "新头像url" }
//    失败返回
//    { response: "error", msg: "错误信息" }

//    个人信息(get)(用于个人信息页面)
//    接口地址: http://www.ktfootball.com/apiv2/users/profile
//    参数：
//    user_id: 39393
//    参数例子:
//    user_id=39393
//    成功返回:
//    { response: "success", nickname: "昵称",gender: "性别:GG或MM", birthday: "1990-10-11", football_age "三年以上", country: "中国", city: "上海" ,country_cities: [{"中国" => [城市1,城市2,城市3]},{..},{...}]] }
}
