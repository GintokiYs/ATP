package com.hundsun.atp.servers.service.impl;


import com.hundsun.atp.api.taginfo.AtpTagInfoService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoQueryDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.AtpRefTagUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import com.hundsun.atp.servers.service.business.AtpUseCaseBusiness;
import com.hundsun.atp.servers.service.business.AtpUseCaseInstanceBusiness;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签明细 服务实现类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpTagInfoServiceImpl implements AtpTagInfoService {

    @Autowired
    private AtpTagInfoBusiness atpTagInfoBusiness;

    @Autowired
    private AtpRefTagUseCaseBusiness atpRefTagUseCaseBusiness;


    @Override
    public RpcResultDTO<Boolean> createTagInfo(AtpTagInfoDto tagInfoDto) {
        return RpcResultUtils.suc(atpTagInfoBusiness.createTagInfo(tagInfoDto));
    }

    @Override
    public RpcResultDTO<Boolean> editTagInfo(AtpTagInfoDto tagInfoDto) {
        return RpcResultUtils.suc(atpTagInfoBusiness.editTagInfo(tagInfoDto));
    }

    @Override
    public RpcResultDTO<Boolean> deleteTagInfo(AtpTagInfoDto tagInfoDto) {
        //先删除tag_info表里的数据，再删关联表数据
        String id = tagInfoDto.getId();
        return RpcResultUtils.suc(atpTagInfoBusiness.deleteTagAndRef(id));
    }

    @Override
    public RpcResultDTO<List<AtpTagInfoVo>> queryTagInfo(AtpTagInfoQueryDto atpTagInfoQueryDto) {
        return RpcResultUtils.suc(atpTagInfoBusiness.queryTagInfo(atpTagInfoQueryDto.getCaseId(), atpTagInfoQueryDto.getProjectId()));
    }

    //用例查询用例集标签集合
    @Override
    public RpcResultDTO<List<AtpTagInfoVo>> queryFolderTags(AtpCommonFolderDto atpCommonFolderDto) {
        try {
            String folderId = atpCommonFolderDto.getId();
            List<AtpTagInfo> atpTagInfos = atpTagInfoBusiness.queryByFolderId(folderId);
            //转成VO
            ArrayList<AtpTagInfoVo> atpTagInfoVos = new ArrayList<>();
            for (AtpTagInfo atpTagInfo : atpTagInfos) {
                AtpTagInfoVo atpTagInfoVo = new AtpTagInfoVo();
                BeanUtils.copyProperties(atpTagInfo, atpTagInfoVo);
                atpTagInfoVos.add(atpTagInfoVo);
            }
            return RpcResultUtils.suc(atpTagInfoVos);
        } catch (BeansException e) {
            return RpcResultUtils.error("200000003", "获取用例标签失败" + e.getMessage());
        }

    }

    @Override
    public RpcResultDTO<List<AtpTagInfoVo>> queryTagInfoAll() {
        return RpcResultUtils.suc(atpTagInfoBusiness.queryTagInfoAll());
    }
}
