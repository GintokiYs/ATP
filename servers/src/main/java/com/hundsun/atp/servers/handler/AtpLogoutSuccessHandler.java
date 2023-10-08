package com.hundsun.atp.servers.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AtpLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AtpLogoutSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        if (log.isDebugEnabled()) {
            log.debug("成功登出");
        }
        RpcResultDTO<String> dto = RpcResultUtils.suc("logout successfully!");
        response.getWriter().write(this.objectMapper.writeValueAsString(dto));
    }
}
