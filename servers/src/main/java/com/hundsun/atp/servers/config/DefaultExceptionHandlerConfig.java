package com.hundsun.atp.servers.config;


import com.hundsun.atp.common.domain.entity.ServerResponseEntity;
import com.hundsun.atp.common.enums.ResponseEnum;
import com.hundsun.atp.common.exception.AtpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一的异常处理类
 *
 * @author yeyh33975
 * @date 2023/9/27
 */
@RestControllerAdvice
public class DefaultExceptionHandlerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandlerConfig.class);

    @ExceptionHandler(AtpException.class)
    public ResponseEntity<ServerResponseEntity<Object>> mall4cloudExceptionHandler(AtpException e) {
        logger.error("mall4cloudExceptionHandler", e);

        ResponseEnum responseEnum = e.getResponseEnum();
        // 失败返回失败消息 + 状态码
        if (responseEnum != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(responseEnum, e.getObject()));
        }
        // 失败返回消息 状态码固定为直接显示消息的状态码
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.showFailMsg(e.getMessage()));
    }
}