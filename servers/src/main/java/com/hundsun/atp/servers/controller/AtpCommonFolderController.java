package com.hundsun.atp.servers.controller;

import com.hundsun.atp.api.folder.AtpFolderService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/commonFolder")
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
    RpcResultDTO<AtpCommonFolderDto> create(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto) {
        return atpFolderService.create(atpCommonFolderDto);
    }

}
