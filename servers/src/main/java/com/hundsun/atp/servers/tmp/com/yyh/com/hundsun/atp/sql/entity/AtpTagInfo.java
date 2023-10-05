package com.yyh.com.hundsun.atp.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签明细
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("atp_tag_info")
@ApiModel(value = "AtpTagInfo对象", description = "标签明细")
public class AtpTagInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("tag")
    private String tagId;

    @ApiModelProperty("标签Key值")
    private String tagKey;

    @ApiModelProperty("标签value值")
    private String tagValue;

    @ApiModelProperty("是否可用")
    private Boolean enabled;

    @ApiModelProperty("空间id")
    private String projectId;
}
