package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.UserAppCartoonsStudy;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class UserAppCartoonsStudyRequest extends BaseRestRequest<UserAppCartoonsStudy> {

    public UserAppCartoonsStudyRequest(String url) {
        super(url);
    }

    public UserAppCartoonsStudyRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public UserAppCartoonsStudy parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        UserAppCartoonsStudy baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, UserAppCartoonsStudy.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new UserAppCartoonsStudy();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
