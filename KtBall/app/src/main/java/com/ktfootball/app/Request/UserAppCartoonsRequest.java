package com.ktfootball.app.Request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseEntity;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.UserAppCartoons;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jy on 16/6/14.
 */
public class UserAppCartoonsRequest extends BaseRestRequest<UserAppCartoons> {

    public UserAppCartoonsRequest(String url) {
        super(url);
    }

    public UserAppCartoonsRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public UserAppCartoons parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        UserAppCartoons baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, UserAppCartoons.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new UserAppCartoons();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
