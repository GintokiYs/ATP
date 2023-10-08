package com.hundsun.atp.servers.security;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

@Component
public class AtpAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(AtpAuthenticationEntryPoint.class);

    @Autowired
    private ObjectMapper objectMapper;

    private RedirectStrategy redirectStrategy = (RedirectStrategy) new DefaultRedirectStrategy();

    private boolean forwardToDestination = false;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        if (log.isDebugEnabled()) {
            log.debug("未登录");
        }
        RpcResultDTO<String> dto = RpcResultUtils.error(401, "100102", null);
        response.getWriter().write(this.objectMapper.writeValueAsString(dto));
    }
}