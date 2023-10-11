package com.hundsun.atp.servers.service.business;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.FolderTypeEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpCommonFolderMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.servers.service.convert.CommonFolderConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 通用文件夹处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpCommonFolderBusiness extends ServiceImpl<AtpCommonFolderMapper, AtpCommonFolder> {

    @Autowired
    private AtpCommonFolderMapper atpCommonFolderMapper;

    @Autowired
    private CommonFolderConvert commonFolderConvert;

    public AtpCommonFolderVo create(AtpCommonFolderDto folderDto) {
        // 根据空间Id、文件夹类型及文件夹名称 校验是否存在同名
        FolderTypeEnum folderTypeEnum = FolderTypeEnum.getByCode(folderDto.getFolderType());
        Precondition.checkArgument(!validateFolderName(folderDto.getFolderName(), folderDto.getProjectId(), folderTypeEnum), "10000013");


        folderDto.setCreateUser(folderDto.getOperatorCode());
        folderDto.setUpdateUser(folderDto.getOperatorCode());
        String parentId = folderDto.getParentId();
        QueryWrapper<AtpCommonFolder> parentFolderQueryWrapper = new QueryWrapper<>();
        HashMap<String, Object> parentFolderQueryMap = MapUtil.newHashMap();

        parentFolderQueryMap.put("id", parentId);
        parentFolderQueryMap.put("enabled", EnableEnum.VALID.getCode());
        AtpCommonFolder parentFolder = atpCommonFolderMapper.selectOne(parentFolderQueryWrapper.allEq(parentFolderQueryMap));
        Precondition.checkNotNull(parentFolder, "10000002", "父节点");

        // 只有父节点为PROJECT的才能创建项目文件夹(PROJECT)
        if (FolderTypeEnum.PROJECT == folderTypeEnum &&
                !FolderTypeEnum.PROJECT.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000114");
        }

        // 只有父节点为PROJECT的才能创建用例类别文件夹(CATEGORY)
        if (FolderTypeEnum.CATEGORY == folderTypeEnum &&
                !FolderTypeEnum.PROJECT.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000114");
        }

        // 只有父节点为CATEGORY的才能创建用例集(USECASE)
        if (FolderTypeEnum.USECASE == folderTypeEnum &&
                !FolderTypeEnum.CATEGORY.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000115");
        }
        folderDto.setParentUtreeid(parentFolder.getUtreeid());


        return insert(folderDto);
    }

    public AtpCommonFolderVo insert(AtpCommonFolderDto folderDto) {
        folderDto.setCreateUser(folderDto.getOperatorCode());
        AtpCommonFolder atpCommonFolder = commonFolderConvert.toModel(folderDto);
        AtpCommonFolderVo atpCommonFolderVo = commonFolderConvert.toVo(atpCommonFolder);
        String parentId = folderDto.getParentId();
        String folderName = folderDto.getFolderName();

        // 判断父文件夹是否存在
        AtpCommonFolder commonFolder = atpCommonFolderMapper.selectById(parentId);
        Precondition.checkNotNull(commonFolder.getId(), "10000002", "folder_id");
        validateFolderName(folderDto.getProjectId(), folderName, FolderTypeEnum.getByCode(folderDto.getFolderType()));
        atpCommonFolder.setParentUtreeid(commonFolder.getUtreeid());

        // 生成uid
        atpCommonFolder.setId(IdUtil.simpleUUID());
        atpCommonFolder.setUtreeid(IdUtil.simpleUUID());
        atpCommonFolder.setProjectId(folderDto.getProjectId());

        atpCommonFolder.setCreateTime(new Date());
        atpCommonFolder.setUpdateTime(new Date());
        atpCommonFolder.setUpdateUser(atpCommonFolder.getCreateUser());

        // 插入数据
        atpCommonFolderMapper.insert(atpCommonFolder);
        atpCommonFolderVo.setId(atpCommonFolder.getId());
        atpCommonFolderVo.setUtreeid(atpCommonFolder.getUtreeid());
        atpCommonFolderVo.setCreateTime(atpCommonFolder.getCreateTime());
        return atpCommonFolderVo;
    }

    /**
     * 判断是否存在同名文件夹
     *
     * @param projectId
     * @param folderName
     * @param typeEnums
     * @return
     */
    public boolean validateFolderName(String projectId, String folderName, FolderTypeEnum typeEnums) {
        QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();

        queryMap.put("project_id", projectId);
        queryMap.put("folder_type", typeEnums.getCode());
        queryMap.put("folder_name", folderName);
        queryMap.put("enabled", EnableEnum.VALID.getCode());
        QueryWrapper<AtpCommonFolder> commonFolderQueryWrapper = queryWrapper.allEq(queryMap);
        Long commonFolderCount = atpCommonFolderMapper.selectCount(commonFolderQueryWrapper);
        Precondition.checkIndexGreaterZero(Convert.toInt(commonFolderCount), "10000003", folderName);
        return true;
    }

    public List<AtpCommonFolderVo> selectFlatFolders(String projectId) {
        // 获取当前空间下所有的Folder
        QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();

        queryMap.put("project_id", projectId);
        queryMap.put("enabled", EnableEnum.VALID.getCode());
        QueryWrapper<AtpCommonFolder> commonFolderQueryWrapper = queryWrapper.allEq(queryMap).or().isNull("project_id");
        List<AtpCommonFolder> atpCommonFolders = atpCommonFolderMapper.selectList(commonFolderQueryWrapper);

        return commonFolderConvert.toVoList(atpCommonFolders);
    }

    public Boolean update(AtpCommonFolderDto atpCommonFolderDto) {
        // 校验要更新目录的名称是否合规（重名---可以调用validateFolderName方法）

        // 更新的话推荐走 智能替换而不是全覆盖
        return null;
    }
}
