package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.AddToUserAppCartoons;
import com.ktfootball.app.Entity.GetCitys;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/23.
 */
public class GetCitysRequest extends BaseRestRequest<GetCitys> {

    public GetCitysRequest(String url) {
        super(url);
    }

    public GetCitysRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public GetCitys parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        GetCitys baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, GetCitys.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new GetCitys();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
