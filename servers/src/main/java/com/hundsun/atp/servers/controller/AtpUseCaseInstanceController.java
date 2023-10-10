package com.hundsun.atp.servers.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试用例实例 前端控制器
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Api(tags = "用例实例管理")
@RestController
@RequestMapping("/atpUseCaseInstance")
@Validated
public class AtpUseCaseInstanceController {

}
