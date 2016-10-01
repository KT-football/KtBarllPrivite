package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.UserAppCartoons;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class AppCartoonRequest extends BaseRestRequest<AppCartoon> {

    public AppCartoonRequest(String url) {
        super(url);
    }

    public AppCartoonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public AppCartoon parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        AppCartoon baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, AppCartoon.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new AppCartoon();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
