package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.GptCaseInfo;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.prompt.LLMEnum;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.prompt.LLMApiUtils;
import com.hundsun.atp.servers.prompt.tcase.PostInterfaceCaseCreate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoc18zNjE0MSIsImlhdCI6MTY5NDA4NDk5MiwiZXhwIjoxNzAyNzI0OTkyfQ.E6AFtY2WD17BOK5kBK6UMPH2hxVSfbWBIx6K7eRcQoE";
    private static final String API_URL = "http://10.20.33.13:8090/uis/chat/completions";

    @Override
    public UseCaseTypeEnum getUseCaseTypeEnum() {
        return UseCaseTypeEnum.INTERFACE;
    }

    @Override
    public List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) throws Exception {
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
        LLMApiUtils.init(API_KEY, API_URL, LLMEnum.GPT3_5);
        String javaCode = gptCaseInfo.getGptInterfaceDes();
        String dependentClass = gptCaseInfo.getGptInterfaceComplement();
        ArrayNode caseArray = PostInterfaceCaseCreate.createTestCaseByJavaCode(javaCode, dependentClass, gptCaseInfo.getGptCaseNum());
        String gptCaseNamePrefix = gptCaseInfo.getGptCaseNamePrefix();
        long gptCaseNameSuffix = System.currentTimeMillis();
        for (JsonNode jsonNode : caseArray) {
            ObjectMapper objectMapper = new ObjectMapper();
            String caseStr = objectMapper.writeValueAsString(jsonNode);
            gptCaseNameSuffix++;
            AtpUseCase atpUseCase = AtpUseCase.builder()
                    .id(IdUtil.simpleUUID())
                    .caseId(IdUtil.simpleUUID())
                    .caseType(usecase.getCaseType())
                    .name(gptCaseNamePrefix + "_" + gptCaseNameSuffix)
                    .folderId(usecase.getFolderId())
                    .interfaceContent(caseStr)
                    .checkRule(null)
                    .executeConfig(JSON.toJSONString(usecase.getExecuteConfig()))
                    .createUser(usecase.getOperatorCode())
                    .updateUser(usecase.getOperatorCode())
                    .enabled(EnableEnum.VALID.getCode())
                    .build();
            result.add(atpUseCase);
        }


        return result;
    }
}
