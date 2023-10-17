package com.hundsun.atp.servers.service.convert;

import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.persister.model.AtpTagInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagInfoConvert {
    TagInfoConvert INSTANCE = Mappers.getMapper(TagInfoConvert.class);

    AtpTagInfoVo toVo(AtpTagInfo atpTagInfo);

}
