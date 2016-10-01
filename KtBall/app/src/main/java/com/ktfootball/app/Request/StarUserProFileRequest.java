package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Entity.StarUserProfile;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class StarUserProFileRequest extends BaseRestRequest<StarUserProfile> {

    public StarUserProFileRequest(String url) {
        super(url);
    }

    public StarUserProFileRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public StarUserProfile parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
//        LogUtils.e(result);
        StarUserProfile baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, StarUserProfile.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new StarUserProfile();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
