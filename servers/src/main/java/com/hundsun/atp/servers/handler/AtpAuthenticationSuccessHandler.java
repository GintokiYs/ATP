package com.hundsun.atp.servers.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AtpAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AtpAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (log.isDebugEnabled()) {
            log.debug("登录成功");
        }
        RpcResultDTO<String> dto = RpcResultUtils.suc("login success");
        response.getWriter().write(this.objectMapper.writeValueAsString(dto));
    }
}