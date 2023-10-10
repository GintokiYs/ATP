package com.hundsun.atp.servers.service.impl;

import com.hundsun.atp.api.folder.AtpFolderService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.servers.service.business.AtpCommonFolderBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtpCommonFolderServiceImpl implements AtpFolderService {
    @Autowired
    private AtpCommonFolderBusiness atpCommonFolderBusiness;

    // 新建项目（更新、删除、查询）
    @Override
    public RpcResultDTO<AtpCommonFolderDto> create(AtpCommonFolderDto folderDto) {
        atpCommonFolderBusiness.create(folderDto);

        return RpcResultUtils.suc(folderDto);
    }
    // 新建测试分类（更新、删除、查询）

    // 新建用例集（更新、删除、查询）
}
