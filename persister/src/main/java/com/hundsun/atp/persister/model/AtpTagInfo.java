package com.hundsun.atp.persister.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
@TableName("atp_tag_info")
@ApiModel(value = "AtpTagInfo对象", description = "标签明细")
@AllArgsConstructor
@NoArgsConstructor
public class AtpTagInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("tagId")
    @TableField("tag_id")
    private String tagId;

    @ApiModelProperty("标签Key值")
    @TableField("tag_key")
    private String tagKey;

    @ApiModelProperty("标签value值")
    @TableField("tag_value")
    private String tagValue;

    @ApiModelProperty("是否可用")
    @TableField("enabled")
    private Integer enabled;

    @ApiModelProperty("空间id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("用例集id")
    @TableField("folder_id")
    private String folderId;
}
