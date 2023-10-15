package com.hundsun.atp.api.usecase;

import com.github.pagehelper.PageInfo;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.domain.vo.usecase.InterfaceUsecaseVo;

import java.util.List;

public interface AtpUsecaseService {

    RpcResultDTO<Boolean> create(AbstractUsecaseDto usecase);

    RpcResultDTO<PageInfo<AtpUseCaseStatistics>> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto);

    RpcResultDTO<Boolean> update(AbstractUsecaseDto usecaseDto);

    RpcResultDTO<Boolean> delete(DeleteUsecaseDto deleteUsecaseDto);

    RpcResultDTO<Boolean> editUsecaseTags(List<AtpTagInfoDto> atpTagInfoDtoList, String caseId, String folderId);

    RpcResultDTO<List<InterfaceUsecaseVo>> queryCasesByTags(List<AtpTagInfoDto> atpTagInfoDtos);
}
