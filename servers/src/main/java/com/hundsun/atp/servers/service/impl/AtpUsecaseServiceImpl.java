package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hundsun.atp.api.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpInterfaceUseCaseBusiness;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public RpcResultDTO<Boolean> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto) {
        // 根据目录id,用例名称和 实例执行结果 查询所有用例
        atpUseCaseMapper.selectUseCaseInfo(queryUsecaseDto.getFoldId(),queryUsecaseDto.getName(),queryUsecaseDto.getCheckResult());
        return null;
    }

    // 用例执行、用例详情查询
}
