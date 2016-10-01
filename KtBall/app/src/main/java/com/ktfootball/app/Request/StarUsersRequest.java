package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Entity.StarUsers;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class StarUsersRequest extends BaseRestRequest<StarUsers> {

    public StarUsersRequest(String url) {
        super(url);
    }

    public StarUsersRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public StarUsers parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
//        LogUtils.e(result);
        StarUsers baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, StarUsers.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new StarUsers();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
