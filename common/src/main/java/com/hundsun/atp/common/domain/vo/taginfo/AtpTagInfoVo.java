package com.hundsun.atp.common.domain.vo.taginfo;

import com.hundsun.atp.common.domain.vo.AtpBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpTagInfo对象")
@AllArgsConstructor
@NoArgsConstructor
public class AtpTagInfoVo extends AtpBaseVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("tag")
    private String tagId;

    @ApiModelProperty("标签Key值")
    private String tagKey;

    @ApiModelProperty("标签value值")
    private String tagValue;

    @ApiModelProperty("是否可用")
    private Integer enabled;

    @ApiModelProperty("用例集id")
    private String folderId;
}
