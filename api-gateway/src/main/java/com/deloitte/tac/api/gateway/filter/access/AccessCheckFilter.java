package com.deloitte.tac.api.gateway.filter.access;

import com.deloitte.tac.api.gateway.utils.GetIp;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description rest 接口filter
 * @Author qiangwang
 * @Createion Date 30/11/2017
 */
@Service
public class AccessCheckFilter extends ZuulFilter {
    static Logger logger = LoggerFactory.getLogger(AccessCheckFilter.class);
    @Autowired
    private AccessRoleManager accessRoleManager;

    public static final String key_FilterType = "pre";

    public static final String pass_css = ".css";
    public static final String pass_js = ".js";
    public static final String pass_png = ".png";
    public static final String pass_jpg = ".jpg";

    @Override
    public String filterType() {
        return key_FilterType;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String url = context.getRequest().getRequestURI();
        if (url.endsWith(pass_css) || url.endsWith(pass_js) || url.endsWith(pass_png) || url.endsWith(pass_jpg)) {
            //如果发现是css或者js文件，直接放行
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String ip = GetIp.getClientIpAddr(context.getRequest());
        String url = context.getRequest().getRequestURI();
        if (!accessRoleManager.pass(url, ip)) {
            logger.error("ip:{}异常访问{}", ip, context.getRequest().getRequestURL().toString());
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
        }
        return null;
    }
}
