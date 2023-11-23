package com.hundsun.atp.servers.controller;

import com.hundsun.atp.api.folder.AtpFolderService;
import com.hundsun.atp.common.annotation.DynamicSwitchDataSource;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.folder.AtpTreeQueryDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hundsun.atp.common.constant.GlobalConstants.DATA_SOURCE_DATASOURCE01;

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
@Api(tags = "目录管理")
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
     * @param atpTreeQueryDto
     * @return
     */
    @PostMapping("/selectFlatFolders")
    @ApiOperation("目录树展示")
    @DynamicSwitchDataSource(DATA_SOURCE_DATASOURCE01)
    RpcResultDTO<List<AtpCommonFolderVo>> selectFlatFolders(@Validated @RequestBody AtpTreeQueryDto atpTreeQueryDto) {
        return atpFolderService.selectFlatFolders(atpTreeQueryDto.getProjectId());
    }

    /**
     * 编辑目录(适用于目录整体信息的编辑以及目录属性：执行配置的编辑更新)
     *
     * @param atpCommonFolderDto
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("编辑目录")
    RpcResultDTO<Boolean> update(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto) {
        String id = atpCommonFolderDto.getId();
        return atpFolderService.update(atpCommonFolderDto);
    }

    /**
     * 删除目录(假删)
     *
     * @param atpCommonFolderDto
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除目录")
    RpcResultDTO<Boolean> delete(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto) {
        String id = atpCommonFolderDto.getId();
        String operatorCode = atpCommonFolderDto.getOperatorCode();
        Integer folderType = atpCommonFolderDto.getFolderType();
        return atpFolderService.delete(id, folderType, operatorCode);
    }

    /**
     * 目录详情展示
     *
     * @param atpCommonFolderDto
     * @return
     */
    @PostMapping("/select")
    @ApiOperation("目录详情展示")
    RpcResultDTO<AtpCommonFolderVo> select(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto) {
        return atpFolderService.selectById(atpCommonFolderDto.getId());
    }
}
