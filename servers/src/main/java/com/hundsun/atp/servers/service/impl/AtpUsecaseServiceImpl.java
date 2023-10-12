package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hundsun.atp.api.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import com.hundsun.atp.common.enums.ExecuteStatusEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpUseCase;

import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import com.hundsun.atp.servers.service.convert.UseCaseConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AtpUsecaseServiceImpl implements AtpUsecaseService {
    @Autowired
    private UseCaseBusinessFactory useCaseBusinessFactory;

    @Autowired
    private AbstractUseCaseBusiness abstractUseCaseBusiness;

    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    @Override
    public RpcResultDTO<Boolean> createUseCase(AbstractUsecaseDto usecase) {
        // 校验

        // 根据type类型获取不同的business
        UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecase.getCaseType());
        AbstractUseCaseBusiness abstractUseCaseBusiness = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
        List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.generateInsertRecord(usecase);
        boolean b = abstractUseCaseBusiness.saveBatch(atpUseCases);
        // 标签关系添加
        return RpcResultUtils.suc(true);
    }

    @Override
    public RpcResultDTO<List<AtpUseCaseStatistics>> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto) {
        List<AtpUseCaseStatistics> result = new ArrayList<>();
        // 根据目录id,用例名称和 实例执行结果 查询所有用例
        List<AtpUseCaseWithInstance> atpUseCaseWithInstances =
                atpUseCaseMapper.selectUseCaseInfo(queryUsecaseDto.getFoldId(), queryUsecaseDto.getName(), queryUsecaseDto.getCheckResult());
        Map<String, List<AtpUseCaseWithInstance>> collect = atpUseCaseWithInstances.stream().collect(Collectors.groupingBy(AtpUseCaseWithInstance::getCaseId));
        for (Map.Entry<String, List<AtpUseCaseWithInstance>> entry : collect.entrySet()) {
            List<AtpUseCaseWithInstance> entryValue = entry.getValue();
            if (CollUtil.isNotEmpty(entryValue)) {
                // 拿到最新business的用例实例结果
                AtpUseCaseWithInstance atpUseCaseWithInstance = entryValue.get(0);
                int totalInstanceCount = entryValue.size();
                int sucInstanceCount = 0;
                int failInstanceCount = 0;
                // 统计实例个数、成功个数、失败个数，计算成功率、失败率
                for (AtpUseCaseWithInstance atpUseCaseWithInstanceItem : entryValue) {
                    if (ExecuteStatusEnum.SUCCESS.getCode().equalsIgnoreCase(atpUseCaseWithInstanceItem.getExecuteStatus())) {
                        sucInstanceCount++;
                    }
                    if (ExecuteStatusEnum.FAIL.getCode().equalsIgnoreCase(atpUseCaseWithInstanceItem.getExecuteStatus())) {
                        failInstanceCount++;
                    }
                }
                AtpUseCaseStatistics atpUseCaseStatistics = UseCaseConvert.INSTANCE.enhanceStatistics(atpUseCaseWithInstance);
                atpUseCaseStatistics.setTotalCount(totalInstanceCount);
                atpUseCaseStatistics.setSuccessCount(sucInstanceCount);
                atpUseCaseStatistics.setSuccessRate(100 * sucInstanceCount / totalInstanceCount);
                atpUseCaseStatistics.setFailCount(failInstanceCount);
                atpUseCaseStatistics.setFailRate(100 * failInstanceCount / totalInstanceCount);
                result.add(atpUseCaseStatistics);
            }
        }
        // 分页

        // 组装标签
        return RpcResultUtils.suc(result);
    }

    // 用例执行、用例详情查询
}
