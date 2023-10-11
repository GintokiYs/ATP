package com.hundsun.atp.servers.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签明细 前端控制器
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping("/tagInfo")
@Validated
public class AtpTagInfoController {

}
