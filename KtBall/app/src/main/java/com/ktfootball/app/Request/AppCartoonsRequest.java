package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.AppCartoons;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class AppCartoonsRequest extends BaseRestRequest<AppCartoons> {

    public AppCartoonsRequest(String url) {
        super(url);
    }

    public AppCartoonsRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public AppCartoons parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
//        LogUtils.e(result);
        AppCartoons baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, AppCartoons.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new AppCartoons();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
