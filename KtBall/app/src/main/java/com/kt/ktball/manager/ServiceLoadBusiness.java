package com.kt.ktball.manager;

import android.util.Pair;

import com.frame.app.base.BaseBusiness;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.net.okhttp.callback.ResultCallback;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Constants;
import com.kt.ktball.entity.Token;
import com.kt.ktball.myclass.MyAlertDialog;
import com.ktfootball.app.Net.NetUtils;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.Map;

/**
 * Created by Neil Zheng on 12/24.
 */
public class ServiceLoadBusiness extends BaseBusiness {

    public final static int GET_CLUB_DATA_SUCCESS = 7191;
    public final static int GET_CLUB_DATA_FAILURE = 7192;


    public final static int GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS = 7193;

    public final static int GET_CLUB_SCHOOL_CLASS_DATA_COUNT_SUCCESS = 7194;

    public final static int GET_CLUB_DATA_COUNT_FAILURE = 7196;

    public final static int GET_CLUB_DATA_COUNT_SUCCESS = 7195;

    public final static int GET_USER_INFO_SUCCESS = 7196;
    public final static int GET_USER_INFO_FAILURE = 7197;

    public final static int GET_ROLE_SUCCESS = 7198;
    public final static int GET_ROLE_FAILURE = 7199;

    private static ServiceLoadBusiness cb = new ServiceLoadBusiness();

    private ServiceLoadBusiness() {
    }

    public static ServiceLoadBusiness getInstance() {
        return cb;
    }

    /**
     * 获得班级数据
     * @param act
     * @param club_id
     */
    public void getClubSchoolClassData(final BaseActivity act,String club_id) {
        NetUtils.getLoadData(Constants.GET_CLUB_SCHOOL_CLASS_DATA + club_id, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }

            @Override
            public void onResponse(final String response) {
                LogUtils.e(response);
                sendMessage(act, GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS, response);
            }
        });
    }


    /**
     * 获取token
     * @param act
     * @param club_id
     */
    public boolean getRole(final BaseActivity act,String tag ,Map<String ,String> param) {
        return NetUtils.getLoadData(Constants.GET_ROLE,tag,param, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }

            @Override
            public void onResponse(final String response) {
                LogUtils.e("getUserInfo = "+response);
                isSucces(act, response, GET_ROLE_SUCCESS,
                        GET_ROLE_FAILURE, Token.class);
            }
        });
    }

    /**
     * 更改用户信息
     * @param act
     * @param club_id
     */
    public void updateUserInfo(final BaseActivity act,String tag ,Map<String ,String> param,Pair<String, File>[] files) {
        NetUtils.upload(Constants.UPDATE_USER_INFO,tag,param,files, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }

            @Override
            public void onResponse(final String response) {
                LogUtils.e("updateUserInfo = "+response);
//                isSucces(act, response, GET_ROLE_SUCCESS,
//                        GET_ROLE_FAILURE, Token.class);
            }
        });
    }
}
