package com.deloitte.tac.api.gateway.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class GetIp {
    static final String key_unknown = "unknown";
    static final String key_X_Forwarded_For = "X-Forwarded-For";
    static final String key_Proxy_Client_IP = "Proxy-Client-IP";
    static final String key_WL_Proxy_Client_IP = "WL-Proxy-Client-IP";
    static final String key_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    static final String key_HTTP_X_FORWARDED = "HTTP_X_FORWARDED";
    static final String key_HTTP_X_CLUSTER_CLIENT_IP = "HTTP_X_CLUSTER_CLIENT_IP";
    static final String key_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    static final String key_HTTP_FORWARDED_FOR = "HTTP_FORWARDED_FOR";
    static final String key_HTTP_VIA = "HTTP_VIA";
    static final String key_REMOTE_ADDR = "REMOTE_ADDR";
    static final String key_split = ",";

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(key_X_Forwarded_For);
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_Proxy_Client_IP);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_WL_Proxy_Client_IP);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_X_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_X_FORWARDED);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_X_CLUSTER_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_HTTP_VIA);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(key_REMOTE_ADDR);
        }
        if (ip == null || ip.length() == 0 || key_unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip)) {
            String[] ips = ip.split(key_split);
            ip = ips[0];
        }
        return ip;

    }
}
