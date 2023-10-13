package com.hundsun.atp.api;

import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.github.pagehelper.PageInfo;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.domain.vo.usecase.InterfaceUsecaseVo;

import java.util.List;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;

import java.util.List;

public interface AtpUsecaseService {
    // 用例新建（编辑、删除、查询）
    RpcResultDTO<Boolean> create(AbstractUsecaseDto usecase);

    RpcResultDTO<PageInfo<AtpUseCaseStatistics>> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto);

    RpcResultDTO<Boolean> update(AbstractUsecaseDto usecaseDto);

    RpcResultDTO<Boolean> delete(DeleteUsecaseDto deleteUsecaseDto);

    RpcResultDTO<Boolean> editUsecaseTags(InterfaceUsecaseDto interfaceUsecaseDto);

    RpcResultDTO<List<AtpTagInfoVo>> queryUsecaseTags(InterfaceUsecaseDto interfaceUsecaseDto);

    RpcResultDTO<List<InterfaceUsecaseVo>> queryCasesByTags(List<AtpTagInfoDto> atpTagInfoDtos);

    // 用例执行、用例详情查询
}
