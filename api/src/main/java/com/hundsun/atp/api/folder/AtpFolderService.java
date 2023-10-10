package com.hundsun.atp.api.folder;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;

public interface AtpFolderService {
    // 新建项目（更新、删除、查询）
    public RpcResultDTO<AtpCommonFolderDto> create(AtpCommonFolderDto folderDto);
    // 新建测试分类（更新、删除、查询）

    // 新建用例集（更新、删除、查询）
}
