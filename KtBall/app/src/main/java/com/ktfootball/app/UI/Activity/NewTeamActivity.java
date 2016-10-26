package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.MyDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Event.AddTeamEvent;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class NewTeamActivity extends BaseActivity {

    @Bind(R.id.imageView72)
    ImageView imageView;
    @Bind(R.id.editText10)
    EditText editText;
    @Bind(R.id.textView104)
    TextView textView;
    @Bind(R.id.relativeLayout888)
    RelativeLayout relativeLayout;//头像选择布局

    String avatarData = "";
    String name;
    int game_type;
    MyAlertDialog myAlertDialog;
    long userId;
    MyDialog myDialog;

    public static final int PAIZHAO_REQUEST_CODE = 1;
    public static final int XIANGCE_REQUEST_CODE = 2;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_team);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        myAlertDialog = new MyAlertDialog(this);
        myDialog = new MyDialog(this,"正在创建");
        if (getIntent()!=null) {
            if (getIntent().getStringExtra("type").equals("1")) {
                textView.setText("2v2");
                game_type = 1;
            } else {
                textView.setText("3v3");
                game_type = 2;
            }
        }
    }

    public void doNewTeamFinsh(View view) {//创建战队完成
        name = editText.getText().toString();
        if (TextUtils.isEmpty(name)){
            myAlertDialog.doAlertDialog("战队名不能为空");
        } else if (game_type < 0){
            myAlertDialog.doAlertDialog("请选择战队类型");
        } else {
            myDialog.show();
            String url = Constants.HOST +"users/create_league";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id",userId);
//                jsonObject.put("avatar", avatarData);
                jsonObject.put("name", name);
                jsonObject.put("game_type", game_type);
                jsonObject.put("authenticity_token","K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject1) {
                            Log.d("]]]]]]]]]]]]]]]",jsonObject1.toString());
                            //关闭动画
                            myDialog.dismiss();
                            try {
                                JSONObject jsonObject11 = new JSONObject(jsonObject1.toString());
                                String response = jsonObject11.getString("response");
                                if (response.equals("success")){
                                    myAlertDialog.doAlertDialog("创建战队成功");
                                    EventBus.getDefault().post(new AddTeamEvent());
                                    finish();
                                } else if (response.equals("error")){
                                    String msg = jsonObject11.getString("msg");
                                    myAlertDialog.doAlertDialog(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("]]]]]]]]]]]]]]]",error.toString());
                    //关闭动画
                    myDialog.dismiss();
                    myAlertDialog.doAlertDialog("网络错误");
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
    }

    public void doBack(View view) {
        finish();
    }

    public void doGetPhoto(View view) {//获取照片
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public void doSeleteType(View view) {//选择战队类型
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.inflate(R.menu.team_type_munu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_v_one:
                        textView.setText("2v2");
                        game_type = 1;
                        break;
                    case R.id.action_v_two:
                        textView.setText("3v3");
                        game_type = 2;
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    //拍照
    private static File imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
    private void takePhotoFromCamara() {
        if (!imageFile.exists()) imageFile.mkdirs();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, PAIZHAO_REQUEST_CODE);
    }

    //从相册中选照片
    private Uri imageUri;
    private void chooseFromMedia() {
        File imageFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
        else imageFile = new File(getCacheDir(), "/KT足球/NewGameAvatar.png");

        if (imageFile.exists()) imageFile.delete();

        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(imageFile);
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, XIANGCE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAIZHAO_REQUEST_CODE) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 280);
            intent.putExtra("outputY", 280);
            intent.putExtra("return-data", true);
            startActivityForResult(intent,XIANGCE_REQUEST_CODE);

        } else if (requestCode == XIANGCE_REQUEST_CODE) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            imageView.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/KT足球/NewGameAvatar.png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                byte[] bb=File2byte(f);
                String str = "";
                StringBuffer sb = new StringBuffer(str);
                for (byte b : bb){
                    sb.append(String.valueOf(b));
                }
                avatarData = sb.toString();
                Log.d("avatarDataavatarData",avatarData);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }


    public void doTakePhoto(View view) {//拍照
        takePhotoFromCamara();
        relativeLayout.setVisibility(View.GONE);
    }

    public void doSeletePhoto(View view) {//从相册中选择
        chooseFromMedia();
        relativeLayout.setVisibility(View.GONE);
    }

    public void doCanal(View view) {//取消
        relativeLayout.setVisibility(View.GONE);
    }
}
