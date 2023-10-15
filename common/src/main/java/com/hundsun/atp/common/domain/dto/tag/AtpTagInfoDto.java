package com.hundsun.atp.common.domain.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p>
 * 标签明细
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpTagInfo对象", description = "标签明细")
@AllArgsConstructor
@NoArgsConstructor
public class AtpTagInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("tagId")
    private String tagId;

    @ApiModelProperty("标签Key值")
    private String tagKey;

    @ApiModelProperty("标签value值")
    private String tagValue;

    @ApiModelProperty("空间id")
    private String projectId;
}
