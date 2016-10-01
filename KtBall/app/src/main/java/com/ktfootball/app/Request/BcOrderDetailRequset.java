package com.ktfootball.app.Request;

import com.frame.app.utils.GsonTools;
import com.ktfootball.app.Base.BaseRestRequest;
import com.ktfootball.app.Entity.BcOrderDetail;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/28.
 */
public class BcOrderDetailRequset extends BaseRestRequest<BcOrderDetail> {

    public BcOrderDetailRequset(String url) {
        super(url);
    }

    public BcOrderDetailRequset(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public BcOrderDetail parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        BcOrderDetail baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, BcOrderDetail.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new BcOrderDetail();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
