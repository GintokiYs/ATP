package com.hundsun.atp.servers.service.convert;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonFolderConvert {

    AtpCommonFolder toModel(AtpCommonFolderDto atpCommonFolderDto);

    AtpCommonFolderVo toVo(AtpCommonFolder atpCommonFolder);

    List<AtpCommonFolderVo> toVoList(List<AtpCommonFolder> atpCommonFolderList);
}
