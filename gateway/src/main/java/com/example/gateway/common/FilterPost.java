package com.example.gateway.common;

import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import com.example.core.domain.result.ResultEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

public class FilterPost extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // Xử lý ngoại lệ
        if (ctx.getResponseStatusCode() != 200) {
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.addOriginResponseHeader("Access-Control-Allow-Origin", "*");
            ctx.setResponseBody(JsonUtil.toJSONString(Result.error(ResultEnum.SERVICE_ABNORMAL)));
        }
        return false;
    }

    @Override
    public Object run() {
        return null;
    }

}
