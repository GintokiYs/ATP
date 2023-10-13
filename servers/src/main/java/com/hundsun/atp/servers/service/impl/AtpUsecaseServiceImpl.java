package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hundsun.atp.api.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.ExecuteStatusEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpUseCase;

import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpUseCaseInstanceBusiness;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import com.hundsun.atp.servers.service.convert.UseCaseConvert;
import io.swagger.annotations.ExampleProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AtpUsecaseServiceImpl implements AtpUsecaseService {
    @Autowired
    private UseCaseBusinessFactory useCaseBusinessFactory;

    @Autowired
    private AbstractUseCaseBusiness abstractUseCaseBusiness;

    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    @Autowired
    private AtpUseCaseInstanceBusiness atpUseCaseInstanceBusiness;

    @Override
    public RpcResultDTO<Boolean> create(AbstractUsecaseDto usecase) {
        try {
            // 校验

            // 根据type类型获取不同的business
            UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecase.getCaseType());
            AbstractUseCaseBusiness abstractUseCaseBusiness = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
            List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.generateInsertRecord(usecase);
            boolean b = abstractUseCaseBusiness.saveBatch(atpUseCases);
            // 标签关系添加
            return RpcResultUtils.suc(true);
        } catch (Exception e) {
            log.error("createUseCase fail , error message is :", e);
            return RpcResultUtils.suc(false);
        }
    }

    @Override
    public RpcResultDTO<PageInfo<AtpUseCaseStatistics>> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto) {
        List<AtpUseCaseStatistics> result = new ArrayList<>();
        // 根据目录id,用例名称和 实例执行结果 查询所有用例
        PageHelper.startPage(queryUsecaseDto.getPageNum(), queryUsecaseDto.getPageSize());
        List<AtpUseCase> atpUseCases =
                atpUseCaseMapper.selectUseCaseInfo(queryUsecaseDto.getFoldId(), queryUsecaseDto.getName());
        PageInfo<AtpUseCase> atpUseCasePageInfo = new PageInfo<>(atpUseCases);
        List<AtpUseCase> list = atpUseCasePageInfo.getList();
        if (CollUtil.isEmpty(list)) {
            PageInfo<AtpUseCaseStatistics> atpUseCaseStatisticsPageInfo = new PageInfo<>(result);
            return RpcResultUtils.suc((atpUseCaseStatisticsPageInfo));
        }
        List<String> caseIdList = list.stream().map(AtpUseCase::getCaseId).collect(Collectors.toList());
        QueryWrapper<AtpUseCaseInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("use_case_id", caseIdList).eq("execute_status", queryUsecaseDto.getCheckResult()).orderByDesc("bussiness_time");
        List<AtpUseCaseInstance> atpUseCaseInstances = atpUseCaseInstanceBusiness.list(queryWrapper);
        Map<String, List<AtpUseCaseInstance>> withInstanceMap = atpUseCaseInstances.stream()
                .collect(Collectors.groupingBy(AtpUseCaseInstance::getUseCaseId));

        for (AtpUseCase atpUseCase : list) {
            String caseId = atpUseCase.getCaseId();
            List<AtpUseCaseInstance> atpUseCaseWithInstances1 = withInstanceMap.get(caseId);
            if (CollUtil.isNotEmpty(atpUseCaseWithInstances1)) {
                int totalInstanceCount = atpUseCaseWithInstances1.size();
                int sucInstanceCount = 0;
                int failInstanceCount = 0;
                // 统计实例个数、成功个数、失败个数，计算成功率、失败率
                for (AtpUseCaseInstance atpUseCaseInstance : atpUseCaseWithInstances1) {
                    if (ExecuteStatusEnum.SUCCESS.getCode().equalsIgnoreCase(atpUseCaseInstance.getExecuteStatus())) {
                        sucInstanceCount++;
                    }
                    if (ExecuteStatusEnum.FAIL.getCode().equalsIgnoreCase(atpUseCaseInstance.getExecuteStatus())) {
                        failInstanceCount++;
                    }
                }
                // 填入最新business的用例实例结果
                AtpUseCaseStatistics atpUseCaseStatistics = UseCaseConvert.INSTANCE.enhanceStatistics(atpUseCase);
                AtpUseCaseInstance lastedAtpUseCaseInstance = atpUseCaseWithInstances1.get(0);
                atpUseCaseStatistics.setInstanceId(lastedAtpUseCaseInstance.getInstanceId());
                atpUseCaseStatistics.setBussinessTime(lastedAtpUseCaseInstance.getBussinessTime());
                atpUseCaseStatistics.setExecuteStatus(lastedAtpUseCaseInstance.getExecuteStatus());

                atpUseCaseStatistics.setTotalCount(totalInstanceCount);
                atpUseCaseStatistics.setSuccessCount(sucInstanceCount);
                atpUseCaseStatistics.setSuccessRate(100 * sucInstanceCount / totalInstanceCount);
                atpUseCaseStatistics.setFailCount(failInstanceCount);
                atpUseCaseStatistics.setFailRate(100 * failInstanceCount / totalInstanceCount);
                result.add(atpUseCaseStatistics);
            }
        }
        PageInfo<AtpUseCaseStatistics> atpUseCaseStatisticsPageInfo = new PageInfo<>(result);

        // 组装标签
        return RpcResultUtils.suc(atpUseCaseStatisticsPageInfo);
    }

    @Override
    public RpcResultDTO<Boolean> update(AbstractUsecaseDto usecaseDto) {
        try {
            UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecaseDto.getCaseType());
            AbstractUseCaseBusiness abstractUseCaseBusinessImpl = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
            // 检查要更新的用例名称是否存在
            String name = usecaseDto.getName();
            QueryWrapper<AtpUseCase> duplicQueryWrapper = new QueryWrapper<>();
            duplicQueryWrapper.eq("name", name).eq("folder_id", usecaseDto.getFolderId()).eq("enabled", EnableEnum.VALID.getCode());
            long dulpCount = abstractUseCaseBusinessImpl.count(duplicQueryWrapper);
            Precondition.checkIndexGreaterZero(Convert.toInt(dulpCount), "00000000", "用例集中已存在相同的用例名称");
            // 检查用例是否存在
            QueryWrapper<AtpUseCase> queryUseCaseWrapper = new QueryWrapper<>();
            queryUseCaseWrapper.eq("id", usecaseDto.getId()).eq("enabled", EnableEnum.VALID.getCode());
            long useCount = abstractUseCaseBusinessImpl.count(queryUseCaseWrapper);
            Precondition.checkArgument(useCount != 1, "00000000", "数据库中该用例已不存在");

            // 数据库更新用例记录
            AtpUseCase atpUseCase = abstractUseCaseBusinessImpl.generateUpdateRecord(usecaseDto);

            if (abstractUseCaseBusinessImpl.updateById(atpUseCase)) {
                return RpcResultUtils.suc(true);
            } else {
                return RpcResultUtils.error("00000000", "更新数据库记录报错");
            }
        } catch (Exception e) {
            log.error("update usecase fail,error message is: ", e);
            return RpcResultUtils.error("00000000", "更新用例失败");
        }
    }

    @Override
    public RpcResultDTO<Boolean> delete(DeleteUsecaseDto deleteUsecaseDto) {
        try {
            AtpUseCase atpUseCase = AtpUseCase.builder()
                    .id(deleteUsecaseDto.getId())
                    .enabled(EnableEnum.DELETE.getCode())
                    .updateUser(deleteUsecaseDto.getOperatorCode())
                    .updateTime(DateUtil.date())
                    .build();
            if (abstractUseCaseBusiness.updateById(atpUseCase)) {
                return RpcResultUtils.suc(true);
            } else {
                return RpcResultUtils.error("00000000", "删除数据库记录报错");
            }
        } catch (Exception e) {
            log.error("delete usecase fail,error message is: ", e);
            return RpcResultUtils.error("00000000", "删除用例失败");
        }
    }

    // 用例执行、用例详情查询
}
