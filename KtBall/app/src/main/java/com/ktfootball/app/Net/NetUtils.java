package com.ktfootball.app.Net;

import android.util.Pair;

import com.frame.app.base.BaseModel;
import com.frame.app.net.okhttp.callback.ResultCallback;
import com.kt.ktball.App;
import com.kt.ktball.utils.CommonUtil;
import com.kt.ktball.OkHttpClient;
import com.ktfootball.app.Utils.MD5;

import java.io.File;
import java.util.Map;

/**
 * Created by jy on 15/12/11.
 */
public class NetUtils {
    private NetUtils() {
    }

    private static OkHttpClient http = App.httpClient;

    /**
     * 设置联网参数
     *
     * @param method   Http请求类型(HttpMethod.POST)
     * @param url
     * @param params
     * @param callback 回调函数
     */

    public static boolean postLoadData(String url, BaseModel params, ResultCallback callback) {

        if (0 == CommonUtil.isNetworkAvailable(App.getContext())) {
            return false;
        } else {
            http.postAsyn(url, params, callback);
        }
        return true;
    }

    public static boolean getLoadData(String url, ResultCallback callback) {

        if (0 == CommonUtil.isNetworkAvailable(App.getContext())) {
            return false;
        } else {
            http.getAsyn(url, callback);
        }
        return true;
    }

    public static boolean getLoadData(String url, String tag, Map<String, String> params, Map<String, String> headers, ResultCallback callback) {

        if (0 == CommonUtil.isNetworkAvailable(App.getContext())) {
            return false;
        } else {
            http.getAsyn(url, tag, params, headers, callback);
        }
        return true;
    }

    public static boolean getLoadData(String url, String tag, Map<String, String> params, ResultCallback callback) {

        if (0 == CommonUtil.isNetworkAvailable(App.getContext())) {
            return false;
        } else {
            params.put("authenticity_token", MD5.getToken(url));
            http.getAsyn(url, tag, params, callback);
        }
        return true;
    }

    public static boolean upload(String url, String tag, Map<String, String> params,  Pair<String, File>[] files, ResultCallback callback) {

        if (0 == CommonUtil.isNetworkAvailable(App.getContext())) {
            return false;
        } else {
            params.put("authenticity_token",MD5.getToken(url));
            http.upload(url, tag, params, files,callback);
        }
        return true;
    }

}
