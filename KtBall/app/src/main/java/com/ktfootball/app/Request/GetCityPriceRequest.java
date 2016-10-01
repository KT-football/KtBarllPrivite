package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.GetCityPrice;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/27.
 */
public class GetCityPriceRequest extends BaseRestRequest<GetCityPrice> {

    public GetCityPriceRequest(String url) {
        super(url);
    }

    public GetCityPriceRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public GetCityPrice parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        GetCityPrice baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, GetCityPrice.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new GetCityPrice();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
