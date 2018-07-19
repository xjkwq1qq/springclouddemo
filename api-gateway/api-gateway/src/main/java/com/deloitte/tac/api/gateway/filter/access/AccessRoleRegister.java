package com.deloitte.tac.api.gateway.filter.access;

import com.deloitte.tac.api.gateway.utils.Jackson2ResourceReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description 访问规则注册类
 * @Author qiangwang
 * @Createion Date 12/12/2017
 */
@Service
public class AccessRoleRegister {
    private List<AccessRole> accessRoles;

    @Value("${access.check.rolePath}")
    private String rolePath;

    @Value("${access.check.use}")
    private boolean checkUse;

    @PostConstruct
    public void init() throws IOException {
        if (!checkUse) {
            return;
        }
        if (accessRoles == null) {
            if (StringUtils.isNotBlank(rolePath)) {
                //读取资源
                Resource resource = new ClassPathResource(rolePath);
                accessRoles = Jackson2ResourceReader.readListFromResource(resource, AccessRole.class);

                //初始化正则表达式
                for (AccessRole accessRole : accessRoles) {
                    accessRole.setCheckUrlPatterns(parseToPattern(accessRole.getCheckUrls()));
                    accessRole.setPassIpPatterns(parseToPattern(accessRole.getPassIps()));
                }
            }
        }
    }

    public List<AccessRole> getAccessRoles() {
        return this.accessRoles;
    }

    /**
     * 转换为正则表达式规则
     *
     * @param regexs
     * @return
     */
    private Pattern[] parseToPattern(String[] regexs) {
        if (regexs != null && regexs.length > 0) {
            List<Pattern> regexPatternsList = new ArrayList<>(regexs.length);
            for (String regex : regexs) {
                if (StringUtils.isNotBlank(regex.trim())) {
                    regexPatternsList.add(Pattern.compile(regex));
                }
            }
            return regexPatternsList.toArray(new Pattern[regexPatternsList.size()]);
        }
        return null;
    }
}
