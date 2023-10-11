package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.GptCaseInfo;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.model.AtpUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用例详情 处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpInterfaceUseCaseBusiness extends AbstractUseCaseBusiness {

    @Override
    public UseCaseTypeEnum getUseCaseTypeEnum() {
        return UseCaseTypeEnum.INTERFACE;
    }

    @Override
    public List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) {
        // 如果

        GptCaseInfo gptCaseInfo = ((InterfaceUsecaseDto) usecase).getGptCaseInfo();
        if (gptCaseInfo != null) {

        }
        AtpUseCase atpUseCase = AtpUseCase.builder()
                .id(IdUtil.simpleUUID())
                .caseId(IdUtil.simpleUUID())
                .caseType(usecase.getCaseType())
                .folderId(usecase.getFolderId())
                .checkRule(usecase.getCheckRule())
                .enabled(1)
                .build();
        return null;
    }
}
