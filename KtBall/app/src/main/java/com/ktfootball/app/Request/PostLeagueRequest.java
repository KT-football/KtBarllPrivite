package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.PostLeague;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/23.
 */
public class PostLeagueRequest extends BaseRestRequest<PostLeague> {

    public PostLeagueRequest(String url) {
        super(url);
    }

    public PostLeagueRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public PostLeague parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        PostLeague baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, PostLeague.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new PostLeague();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
