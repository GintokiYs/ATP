package com.hundsun.atp.api.taginfo;

import com.github.pagehelper.PageInfo;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoQueryDto;
import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.DeleteUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.QueryUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;

import java.util.List;

public interface AtpTagInfoService {

    // 标签新建
    RpcResultDTO<Boolean> createTagInfo(AtpTagInfoDto tagInfoDto);

    // 标签编辑
    RpcResultDTO<Boolean> editTagInfo(AtpTagInfoDto tagInfoDto);

    //标签删除
    RpcResultDTO<Boolean> deleteTagInfo(AtpTagInfoDto tagInfoDto);

    //标签查询
    RpcResultDTO<List<AtpTagInfoVo>> queryTagInfo(AtpTagInfoQueryDto atpTagInfoQueryDto);

    RpcResultDTO<List<AtpTagInfoVo>> queryFolderTags(AtpCommonFolderDto atpCommonFolderDto);

    RpcResultDTO<List<AtpTagInfoVo>> queryTagInfoAll();
}
