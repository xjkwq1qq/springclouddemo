package com.deloitte.tac.api.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import java.util.Set;

/**
 * @Description cookie设置
 * @Author qiangwang
 * @Createion Date 20/11/2017
 */
public class CookieSetFilter extends ZuulFilter {
    public static final String key_Splite = ";";
    public static final String key_Equals = "=";
    public static final String key_Cookie = "Cookie";
    public static final String key_FilterType = "pre";

    @Override
    public String filterType() {
        return key_FilterType;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Cookie[] cookies = ctx.getRequest().getCookies();
        //需要做一些cookie相关的utils那些设置进去 那些不设置进去 这样的 不能老这么for循环啊
        //当然也可以不用这样的形式 每次都带上token的方式 这样就不知道是否可以直接用 spring session里 是否需要自己做redis的session管理
        if (cookies != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < cookies.length; i++) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(key_Splite);
                }
                stringBuilder.append(cookies[i].getName()).append(key_Equals).append(cookies[i].getValue());
            }
            ctx.addZuulRequestHeader(key_Cookie, stringBuilder.toString());
        }
        return null;
    }
}
