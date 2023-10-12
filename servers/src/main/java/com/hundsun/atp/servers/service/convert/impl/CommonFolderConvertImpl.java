package com.hundsun.atp.servers.service.convert.impl;

import com.alibaba.fastjson.JSON;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo.AtpCommonFolderVoBuilder;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpCommonFolder.AtpCommonFolderBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hundsun.atp.servers.service.convert.CommonFolderConvert;
import org.springframework.stereotype.Component;


@Component
public class CommonFolderConvertImpl implements CommonFolderConvert {

    @Override
    public AtpCommonFolder toModel(AtpCommonFolderDto atpCommonFolderDto) {
        if (atpCommonFolderDto == null) {
            return null;
        }

        AtpCommonFolderBuilder<?, ?> atpCommonFolder = AtpCommonFolder.builder();

        atpCommonFolder.id(atpCommonFolderDto.getId());
        atpCommonFolder.parentId(atpCommonFolderDto.getParentId());
        atpCommonFolder.folderName(atpCommonFolderDto.getFolderName());
        atpCommonFolder.folderType(atpCommonFolderDto.getFolderType());
        atpCommonFolder.parentUtreeid(atpCommonFolderDto.getParentUtreeid());
        atpCommonFolder.utreeid(atpCommonFolderDto.getUtreeid());
        atpCommonFolder.enabled(atpCommonFolderDto.getEnabled());
        atpCommonFolder.projectId(atpCommonFolderDto.getProjectId());
        atpCommonFolder.executeConfig(JSON.toJSONString(atpCommonFolderDto.getExecuteConfig()));
        atpCommonFolder.createUser(atpCommonFolderDto.getCreateUser());
        atpCommonFolder.createTime(atpCommonFolderDto.getCreateTime());
        atpCommonFolder.updateUser(atpCommonFolderDto.getUpdateUser());
        atpCommonFolder.updateTime(atpCommonFolderDto.getUpdateTime());
        atpCommonFolder.remark(atpCommonFolderDto.getRemark());

        return atpCommonFolder.build();
    }

    @Override
    public AtpCommonFolderVo toVo(AtpCommonFolder atpCommonFolder) {
        if (atpCommonFolder == null) {
            return null;
        }

        AtpCommonFolderVoBuilder<?, ?> atpCommonFolderVo = AtpCommonFolderVo.builder();

        atpCommonFolderVo.projectId(atpCommonFolder.getProjectId());
        atpCommonFolderVo.id(atpCommonFolder.getId());
        atpCommonFolderVo.parentId(atpCommonFolder.getParentId());
        atpCommonFolderVo.folderName(atpCommonFolder.getFolderName());
        atpCommonFolderVo.folderType(atpCommonFolder.getFolderType());
        atpCommonFolderVo.parentUtreeid(atpCommonFolder.getParentUtreeid());
        atpCommonFolderVo.utreeid(atpCommonFolder.getUtreeid());
        atpCommonFolderVo.enabled(atpCommonFolder.getEnabled());
        if (atpCommonFolder.getExecuteConfig() != null) {
            atpCommonFolderVo.executeConfig(JSON.parseObject(atpCommonFolder.getExecuteConfig(), new HashMap<String, String>().getClass()));
        }
        atpCommonFolderVo.createUser(atpCommonFolder.getCreateUser());
        atpCommonFolderVo.createTime(atpCommonFolder.getCreateTime());
        atpCommonFolderVo.updateUser(atpCommonFolder.getUpdateUser());
        atpCommonFolderVo.updateTime(atpCommonFolder.getUpdateTime());
        atpCommonFolderVo.remark(atpCommonFolder.getRemark());

        return atpCommonFolderVo.build();
    }

    @Override
    public List<AtpCommonFolderVo> toVoList(List<AtpCommonFolder> atpCommonFolderList) {
        if (atpCommonFolderList == null) {
            return null;
        }

        List<AtpCommonFolderVo> list = new ArrayList<AtpCommonFolderVo>(atpCommonFolderList.size());
        for (AtpCommonFolder atpCommonFolder : atpCommonFolderList) {
            list.add(toVo(atpCommonFolder));
        }

        return list;
    }
}
