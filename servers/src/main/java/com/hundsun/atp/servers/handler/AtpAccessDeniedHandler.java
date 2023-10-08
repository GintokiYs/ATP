package com.hundsun.atp.servers.handler;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AtpAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(AtpAccessDeniedHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        if (log.isDebugEnabled()) {
            log.debug("没有访问权限");
        }
        RpcResultDTO dto = RpcResultUtils.error(403, "100103", null);
        response.getWriter().write(this.objectMapper.writeValueAsString(dto));
    }
}