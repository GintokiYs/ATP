package com.hundsun.atp.servers.service.business;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpRefTagUseCaseMapper;
import com.hundsun.atp.persister.mapper.AtpTagInfoMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用例与标签之间的关联关系 处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpRefTagUseCaseBusiness extends ServiceImpl<AtpRefTagUseCaseMapper, AtpRefTagUseCase> {

    @Autowired
    private AtpRefTagUseCaseMapper atpRefTagUseCaseMapper;

    public Boolean creatRefTagUsecase(AtpRefTagUseCase atpRefTagUseCase) {
        // 根据tag_id、case_id、project_id、folder_id 来校验这个表里是否存在同一个tag了,如果存在就不做操作，如果不存在就插入
        if (!validateTagExist(atpRefTagUseCase.getTagId(),atpRefTagUseCase.getCaseId(),atpRefTagUseCase.getProjectId(),atpRefTagUseCase.getFolderId())){
            int insertCount = atpRefTagUseCaseMapper.insert(atpRefTagUseCase);
            return insertCount > 0;
        }
        return false;
    }

    public boolean validateTagExist(String tagId, String caseId, String projectId, String folderId) {
        QueryWrapper<AtpRefTagUseCase> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();
        queryMap.put("tag_id", tagId);
        queryMap.put("case_id", caseId);
        queryMap.put("project_id", projectId);
        queryMap.put("folder_id", folderId);
        QueryWrapper<AtpRefTagUseCase> refTagUseCaseQueryWrapper = queryWrapper.allEq(queryMap);
        Long tagInfoCount = atpRefTagUseCaseMapper.selectCount(refTagUseCaseQueryWrapper);
        return tagInfoCount > 0;
    }

    public List<AtpRefTagUseCase> queryByCaseId(String caseId) {
        HashMap<String, Object> queryMap = MapUtil.newHashMap();
        queryMap.put("case_id", caseId);
        List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseMapper.selectByMap(queryMap);
        return atpRefTagUseCases;
    }

    public List<AtpRefTagUseCase> queryByFolderId(String folderId) {
        HashMap<String, Object> queryMap = MapUtil.newHashMap();
        queryMap.put("folder_id", folderId);
        List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseMapper.selectByMap(queryMap);
        return atpRefTagUseCases;
    }

    public List<AtpRefTagUseCase> queryByTagIds(ArrayList<String> tagIds) {
        List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseMapper.selectByTagIds(tagIds);
        return atpRefTagUseCases;
    }

    public List<String> queryTagIdsByCaseId(String caseId) {
        List<String> tagIds = atpRefTagUseCaseMapper.selectTagIdsByCaseId(caseId);
        return tagIds;
    }

    public void deleteByCaseId(String caseId) {
        atpRefTagUseCaseMapper.deleteByCaseId(caseId);
    }
}
