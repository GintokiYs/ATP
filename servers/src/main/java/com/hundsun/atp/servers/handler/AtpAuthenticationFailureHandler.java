package com.hundsun.atp.servers.handler;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AtpAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(AtpAuthenticationFailureHandler.class);

    @Value("${app.config.config-with-file-mode-enable}")
    private Boolean enableConfileMode;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (StrUtil.isNotBlank(e.getMessage())) {
            String errorMsg = e.getMessage();
        } else {
            String errorMsg = "登录失败";
        }
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        if (log.isDebugEnabled()) {
            log.debug("认证失败");
        }
        RpcResultDTO<String> dto = null;
        if (this.enableConfileMode.booleanValue()) {
            dto = RpcResultUtils.error(401, "100106", null);
        } else {
            dto = RpcResultUtils.error(401, "100104", null);
        }
        response.getWriter().write(this.objectMapper.writeValueAsString(dto));
    }
}