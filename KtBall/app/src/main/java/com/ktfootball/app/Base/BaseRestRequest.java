package com.ktfootball.app.Base;

import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.RestRequest;

/**
 * Created by jy on 16/6/16.
 */
public abstract class BaseRestRequest<T> extends RestRequest<T> {
    public BaseRestRequest(String url) {
        super(url);
        setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
    }

    public BaseRestRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
    }
}
