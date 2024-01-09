package com.example.gateway.common;

import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

public class FilterError extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(FilterError.class);

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        if (throwable != null) {
            logger.warn(ReflectionToStringBuilder.toString(throwable, ToStringStyle.MULTI_LINE_STYLE));
            ctx.addOriginResponseHeader("Access-Control-Allow-Origin", "*");
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.setResponseBody(JsonUtil.toJSONString(Result.error("Ngoại lệ hệ thống")));
        }
        return null;
    }

}
