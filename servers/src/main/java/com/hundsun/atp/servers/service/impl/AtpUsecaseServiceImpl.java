package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.domain.vo.usecase.InterfaceUsecaseVo;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.ExecuteStatusEnum;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import com.hundsun.atp.servers.service.business.*;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import com.hundsun.atp.servers.service.convert.UseCaseConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import com.hundsun.atp.api.AtpUsecaseService;

@Service
@Slf4j
public class AtpUsecaseServiceImpl implements AtpUsecaseService {
    @Autowired
    private UseCaseBusinessFactory useCaseBusinessFactory;

    @Autowired
    private AbstractUseCaseBusiness abstractUseCaseBusiness;

    @Autowired
    private AtpUseCaseBusiness atpUseCaseBusiness;

    @Autowired
    private AtpTagInfoBusiness atpTagInfoBusiness;

    @Autowired
    private AtpRefTagUseCaseBusiness atpRefTagUseCaseBusiness;

    // 用例新建（编辑、删除、查询）
    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    @Autowired
    private AtpUseCaseInstanceBusiness atpUseCaseInstanceBusiness;

    @Autowired
    private UseCaseConvert useCaseConvert;

    @Override
    public RpcResultDTO<Boolean> create(AbstractUsecaseDto usecase) {
        try {
            // 检查要更新的用例名称是否存在
            checkDuplicateUseCase(usecase);
            // 根据type类型获取不同的business
            UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecase.getCaseType());
            AbstractUseCaseBusiness abstractUseCaseBusiness = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
            List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.generateInsertRecord(usecase);
            boolean result = abstractUseCaseBusiness.saveBatch(atpUseCases);
            // 标签关系添加
            List<AtpTagInfoDto> tags = usecase.getTags();
            // 根据标签和atpUseCases ,更新中间关联表信息
            for (AtpUseCase atpUseCase : atpUseCases) {
                editUsecaseTags(tags, atpUseCase.getCaseId());
            }

            if (result) {
                return RpcResultUtils.suc(true);
            } else {
                return RpcResultUtils.error("00000000", "新增数据库记录报错");
            }
        } catch (Exception e) {
            log.error("create useCase fail , error message is :", e);
            return RpcResultUtils.error("00000000", "新增用例失败");
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
        List<String> caseIdList = list.stream().map(AtpUseCase::getId).collect(Collectors.toList());
        QueryWrapper<AtpUseCaseInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("use_case_id", caseIdList);
        if (queryUsecaseDto.getCheckResult() != null) {
            queryWrapper = queryWrapper.eq("execute_status", queryUsecaseDto.getCheckResult());
        }
        List<AtpUseCaseInstance> atpUseCaseInstances = atpUseCaseInstanceBusiness.list(queryWrapper.orderByDesc("bussiness_time"));
        Map<String, List<AtpUseCaseInstance>> withInstanceMap = atpUseCaseInstances.stream()
                .collect(Collectors.groupingBy(AtpUseCaseInstance::getUseCaseId));

        for (AtpUseCase atpUseCase : list) {
            String id = atpUseCase.getId();
            List<AtpUseCaseInstance> atpUseCaseWithInstances1 = withInstanceMap.get(id);
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
                AtpUseCaseStatistics atpUseCaseStatistics = useCaseConvert.enhanceStatistics(atpUseCase);
                AtpUseCaseInstance lastedAtpUseCaseInstance = atpUseCaseWithInstances1.get(0);
                atpUseCaseStatistics.setInstanceId(lastedAtpUseCaseInstance.getInstanceId());
                atpUseCaseStatistics.setBussinessTime(lastedAtpUseCaseInstance.getBussinessTime());
                atpUseCaseStatistics.setExecuteStatus(lastedAtpUseCaseInstance.getExecuteStatus());

                atpUseCaseStatistics.setTotalCount(totalInstanceCount);
                atpUseCaseStatistics.setSuccessCount(sucInstanceCount);
                atpUseCaseStatistics.setSuccessRate(100 * sucInstanceCount / totalInstanceCount);
                atpUseCaseStatistics.setFailCount(failInstanceCount);
                atpUseCaseStatistics.setFailRate(100 * failInstanceCount / totalInstanceCount);
                List<AtpTagInfoVo> tagList = queryUsecaseTags(id);
                atpUseCaseStatistics.setTags(tagList);
                result.add(atpUseCaseStatistics);
            }
        }
        PageInfo<AtpUseCaseStatistics> atpUseCaseStatisticsPageInfo = new PageInfo<>(result);

