package com.hundsun.atp.servers.filter;

import com.alibaba.fastjson.JSONObject;
import com.hundsun.atp.servers.handler.AtpAuthenticationFailureHandler;
import com.hundsun.atp.servers.handler.AtpAuthenticationSuccessHandler;
import com.hundsun.atp.servers.security.AtpAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.servers.handler
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-07 10:04
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-07 10:04
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Slf4j
@Component
public class AtpUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements InitializingBean {

    @Autowired
    private AtpAuthenticationSuccessHandler atpAuthenticationSuccessHandler;

    @Autowired
    private AtpAuthenticationFailureHandler atpAuthenticationFailureHandler;

    @Autowired
    private AtpAuthenticationProvider atpAuthenticationProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Map<String, String> map = null;
        String username = null;
        String password = null;
        try {
            map = JSONObject.parseObject(request.getInputStream(), Map.class);
            username = map.get("username");
            password = map.get("password");
//            password = RSAUtil.privateDecrypt(password, RSAUtil.getPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnn7ZUr+fNmjR/HgahN399x+tYZwO1hQl1SPzlCSs7waQxIw02fUenPrYT6GN9jNWZUzwtBJvmcrMKxdquRuSJQIDAQABAkAFw/eD2xSflhCNbpEIZErowm48LsEbaIA30Kb7rJnd2ACD7AX90YQIdn5ne9EMaQRjF9/5YqdyZcTlNQYdDSXBAiEA5cYN4r4rhw+gG7G82vK/dp3/CELd58Ywq/CNnoUTyeMCIQCwlg9L5bg2RBAexFAGUWfC/PesLDK5HA/kokdLHxASVwIgXN/TGQV5HpyIQOl0xqM8FLwEK9irTVXksnsHIz3zI7UCIQCBVhd0DphC3s0zy7OVPRCTp3a8G083d49IdXdkVScPVwIgCt+SAb8yyH4EPj2Tzf16MaqZj4VV6uYDE0lvm0i+j64="));
//            password = EncryptUtil.rsaDecode(password);
            return this.atpAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            log.error("前端传输的密码有问题，无法通过rsa解密：{}", password);
            throw new AuthenticationServiceException("authentication failed!");
        }
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationSuccessHandler((AuthenticationSuccessHandler) this.atpAuthenticationSuccessHandler);
        setAuthenticationFailureHandler((AuthenticationFailureHandler) this.atpAuthenticationFailureHandler);
        setFilterProcessesUrl("/login");
    }
}
