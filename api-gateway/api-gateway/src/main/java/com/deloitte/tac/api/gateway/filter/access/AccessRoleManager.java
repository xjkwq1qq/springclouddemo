package com.deloitte.tac.api.gateway.filter.access;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description 访问规则管理
 * @Author qiangwang
 * @Createion Date 30/11/2017
 */
@Slf4j
@Service
public class AccessRoleManager {

    @Value("${access.check.use}")
    private boolean checkUse;

    @Autowired
    private AccessRoleRegister accessRoleRegister;

    public boolean pass(String url, String ip) {
        if (!checkUse) {
            return true;
        }
        List<AccessRole> accessRoles = accessRoleRegister.getAccessRoles();
        //验证是否通过
        if (checkPass(url, ip, accessRoles)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 验证是否被限制
     *
     * @param url
     * @param ip
     * @param accessRoles
     * @return true_被限制，false_不被限制
     */
    private boolean checkPass(String url, String ip, List<AccessRole> accessRoles) {
        log.info("checkPass in url:{} ip:{}", url,ip);
        if (!CollectionUtils.isEmpty(accessRoles)) {
            for (AccessRole accessRole : accessRoles) {
                if (checkPattern(url, accessRole.getCheckUrlPatterns())) {
                    //url匹配上
                    if (accessRole.getPassIpPatterns() == null || accessRole.getPassIpPatterns().length == 0) {
                        //不允许ip通过
                        return false;
                    } else {
                        if (checkPattern(ip, accessRole.getPassIpPatterns())) {
                            //匹配上pass的ip,pass
                            return true;
                        } else {
                            //未匹配上，非法访问
                            return false;
                        }
                    }
                }
            }
        }
        log.info("checkPass out url:{} ip:{}", url,ip);
        //所有url规则未匹配，未限制
        return true;
    }

    /**
     * 验证是否匹配
     *
     * @param str
     * @param patterns
     * @return
     */
    private boolean checkPattern(String str, Pattern[] patterns) {
        if (patterns != null && StringUtils.isNotBlank(str)) {
            for (Pattern pattern : patterns) {
                if (pattern.matcher(str).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
