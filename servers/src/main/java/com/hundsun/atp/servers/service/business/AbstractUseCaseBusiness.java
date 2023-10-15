package com.hundsun.atp.servers.service.business;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.caserun.ICaseRun;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public abstract class AbstractUseCaseBusiness<IN, OUT, T extends ICaseRun<IN, OUT>> extends ServiceImpl<AtpUseCaseMapper, AtpUseCase> {

    private T iCaseRun;

    public abstract UseCaseTypeEnum getUseCaseTypeEnum();

    public abstract List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) throws Exception;

    public abstract AtpUseCase generateUpdateRecord(AbstractUsecaseDto usecaseDto);
    public abstract List<AtpUseCase> generateInsertRecord(AbstractUsecaseDto usecase) throws Exception ;

    /**
     * 执行测试用例并获取执行结果
     * @param atpUseCase 测试用例信息

     * @return OUT
     */
    public OUT testCase(IN atpUseCase) throws IOException {
        return iCaseRun.excuteHttpPostCase(atpUseCase);
    };

    public List<AtpUseCase> queryUserCaseByCaseIdList(List<Long> caseIdList){
        // 获取当前空间下所有的Folder
        return this.baseMapper.queryUseCaseInfoList(caseIdList);
    }
}
