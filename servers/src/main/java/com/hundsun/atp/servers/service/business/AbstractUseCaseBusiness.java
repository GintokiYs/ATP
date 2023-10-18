package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.enums.ExecuteStatusEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import com.hundsun.atp.servers.service.business.caserun.AbstractCaseRunHandle;
import com.hundsun.atp.servers.service.business.caserun.CaseRunParams;
import com.hundsun.atp.servers.service.business.caserun.CaseRunResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.servers.service.business
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-11 16:38
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-11 16:38
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Slf4j
@Service
public abstract class AbstractUseCaseBusiness<IN extends CaseRunParams, OUT extends CaseRunResult> extends ServiceImpl<AtpUseCaseMapper, AtpUseCase> {

    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    @Autowired
    private AtpUseCaseInstanceBusiness atpUseCaseInstanceBusiness;

    private final AbstractCaseRunHandle<IN, OUT> caseRunHandle;

    public AbstractUseCaseBusiness(AbstractCaseRunHandle<IN, OUT> caseRunHandle) {
        this.caseRunHandle = caseRunHandle;
    }

    public abstract UseCaseTypeEnum getUseCaseTypeEnum();

    public abstract List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) throws Exception;

    public abstract AtpUseCase generateUpdateRecord(AbstractUsecaseDto usecaseDto);


    /**
     * 执行测试用例并获取执行结果
     *
     * @param atpUseCase 测试用例信息
     * @param atpCommonFolder 测试用例集信息
     * @return OUT
     */
    public void testCase(final AtpUseCase atpUseCase, final AtpCommonFolder atpCommonFolder){
        AtpUseCaseInstance atpUseCaseInstance = new AtpUseCaseInstance();
        atpUseCaseInstance.setId(IdUtil.simpleUUID());
        atpUseCaseInstance.setUseCaseId(atpUseCase.getCaseId());
        atpUseCaseInstance.setInstanceId(IdUtil.simpleUUID());
        atpUseCaseInstance.setStartTime(new Date());
        atpUseCaseInstance.setExecuteStatus(ExecuteStatusEnum.RUNNING.getCode());
        atpUseCaseInstanceBusiness.save(atpUseCaseInstance);
        AtpUseCaseInstance newAtpUseCaseInstance = atpUseCaseInstanceBusiness.selectByInstanceId(atpUseCaseInstance.getInstanceId());
        log.debug("准备执行用例{}。", atpUseCase.getName());
        try {
            IN caseRunParams = caseRunHandle.caseTransform(atpUseCase, atpCommonFolder);
            OUT executeResult = caseRunHandle.excuteCase(caseRunParams);
            // todo 需要记录executeResult
            atpUseCaseInstance.setExecuteStatus(ExecuteStatusEnum.SUCCESS.getCode());
        } catch (Exception e){
            log.error("执行用例失败, 用例名称为：{}, 用例id为：{}。", atpUseCase.getName(), atpUseCase.getCaseId(), e);
            newAtpUseCaseInstance.setExecuteStatus(ExecuteStatusEnum.FAIL.getCode());
        } finally {
            newAtpUseCaseInstance.setStopTime(new Date());
            atpUseCaseInstanceBusiness.updateById(newAtpUseCaseInstance);
        }

    }

    public List<AtpUseCase> queryUserCaseByCaseIdList(List<String> caseIdList) {
        // 获取当前空间下所有的Folder
        return atpUseCaseMapper.selectByCaseIds(caseIdList);
    }

    public List<AtpUseCase> queryByCaseIds(ArrayList<String> caseIds) {
        List<AtpUseCase> atpUseCases = atpUseCaseMapper.selectByCaseIds(caseIds);
        return atpUseCases;
    }
}