        return RpcResultUtils.suc(atpUseCaseStatisticsPageInfo);
    }

    @Override
    public RpcResultDTO<Boolean> update(AbstractUsecaseDto usecaseDto) {
        try {
            UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecaseDto.getCaseType());
            AbstractUseCaseBusiness abstractUseCaseBusinessImpl = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
            // 检查要更新的用例名称是否存在
            checkDuplicateUseCase(usecaseDto);
            // 检查用例是否存在
            QueryWrapper<AtpUseCase> queryUseCaseWrapper = new QueryWrapper<>();
            queryUseCaseWrapper.eq("id", usecaseDto.getId()).eq("enabled", EnableEnum.VALID.getCode());
            long useCount = abstractUseCaseBusinessImpl.count(queryUseCaseWrapper);
            Precondition.checkArgument(useCount != 1, "00000000", "数据库中该用例已不存在");

            // 数据库更新用例记录
            AtpUseCase atpUseCase = abstractUseCaseBusinessImpl.generateUpdateRecord(usecaseDto);

            // 根据标签和atpUseCase ,更新中间关联表信息
            editUsecaseTags(usecaseDto.getTags(), atpUseCase.getCaseId());

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

    private void checkDuplicateUseCase(AbstractUsecaseDto usecaseDto) {
        String name = usecaseDto.getName();
        QueryWrapper<AtpUseCase> duplicQueryWrapper = new QueryWrapper<>();
        duplicQueryWrapper.eq("name", name).eq("folder_id", usecaseDto.getFolderId()).eq("enabled", EnableEnum.VALID.getCode());
        long dulpCount = abstractUseCaseBusiness.count(duplicQueryWrapper);
        Precondition.checkIndexGreaterZero(Convert.toInt(dulpCount), "00000000", "用例集中已存在相同的用例名称");
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

    //用例编辑标签
    @Override
    public RpcResultDTO<Boolean> editUsecaseTags(List<AtpTagInfoDto> atpTagInfoDtoList, String caseId) {
        try {
            //校验所选的tag是否有重复（可能它又新增了一个与之前一模一样的tag，然后框选了两个一样的）
//            List<AtpTagInfoDto> atpTagInfoDtoList = interfaceUsecaseDto.getTags();
            //如果有重复就抛异常
            Precondition.checkArgument(!atpTagInfoBusiness.hasDuplicateTagKey(atpTagInfoDtoList), "200000001");
            //再根据caseId去关联表里把对应的tagcase匹配记录删除掉，要先删，再插入
//            String caseId = interfaceUsecaseDto.getCaseId();
            atpRefTagUseCaseBusiness.deleteByCaseId(caseId);
            //再对atp_tag_info这个表里进行insert，如果能找到对应的tagKey就不做操作，找不到就新增
//            atpTagInfoBusiness.processAtpTagInfoList(atpTagInfoDtoList);
            //再对atp_ref_tag_use_case表进行插入操作，使得use_case_instance与tag_info关联起来
//            String folderId = interfaceUsecaseDto.getFolderId();
            for (AtpTagInfoDto dto : atpTagInfoDtoList) {
//                AtpTagInfo atpTagInfo = atpTagInfoBusiness.queryByTagKey(dto.getTagKey());
                String tagId = dto.getId();
                String projectId = dto.getProjectId();
                AtpRefTagUseCase atpRefTagUseCase = new AtpRefTagUseCase();
                atpRefTagUseCase.setTagId(tagId);
                atpRefTagUseCase.setCaseId(caseId);
                atpRefTagUseCase.setProjectId(projectId);
                atpRefTagUseCase.setId(IdUtil.simpleUUID());
//                atpRefTagUseCase.setFolderId(folderId);
                atpRefTagUseCaseBusiness.creatRefTagUsecase(atpRefTagUseCase);

            }
            return RpcResultUtils.suc(true);
        } catch (Exception e) {
            return RpcResultUtils.error("200000002", "编辑标签出问题" + e.getMessage());
        }
    }

    //用例查询标签集合
    public List<AtpTagInfoVo> queryUsecaseTags(String caseId) {
        try {
            //再用这个case_id去查ref_tag_use_case表
            List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseBusiness.queryByCaseId(caseId);
            //接着依次取出tag_id，存到一个List中
            ArrayList<String> tagIds = new ArrayList<>();
            for (AtpRefTagUseCase atpRefTagUseCase : atpRefTagUseCases) {
                tagIds.add(atpRefTagUseCase.getTagId());
            }
            //再从atp_tag_info表里去根据tag_id查表
            List<AtpTagInfo> atpTagInfos = atpTagInfoBusiness.queryByTagIds(tagIds);
            //转成VO
            ArrayList<AtpTagInfoVo> atpTagInfoVos = new ArrayList<>();
            for (AtpTagInfo atpTagInfo : atpTagInfos) {
                AtpTagInfoVo atpTagInfoVo = new AtpTagInfoVo();
                BeanUtils.copyProperties(atpTagInfo, atpTagInfoVo);
                atpTagInfoVos.add(atpTagInfoVo);
            }
            return atpTagInfoVos;
        } catch (Exception e) {
            log.error("error message is: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    public RpcResultDTO<List<InterfaceUsecaseVo>> queryCasesByTags(List<AtpTagInfoDto> atpTagInfoDtos) {
        try {
            //先获取atpTagInfoDtos的tagIds列表
            ArrayList<String> tagIds = new ArrayList<>();
            for (AtpTagInfoDto atpTagInfoDto : atpTagInfoDtos) {
                tagIds.add(atpTagInfoDto.getTagId());
            }
            //再根据tagIds列表去查atp_ref_tag_use_case表的caseIds列表
            List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseBusiness.queryByTagIds(tagIds);
            ArrayList<String> caseIds = new ArrayList<>();
            for (AtpRefTagUseCase atpRefTagUseCase : atpRefTagUseCases) {
                caseIds.add(atpRefTagUseCase.getCaseId());
            }
            //接着去根据caseIds列表去atp_use_case表里去查数据
            List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.queryByCaseIds(caseIds);
            ArrayList<InterfaceUsecaseVo> interfaceUsecaseVos = new ArrayList<>();
            for (AtpUseCase atpUseCase : atpUseCases) {
                InterfaceUsecaseVo interfaceUsecaseVo = new InterfaceUsecaseVo();
                BeanUtils.copyProperties(atpUseCase, interfaceUsecaseVo);
                String caseId = atpUseCase.getCaseId();
                List<String> tagIdsTmp = atpRefTagUseCaseBusiness.queryTagIdsByCaseId(caseId);
                List<AtpTagInfo> atpTagInfos = atpTagInfoBusiness.queryByTagIds(tagIdsTmp);
                List<AtpTagInfoVo> atpTagInfoVos = new ArrayList<>();
                for (AtpTagInfo atpTagInfo : atpTagInfos) {
                    AtpTagInfoVo atpTagInfoVo = new AtpTagInfoVo();
                    BeanUtils.copyProperties(atpTagInfo, atpTagInfoVo);
                    atpTagInfoVos.add(atpTagInfoVo);
                }
                interfaceUsecaseVo.setTags(atpTagInfoVos);
                interfaceUsecaseVos.add(interfaceUsecaseVo);
            }
            return RpcResultUtils.suc(interfaceUsecaseVos);
        } catch (BeansException e) {
            return RpcResultUtils.error("200000004", "查询用例失败" + e.getMessage());
        }
    }


    //用例查询


    // 用例执行、用例详情查询

}
