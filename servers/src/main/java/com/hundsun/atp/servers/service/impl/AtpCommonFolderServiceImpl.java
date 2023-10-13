package com.hundsun.atp.servers.service.impl;

import com.hundsun.atp.api.folder.AtpFolderService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.servers.service.business.AtpCommonFolderBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtpCommonFolderServiceImpl implements AtpFolderService {
    @Autowired
    private AtpCommonFolderBusiness atpCommonFolderBusiness;

    /**
     * 创建文件夹
     *
     * @param folderDto
     * @return
     */
    @Override
    public RpcResultDTO<AtpCommonFolderVo> create(AtpCommonFolderDto folderDto) {
        AtpCommonFolderVo atpCommonFolderVo = atpCommonFolderBusiness.create(folderDto);

        return RpcResultUtils.suc(atpCommonFolderVo);
    }

    /**
     * 目录树展示,获取平铺的文件夹
     *
     * @param projectId
     * @return
     */
    @Override
    public RpcResultDTO<List<AtpCommonFolderVo>> selectFlatFolders(String projectId) {
        return RpcResultUtils.suc(atpCommonFolderBusiness.selectFlatFolders(projectId));
    }

    /**
     * 编辑目录(适用于目录整体信息的编辑以及目录属性：执行配置的编辑更新)
     *
     * @param atpCommonFolderDto
     * @return
     */
    @Override
    public RpcResultDTO<Boolean> update(AtpCommonFolderDto atpCommonFolderDto) {
        return RpcResultUtils.suc(atpCommonFolderBusiness.update(atpCommonFolderDto));
    }

    /**
     * 删除目录(假删)
     *
     * @param id
     * @param operatorCode
     * @return
     */
    @Override
    public RpcResultDTO<Boolean> delete(String id, String operatorCode) {
        return null;
    }

    /**
     * 目录详情展示
     *
     * @param id
     * @return
     */
    @Override
    public RpcResultDTO<List<AtpCommonFolderVo>> select(String id) {
        return null;
    }


}
