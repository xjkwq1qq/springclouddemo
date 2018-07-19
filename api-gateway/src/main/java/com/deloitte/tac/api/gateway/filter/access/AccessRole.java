package com.deloitte.tac.api.gateway.filter.access;

import lombok.Data;

import java.util.regex.Pattern;

/**
 * @Description 访问规则
 * @Author qiangwang
 * @Createion Date 12/12/2017
 */
@Data
public class AccessRole {
    private String[] checkUrls;
    private String[] passIps;
    private Pattern[] checkUrlPatterns;
    private Pattern[] passIpPatterns;
}
