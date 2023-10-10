package com.hundsun.atp.servers.service.convert;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonFolderConvert {

    //    @Mappings({@Mapping(source = "p_id", target = "parentId"), @Mapping(source = "p_uid", target = "parentUid")})
    AtpCommonFolder toModel(AtpCommonFolderDto paramAtpCommonFolderDto);

    AtpCommonFolderVo toVo(AtpCommonFolderDto paramAtpCommonFolderDto);

    //    @Mappings({@Mapping(source = "parentId", target = "p_id"), @Mapping(source = "parentUid", target = "p_uid")})
    AtpCommonFolderDto toDto(AtpCommonFolder atpCommonFolder);

//    List<CommonFolderDto> toDtoList(List<BdpStudioCommonFolder> paramList);
}
