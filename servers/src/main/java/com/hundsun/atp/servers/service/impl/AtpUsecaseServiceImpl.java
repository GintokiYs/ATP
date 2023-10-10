package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.lang.UUID;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.AtpUseCaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtpUsecaseServiceImpl implements AtpUsecaseService {
    @Autowired
    private AtpUseCaseBusiness atpUseCaseBusiness;

    // 用例新建（编辑、删除、查询）
    @Override
    public Boolean createUsecase(AbstractUsecaseDto usecase) {
        //
        AtpUseCase atpUseCase = AtpUseCase.builder().id(UUID.randomUUID().toString())
                .caseId(UUID.randomUUID().toString())
                .caseType(usecase.getCaseType())
                .folderId(usecase.getFolderId())
                .checkRule(usecase.getCheckRule())
                .enabled(1)
                .build();
        return atpUseCaseBusiness.save(atpUseCase);
    }

    // 用例执行、用例详情查询
}
