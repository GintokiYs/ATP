package com.hundsun.atp.servers.service.convert.impl;

import com.alibaba.fastjson.JSON;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics.AtpUseCaseStatisticsBuilder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.convert.UseCaseConvert;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.HashMap;

@Component
public class UseCaseConvertImpl implements UseCaseConvert {

    @Override
    public AtpUseCaseStatistics enhanceStatistics(AtpUseCase atpUseCase) {
        if (atpUseCase == null) {
            return null;
        }

        AtpUseCaseStatisticsBuilder<?, ?> atpUseCaseStatistics = AtpUseCaseStatistics.builder();

        atpUseCaseStatistics.id(atpUseCase.getId());
        atpUseCaseStatistics.caseId(atpUseCase.getCaseId());
        atpUseCaseStatistics.name(atpUseCase.getName());
        atpUseCaseStatistics.folderId(atpUseCase.getFolderId());
        atpUseCaseStatistics.checkRule(atpUseCase.getCheckRule());
        atpUseCaseStatistics.caseType(atpUseCase.getCaseType());
        atpUseCaseStatistics.executeConfig(JSON.parseObject(atpUseCase.getExecuteConfig(), new HashMap<String, String>().getClass()));
        atpUseCaseStatistics.enabled(atpUseCase.getEnabled());
        atpUseCaseStatistics.createUser(atpUseCase.getCreateUser());
        atpUseCaseStatistics.createTime(atpUseCase.getCreateTime());
        atpUseCaseStatistics.updateUser(atpUseCase.getUpdateUser());
        atpUseCaseStatistics.updateTime(atpUseCase.getUpdateTime());
        atpUseCaseStatistics.interfaceContent(atpUseCase.getInterfaceContent());
        atpUseCaseStatistics.remark(atpUseCase.getRemark());

        return atpUseCaseStatistics.build();
    }
}
