package com.hundsun.atp.servers.controller;

import com.github.pagehelper.PageInfo;
import com.hundsun.atp.api.taginfo.AtpTagInfoService;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.domain.vo.usecase.InterfaceUsecaseVo;
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

    @Autowired
    private AtpTagInfoService atpTagInfoService;


    @PostMapping("/create")
    @ApiOperation("新建测试用例")
    public RpcResultDTO<Boolean> create(@Validated @RequestBody AbstractUsecaseDto usecaseDto) {
        return atpUsecaseService.create(usecaseDto);
    }

    @PostMapping("/selectUseCaseInfo")
    @ApiOperation("查询测试用例详情")
    public RpcResultDTO<PageInfo<AtpUseCaseStatistics>> selectUseCaseInfo(@Validated @RequestBody QueryUsecaseDto queryUsecaseDto) {
        return atpUsecaseService.selectUseCaseInfo(queryUsecaseDto);
    }



    //用例打标签（在用例上新增标签，再选中，相当于把选中的标签的List传给AtpUseCaseDto）
    @PostMapping("/edittags")
    @ApiOperation("用例实例上新增标签")
    public RpcResultDTO<Boolean> editUsecaseTags(@Validated @RequestBody InterfaceUsecaseDto interfaceUsecaseDto){
        return atpUsecaseService.editUsecaseTags(interfaceUsecaseDto);

    }

    //用例查询标签集合
    @PostMapping("/querycasetags")
    @ApiOperation("用例查询标签集")
    public RpcResultDTO<List<AtpTagInfoVo>> queryUsecaseTags(@Validated @RequestBody InterfaceUsecaseDto interfaceUsecaseDto){
        return atpUsecaseService.queryUsecaseTags(interfaceUsecaseDto);
    }

    //用例查询用例集的标签集合
    @PostMapping("/queryfoldertags")
    @ApiOperation("用例查询用例集的标签集")
    public RpcResultDTO<List<AtpTagInfoVo>> queryFolderTags(@Validated @RequestBody AtpCommonFolderDto atpCommonFolderDto){
        return atpTagInfoService.queryFolderTags(atpCommonFolderDto);
    }

    //根据筛选的标签展示case列表
    @PostMapping("/querycasesbytags")
    @ApiOperation("根据筛选的标签展示case列表")
    public RpcResultDTO<List<InterfaceUsecaseVo>> queryCasesByTags(@Validated @RequestBody List<AtpTagInfoDto> atpTagInfoDtos){
        return atpUsecaseService.queryCasesByTags(atpTagInfoDtos);
    }

    @PostMapping("/update")
    @ApiOperation("编辑测试用例")
    public RpcResultDTO<Boolean> update(@Validated @RequestBody AbstractUsecaseDto usecaseDto) {
        return atpUsecaseService.update(usecaseDto);
    }

    @PostMapping("/delete")
    @ApiOperation("删除测试用例")
    public RpcResultDTO<Boolean> delete(@Validated @RequestBody DeleteUsecaseDto deleteUsecaseDto) {
        return atpUsecaseService.delete(deleteUsecaseDto);
    }
}
