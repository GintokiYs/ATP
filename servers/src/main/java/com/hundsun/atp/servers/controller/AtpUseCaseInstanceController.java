package com.hundsun.atp.servers.controller;

import com.hundsun.atp.api.AtpUseCaseInstanceService;
import com.hundsun.atp.api.taginfo.AtpTagInfoService;
import com.hundsun.atp.common.domain.dto.CaseTestRequest;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/useCaseInstance")
@Validated
public class AtpUseCaseInstanceController {

    @Autowired
    private AtpTagInfoService atpTagInfoService;

    @Autowired
    private AtpUseCaseInstanceService atpUseCaseInstanceService;

//    //用例实例打标签（在用例实例上新增标签，再选中，相当于把选中的标签的List传给AtpUseCaseInstanceDto）
//    @PostMapping("/edittag")
//    @ApiOperation("用例实例上新增标签")
//    public RpcResultDTO<Boolean> editInstanceTag(@Validated @RequestBody InterfaceUsecaseInstanceDto interfaceUsecaseInstanceDto){
//        return atpUseCaseInstanceService.editInstanceTag(interfaceUsecaseInstanceDto);
//    }

    @PostMapping("/caseRun")
    @ApiOperation("新建测试用例")
    public RpcResultDTO<String> caseRun(@Validated @RequestBody CaseTestRequest caseTestRequest) {
        return atpUseCaseInstanceService.caseRun(caseTestRequest);
    }



}
