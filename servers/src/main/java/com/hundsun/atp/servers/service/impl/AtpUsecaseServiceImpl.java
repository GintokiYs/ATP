package com.hundsun.atp.servers.service.impl;

import cn.hutool.core.util.IdUtil;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.domain.vo.usecase.InterfaceUsecaseVo;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.AtpRefTagUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import com.hundsun.atp.servers.service.business.AtpUseCaseBusiness;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import com.hundsun.atp.servers.service.business.factory.UseCaseBusinessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    @Override
    public RpcResultDTO<Boolean> createUseCase(AbstractUsecaseDto usecase) {
        // 校验

        // 根据type类型获取不同的business
        UseCaseTypeEnum useCaseTypeEnum = UseCaseTypeEnum.getByCode(usecase.getCaseType());
        AbstractUseCaseBusiness abstractUseCaseBusiness = useCaseBusinessFactory.buildBusiness(useCaseTypeEnum);
        List<AtpUseCase> atpUseCases = abstractUseCaseBusiness.generateInsertRecord(usecase);
        boolean b = abstractUseCaseBusiness.saveBatch(atpUseCases);
        // 标签关系添加
        return RpcResultUtils.suc(true);
    }

    @Override
    public RpcResultDTO<Boolean> selectUseCaseInfo(QueryUsecaseDto queryUsecaseDto) {
        // 根据目录id,用例名称和 实例执行结果 查询所有用例
        atpUseCaseMapper.selectUseCaseInfo(queryUsecaseDto.getFoldId(),queryUsecaseDto.getName(),queryUsecaseDto.getCheckResult());
        return null;
    }

    //用例编辑标签
    @Override
    public RpcResultDTO<Boolean> editUsecaseTags(InterfaceUsecaseDto interfaceUsecaseDto) {
        try {
            //校验所选的tag是否有重复（可能它又新增了一个与之前一模一样的tag，然后框选了两个一样的）
            List<AtpTagInfoDto> atpTagInfoDtoList = interfaceUsecaseDto.getTags();
            //如果有重复就抛异常
            Precondition.checkArgument(!atpTagInfoBusiness.hasDuplicateTagKey(atpTagInfoDtoList), "200000001");
            //再根据caseId去关联表里把对应的tagcase匹配记录删除掉，要先删，再插入
            String caseId = interfaceUsecaseDto.getCaseId();
            atpRefTagUseCaseBusiness.deleteByCaseId(caseId);
            //再对atp_tag_info这个表里进行insert，如果能找到对应的tagKey就不做操作，找不到就新增
//            atpTagInfoBusiness.processAtpTagInfoList(atpTagInfoDtoList);
            //再对atp_ref_tag_use_case表进行插入操作，使得use_case_instance与tag_info关联起来
            String folderId = interfaceUsecaseDto.getFolderId();
            for (AtpTagInfoDto dto : atpTagInfoDtoList) {
//                AtpTagInfo atpTagInfo = atpTagInfoBusiness.queryByTagKey(dto.getTagKey());
                String tagId = dto.getTagId();
                String projectId = dto.getProjectId();
                AtpRefTagUseCase atpRefTagUseCase = new AtpRefTagUseCase();
                atpRefTagUseCase.setTagId(tagId);
                atpRefTagUseCase.setCaseId(caseId);
                atpRefTagUseCase.setProjectId(projectId);
                atpRefTagUseCase.setId(IdUtil.simpleUUID());
                atpRefTagUseCase.setFolderId(folderId);
                atpRefTagUseCaseBusiness.creatRefTagUsecase(atpRefTagUseCase);

            }
            return RpcResultUtils.suc(true);
        } catch (Exception e) {
            return RpcResultUtils.error("200000002", "编辑标签出问题" + e.getMessage());
        }
    }

    //用例查询标签集合
    @Override
    public RpcResultDTO<List<AtpTagInfoVo>> queryUsecaseTags(InterfaceUsecaseDto interfaceUsecaseDto) {
        try {
            //拿到这个用例的case_id
            String caseId = interfaceUsecaseDto.getCaseId();
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
            return RpcResultUtils.suc(atpTagInfoVos);
        } catch (Exception e) {
            return RpcResultUtils.error("200000003", "查询用例标签失败" + e.getMessage());
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
            List<AtpUseCase> atpUseCases = atpUseCaseBusiness.queryByCaseIds(caseIds);
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
