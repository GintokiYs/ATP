package com.hundsun.atp.api;

import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;

import java.util.List;

public interface AtpUsecaseService {
    // 用例新建（编辑、删除、查询）
    public RpcResultDTO<Boolean> createUseCase(AbstractUsecaseDto usecase);

    RpcResultDTO<List<AtpUseCaseStatistics>> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto);

    // 用例执行、用例详情查询
}
