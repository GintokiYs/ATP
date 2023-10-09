package com.hundsun.atp.servers.service.business;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.folder.CommonFolderDto;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.FolderTypeEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpCommonFolderMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    public void create(CommonFolderDto folderDto) {
        // 根据空间Id、文件夹类型及文件夹名称 校验是否存在同名

        Precondition.checkArgument(!validateFolderName(folderDto.getFolderName(), folderDto.getProjectId(), FolderTypeEnum.getByCode(folderDto.getFolderType())), "10000013");


        folderDto.setCreateUser(folderDto.getOperatorCode());
        folderDto.setUpdateUser(folderDto.getOperatorCode());
        String parentId = folderDto.getParentId();
        QueryWrapper<AtpCommonFolder> parentFolderQueryWrapper = new QueryWrapper<>();
        HashMap<String, Object> parentFolderQueryMap = MapUtil.newHashMap();

        parentFolderQueryMap.put("parent_id", parentId);
        parentFolderQueryMap.put("project_id", folderDto.getProjectId());
        parentFolderQueryMap.put("enabled", EnableEnum.VALID.getCode());
        AtpCommonFolder parentFolder = atpCommonFolderMapper.selectOne(parentFolderQueryWrapper.allEq(parentFolderQueryMap));
        Precondition.checkNotNull(parentFolder, "10000002", new String[]{"父节点"});

        FolderTypeEnum folderTypeEnum = (FolderTypeEnum) EnumUtils.getEnumBycode(FolderTypeEnum.class, folderDto.getFolderType().intValue());
        // 只有根目录或者父节点为ORMAL的才能创建流程
        if (FolderTypeEnum.BUSINESS == folderTypeEnum &&
                !FolderTypeEnum.NORMAL.getCode().equals(parentFolder.getFolderType()) &&
                !GlobalConstants.DEFAULT_FOLDER_ROOT_ID.equals(parentFolder.getId()))
            throw Precondition.exceptionHandle("10000114", new String[0]);
        if (FolderTypeEnum.NORMAL == folderTypeEnum &&
                !FolderTypeEnum.NORMAL.getCode().equals(parentFolder.getFolderType()) &&
                !GlobalConstants.DEFAULT_FOLDER_ROOT_ID.equals(parentFolder.getId()))
            throw Precondition.exceptionHandle("10000115", new String[0]);
        if (FolderTypeEnum.DATA_INTEGRATION == folderTypeEnum || FolderTypeEnum.DATA_CAL == folderTypeEnum || FolderTypeEnum.UNIVERSAL == folderTypeEnum)
            throw Precondition.exceptionHandle("10000116", new String[]{((FolderTypeEnum) EnumUtils.getEnumBycode(FolderTypeEnum.class, folderDto.getFolderType().intValue())).getName()});
        folderDto.setP_uid(parentFolder.getUid());
        String parentExtCategoryId = parentFolder.getExtCategoryId();
        folderDto.setExtParentCategoryId(parentExtCategoryId);
        folderDto.setCanModify(Integer.valueOf(1));
        insert(folderDto, false);
    }

    private boolean validateFolderName(String folderName, String projectId, FolderTypeEnum typeEnums) {
    }
}
