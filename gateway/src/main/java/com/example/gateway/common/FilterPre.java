package com.example.gateway.common;

import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import com.google.common.cache.LoadingCache;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

public class FilterPre extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(FilterPre.class);
    private static final String TOKEN = "token";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        String uri = RequestContext.getCurrentContext().getRequest().getServletPath();

        if (uri.contains("/login")) {
            return false;
        }

        if (uri.contains("/v2/api-docs"))
            return false;

        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String userNo = null;
        try {
            userNo = getUserNoByToken(request);
        } catch (Exception e) {
            logger.error("Lỗi hệ thống {}", e.getMessage());
            resp(ctx, e.getMessage(), e.hashCode());
        }

        ctx.getZuulRequestHeaders().put(TOKEN, userNo);
        return null;
    }

    private String getUserNoByToken(HttpServletRequest request) {
        String token = "request.getHeader(TOKEN);";

        String userNo = System.currentTimeMillis()+"";

        if (!StringUtils.hasLength(userNo)) {
            userNo = String.valueOf(System.currentTimeMillis());
        }

//        //set lại thời gian token khi người dùng request
        stringRedisTemplate.opsForValue().set(userNo, token, 1, TimeUnit.HOURS);

        return userNo;
    }

    private void resp(RequestContext ctx, String msg, int code) {
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setResponseBody(JsonUtil.toJSONString(Result.error(code, msg)));
        ctx.addOriginResponseHeader("Access-Control-Allow-Origin", "*");
    }


}
