package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.hundsun.atp.api.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
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

    @Override
    public Boolean createUsecase(AbstractUsecaseDto usecase) {
        // 校验

        // 根据type类型获取不同的business
        UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecase.getCaseType());
        AbstractUseCaseBusiness abstractUseCaseBusiness = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
        List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.generateInsertRecord(usecase);

        return true;
    }

    // 用例执行、用例详情查询
}
