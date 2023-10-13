package com.hundsun.atp.servers.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.hundsun.atp.api.AtpUseCaseInstanceService;
import com.hundsun.atp.common.domain.dto.CaseTestRequest;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpCommonFolderBusiness;
import com.hundsun.atp.servers.service.business.AtpInterfaceUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseParams;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseRunBusiness;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 测试用例实例 服务实现类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpUseCaseInstanceServiceImpl implements AtpUseCaseInstanceService {

    private static Logger logger = LoggerFactory.getLogger(AtpUseCaseInstanceServiceImpl.class);

    @Autowired
    private AtpTagInfoBusiness atpTagInfoBusiness;

    @Autowired
    private AtpCommonFolderBusiness atpCommonFolderBusiness;

    private UseCaseBusinessFactory useCaseBusinessFactory;

    @Override
    public RpcResultDTO<Boolean> testTCaseList(CaseTestRequest caseTestRequest) {
        List<Long> caseIdList = caseTestRequest.getCaseIdList();
        Long foldId = caseTestRequest.getFolderId();
        AtpCommonFolder atpCommonFolder = atpCommonFolderBusiness.getById(foldId);
        AtpInterfaceUseCaseBusiness atpInterfaceUseCaseBusiness = (AtpInterfaceUseCaseBusiness)useCaseBusinessFactory.buildBusiness(UseCaseTypeEnum.INTERFACE);
        List<AtpUseCase> caseList = atpInterfaceUseCaseBusiness.queryUserCaseByCaseIdList(caseIdList);
        List<JsonNode> testResultList = new ArrayList<>();
        for (AtpUseCase atpUseCase : caseList) {
            HttpPostCaseParams httpPostCaseParams = new HttpPostCaseParams();
            httpPostCaseParams.settCaseJson(atpUseCase.getInterfaceContent());
            // todo 获取测试集对应的URL
            String forlderName = atpCommonFolder.getFolderName();
            String url = "";
            httpPostCaseParams.setUrl(url);
            try {
                JsonNode jsonNode = atpInterfaceUseCaseBusiness.testCase(httpPostCaseParams);
                testResultList.add(jsonNode);
            } catch (IOException ioException){
                logger.error("HttpPost用例执行异常, 用例名称：{}, 用例id：{}。", atpUseCase.getName(), atpUseCase.getCaseId());
            }
        }

        // todo 保存/校验testResultList
        return null;
    }
}
