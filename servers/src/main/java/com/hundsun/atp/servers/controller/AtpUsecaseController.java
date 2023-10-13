package com.hundsun.atp.servers.controller;

import com.github.pagehelper.PageInfo;
import com.hundsun.atp.api.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.servers.controller
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-09-27 15:56
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-09-27 15:56
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Api(tags = "用例管理")
@RestController
@RequestMapping("/usecase")
@Validated
public class AtpUsecaseController {
    @Autowired
    private AtpUsecaseService atpUsecaseService;


    @PostMapping("/create")
    @ApiOperation("新建测试用例")
    public RpcResultDTO<Boolean> createUseCase(@Validated @RequestBody AbstractUsecaseDto usecaseDto) {
        return atpUsecaseService.createUseCase(usecaseDto);
    }

    @PostMapping("/select")
    @ApiOperation("查询测试用例详情")
    public RpcResultDTO<PageInfo<AtpUseCaseStatistics>> selectUseCaseInfo(@Validated @RequestBody QueryUsecaseDto queryUsecaseDto) {
        return atpUsecaseService.selectUseCaseInfo(queryUsecaseDto);
    }
}
