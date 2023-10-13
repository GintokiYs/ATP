package com.hundsun.atp.api.folder;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;

import java.util.List;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.servers.controller
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-09-27 15:56
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-09-27 15:56
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
public interface AtpFolderService {

    RpcResultDTO<AtpCommonFolderVo> create(AtpCommonFolderDto folderDto);

    RpcResultDTO<List<AtpCommonFolderVo>> selectFlatFolders(String projectId);

    RpcResultDTO<Boolean> update(AtpCommonFolderDto atpCommonFolderDto);

    RpcResultDTO<Boolean> delete(String id, String operatorCode);

    RpcResultDTO<List<AtpCommonFolderVo>> select(String id);

    RpcResultDTO<AtpCommonFolderVo> selectById(String id);


}
