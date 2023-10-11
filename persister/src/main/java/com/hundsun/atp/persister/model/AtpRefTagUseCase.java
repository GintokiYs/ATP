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
 * 用例与标签之间的关联关系
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@TableName("atp_ref_tag_use_case")
@ApiModel(value = "AtpRefTagUseCase对象", description = "用例与标签之间的关联关系")
@AllArgsConstructor
@NoArgsConstructor
public class AtpRefTagUseCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("标签id")
    @TableField(value = "tag_id")
    private String tagId;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseId;

    @ApiModelProperty("空间id")
    @TableField(value = "project_id")
    private String projectId;
}
