package com.hundsun.atp.servers.service.impl;

import com.hundsun.atp.api.AtpUseCaseInstanceService;
import com.hundsun.atp.common.domain.dto.CaseTestRequest;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.AtpApplication;
import com.hundsun.atp.servers.service.business.AtpCommonFolderBusiness;
import com.hundsun.atp.servers.service.business.AtpInterfaceUseCaseBusiness;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseRunHandle;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@PrepareForTest()
@Slf4j
public class AtpUseCaseInstanceServiceImplTest {

    @InjectMocks
    private AtpUseCaseInstanceServiceImpl atpUseCaseInstanceService;

    @Mock
    private AtpCommonFolderBusiness atpCommonFolderBusiness;

    @Mock
    private UseCaseBusinessFactory useCaseBusinessFactory;

    @Mock
    private AtpUseCaseMapper atpUseCaseMapper;

    @Test
    public void caseRunTest() {
        AtpCommonFolder mockFolder = new AtpCommonFolder();
        mockFolder.setFolderName("测试用例集");
        mockFolder.setExecuteConfig("{\"url\":\"http://localhost:8080/useCaseInstance/caseRun\"}");
        Mockito.when(atpCommonFolderBusiness.getById(0L)).thenReturn(mockFolder);

        AtpInterfaceUseCaseBusiness mockAtpInterfaceUseCaseBusiness = new AtpInterfaceUseCaseBusiness(new HttpPostCaseRunHandle());

        ReflectionTestUtils.setField(mockAtpInterfaceUseCaseBusiness, "baseMapper", atpUseCaseMapper);

        List<AtpUseCase> mockCaseList = new ArrayList<>();
        AtpUseCase atpUseCase = new AtpUseCase();
        atpUseCase.setInterfaceContent("{\"caseIdList\":[4444444444,5555555555,6666666666,7777777777],\"folderId\":-999}");
        atpUseCase.setCaseId("1");
        atpUseCase.setName("测试用例1");
        mockCaseList.add(atpUseCase);

        Mockito.when(mockAtpInterfaceUseCaseBusiness.queryUserCaseByCaseIdList(any()))
                .thenReturn(mockCaseList);
        Mockito.when(useCaseBusinessFactory.buildBusiness(UseCaseTypeEnum.INTERFACE)).thenReturn(mockAtpInterfaceUseCaseBusiness);


        CaseTestRequest caseTestRequest = new CaseTestRequest();
        List<String> list = new ArrayList<>();
        list.add("4444444444");
        list.add("5555555555");
        list.add("6666666666");
        list.add("7777777777");
        caseTestRequest.setCaseIdList(list);
        caseTestRequest.setFoldId("0");

        RpcResultDTO<String> rpcResult = atpUseCaseInstanceService.caseRun(caseTestRequest);

        log.info(rpcResult.getResult());
    }

}
