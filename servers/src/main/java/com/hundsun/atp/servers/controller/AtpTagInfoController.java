package com.hundsun.atp.servers.controller;

import com.hundsun.atp.api.taginfo.AtpTagInfoService;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoQueryDto;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private AtpTagInfoService atpTagInfoService;

    @PostMapping("/create")
    @ApiOperation("新建标签")
    public RpcResultDTO<Boolean> createTagInfo(@Validated @RequestBody AtpTagInfoDto tagInfoDto) {

        return atpTagInfoService.createTagInfo(tagInfoDto);
    }

    @PostMapping("/delete")
    @ApiOperation("删除标签")
    @Transactional
    public RpcResultDTO<Boolean> deleteTagInfo(@Validated @RequestBody AtpTagInfoDto tagInfoDto) {

        return atpTagInfoService.deleteTagInfo(tagInfoDto);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑标签信息")
    public RpcResultDTO<Boolean> editTagInfo(@Validated @RequestBody AtpTagInfoDto tagInfoDto) {

        return atpTagInfoService.editTagInfo(tagInfoDto);
    }

    @PostMapping("/queryAll")
    @ApiOperation("查询所有标签")
    public RpcResultDTO<List<AtpTagInfoVo>> queryTagInfoAll() {

        return atpTagInfoService.queryTagInfoAll();
    }

    @PostMapping("/query")
    @ApiOperation("查询标签")
    public RpcResultDTO<List<AtpTagInfoVo>> queryTagInfo(@Validated @RequestBody AtpTagInfoQueryDto atpTagInfoQueryDto) {

        return atpTagInfoService.queryTagInfo(atpTagInfoQueryDto);
    }

}
