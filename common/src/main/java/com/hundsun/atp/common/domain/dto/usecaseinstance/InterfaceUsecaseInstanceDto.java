package com.hundsun.atp.common.domain.dto.usecaseinstance;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonTypeName(value = "1")
public class InterfaceUsecaseInstanceDto extends AbstractUsecaseInstanceDto{

    @ApiModelProperty("标签列表")
    private List<AtpTagInfoDto> atpTagInfoDtoList;

}
