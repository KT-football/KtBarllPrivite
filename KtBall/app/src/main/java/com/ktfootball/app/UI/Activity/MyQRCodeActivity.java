package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.PhoneUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.ktfootball.app.UI.Activity.setting.WhatIsKTBiActivity;
import com.kt.ktball.entity.UserLogin;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class MyQRCodeActivity extends BaseActivity {

    @Bind(R.id.layout_qrcode_header)
    MyCircleImageView header;
    @Bind(R.id.layout_qrcode_name)
    TextView name;

    @Bind(R.id.layout_qrcode_bi)
    TextView bi;
    @Bind(R.id.layout_qrcode_chang)
    TextView chang;
    @Bind(R.id.layout_qrcode_qrcode)
    ImageView qrcode;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_qrcode);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        UserLogin userLogin = App.getUserLogin();
        BitmapManager.getInstance().displayUserLogo(header, Constants.HOST + userLogin.avatar);
        name.setText(userLogin.nickname);
        bi.setText("剩余KT币        " + userLogin.ktb);
        chang.setText("剩余场次         " + userLogin.vip_count);
        int screenW = PhoneUtils.getScreenWidth(getThis());
        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) qrcode.getLayoutParams();
        ll.height = screenW/2;
        ll.width = screenW/2;
        qrcode.setLayoutParams(ll);
        Bitmap qr = null;
        try {
            qr = Create2DCode(userLogin.user_id+"",screenW/2,screenW/2);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrcode.setImageBitmap(qr);
    }

    @OnClick(R.id.layout_qrcode_how_ktbi)
    public void whatKT(){
        Intent intent = new Intent(getThis(), WhatIsKTBiActivity.class);
        startActivity(intent);
    }

    /**
     * 用字符串生成二维码
     *
     * @param str
     * @return
     * @throws WriterException
     * @author zhouzhe@lenovo-cw.com
     */
    public Bitmap Create2DCode(String str,int w,int h ) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, w, h);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public void doBack(View view) {
        finish();
    }


//    参赛二维码(用于显示参赛二维码页面)(get)
//    接口地址: http://www.ktfootball.com/apiv2/users/qrcode
//    参数：
//    user_id:  用户ID
//    参数例子:
//    user_id=1
//    成功返回：
//    { response: "success", qrcode: "二维码url", grade: 3(等级), ktb: kt币, vip_count: 3(剩余场次), 等级百分比: "17.777%" }
}
