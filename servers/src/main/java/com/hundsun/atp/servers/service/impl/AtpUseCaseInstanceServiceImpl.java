package com.hundsun.atp.servers.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.api.AtpUseCaseInstanceService;
import com.hundsun.atp.common.domain.dto.CaseTestRequest;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.*;
import com.hundsun.atp.servers.service.business.caserun.CaseRunResult;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseParams;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 测试用例实例 服务实现类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
@Slf4j
public class AtpUseCaseInstanceServiceImpl implements AtpUseCaseInstanceService {

    @Autowired
    private AtpCommonFolderBusiness atpCommonFolderBusiness;

    @Autowired
    private AtpUseCaseInstanceBusiness atpUseCaseInstanceBusiness;

    @Autowired
    private UseCaseBusinessFactory useCaseBusinessFactory;

    // Create an ExecutorService for the asynchronous execution of test cases

    private ExecutorService executorService = Executors.newFixedThreadPool(5); // You can adjust the pool size as needed


    @Override
    public RpcResultDTO<String> caseRun(CaseTestRequest caseTestRequest) {
        try {
            List<String> caseIdList = caseTestRequest.getCaseIdList();
            String foldId = caseTestRequest.getFoldId();
            AtpCommonFolder atpCommonFolder = atpCommonFolderBusiness.getById(foldId);
            AbstractUseCaseBusiness atpInterfaceUseCaseBusiness = useCaseBusinessFactory.buildBusiness(UseCaseTypeEnum.INTERFACE);
//        final List<AtpUseCase> caseList = atpInterfaceUseCaseBusiness.queryUserCaseByCaseIdList(caseIdList);
            final List<AtpUseCase> caseList = atpInterfaceUseCaseBusiness.listByIds(caseIdList);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (AtpUseCase atpUseCase : caseList) {
                        try {
                            atpInterfaceUseCaseBusiness.testCase(atpUseCase, atpCommonFolder);
                        } catch (Exception e) {
                            log.error("HttpPost用例执行异常, 用例名称：{}, 用例id：{}。", atpUseCase.getName(), atpUseCase.getCaseId(), e);
                        }
                    }
                }
            });
            return RpcResultUtils.suc("用例执行完成");
        } catch (Exception e) {
            log.error("error message is:", e);
            return RpcResultUtils.error("000000", "用例执行异常");
        }
    }


}
