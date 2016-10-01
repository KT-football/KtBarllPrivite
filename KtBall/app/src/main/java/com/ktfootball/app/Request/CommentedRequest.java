package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Entity.CommentedVideos;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class CommentedRequest extends BaseRestRequest<CommentedVideos> {

    public CommentedRequest(String url) {
        super(url);
    }

    public CommentedRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public CommentedVideos parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
//        LogUtils.e(result);
        CommentedVideos baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, CommentedVideos.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new CommentedVideos();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
