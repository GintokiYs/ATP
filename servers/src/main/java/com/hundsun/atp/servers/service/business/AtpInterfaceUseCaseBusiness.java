package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.GptCaseInfo;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseParams;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseRunBusiness;
import com.hundsun.atp.servers.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class AtpInterfaceUseCaseBusiness extends AbstractUseCaseBusiness<HttpPostCaseParams, JsonNode, HttpPostCaseRunBusiness> {
    private Logger logger = LoggerFactory.getLogger(AtpInterfaceUseCaseBusiness.class);

    @Override
    public UseCaseTypeEnum getUseCaseTypeEnum() {
        return UseCaseTypeEnum.INTERFACE;
    }

    @Override
    public List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) {
        ArrayList<AtpUseCase> result = new ArrayList<>();
        // 如果 GptCaseInfo 对象为空,则为普通的用例添加
        GptCaseInfo gptCaseInfo = ((InterfaceUsecaseDto) usecase).getGptCaseInfo();
        if (gptCaseInfo == null) {
            AtpUseCase atpUseCase = AtpUseCase.builder()
                    .id(IdUtil.simpleUUID())
                    .caseId(IdUtil.simpleUUID())
                    .caseType(usecase.getCaseType())
                    .name(usecase.getName())
                    .folderId(usecase.getFolderId())
                    .interfaceContent(((InterfaceUsecaseDto) usecase).getInterfaceContent())
                    .checkRule(usecase.getCheckRule())
                    .executeConfig(JSON.toJSONString(usecase.getExecuteConfig()))
                    .createUser(usecase.getOperatorCode())
                    .updateUser(usecase.getOperatorCode())
                    .enabled(EnableEnum.VALID.getCode())
                    .build();
            result.add(atpUseCase);
            return result;
        }
        // 调用大模型接口生成

        return result;
    }


    public static class TestHttpPostCaseParams {
        private AtpUseCase atpUseCase;
        private String url;

        public AtpUseCase getAtpUseCase() {
            return atpUseCase;
        }

        public void setAtpUseCase(AtpUseCase atpUseCase) {
            this.atpUseCase = atpUseCase;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
