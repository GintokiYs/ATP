package com.hundsun.atp.api;


import com.github.pagehelper.PageInfo;
import com.hundsun.atp.common.domain.dto.CaseTestRequest;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;

/**
 * <p>
 * 测试用例实例 服务类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
public interface AtpUseCaseInstanceService {

    /**
     * 执行用例
     */
    RpcResultDTO<String> caseRun(CaseTestRequest caseTestRequest);

}
