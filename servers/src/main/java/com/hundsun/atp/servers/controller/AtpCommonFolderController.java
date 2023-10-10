package com.hundsun.atp.servers.controller;

import com.hundsun.atp.api.folder.AtpFolderService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "文件夹管理")
@RestController
@RequestMapping("/commonFolder")
@Validated
public class AtpCommonFolderController {
    @Autowired
    private AtpFolderService atpFolderService;

    /**
     * 创建文件夹
     *
     * @param atpCommonFolderDto
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("新建目录")
    RpcResultDTO<AtpCommonFolderVo> create(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto) {
        return atpFolderService.create(atpCommonFolderDto);
    }

    /**
     * 目录树展示,获取平铺的文件夹
     *
     * @param projectId
     * @return
     */
    @PostMapping("/selectFlatFolders")
    @ApiOperation("目录树展示")
    RpcResultDTO<List<AtpCommonFolderVo>> selectFlatFolders(@RequestParam String projectId) {
        return atpFolderService.selectFlatFolders(projectId);
    }
}
