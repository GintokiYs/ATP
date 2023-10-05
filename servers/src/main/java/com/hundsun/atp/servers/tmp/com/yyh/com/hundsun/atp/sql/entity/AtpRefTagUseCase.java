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
 * 用例与标签之间的关联关系
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("atp_ref_tag_use_case")
@ApiModel(value = "AtpRefTagUseCase对象", description = "用例与标签之间的关联关系")
public class AtpRefTagUseCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty("用例id")
    private String caseId;

    @ApiModelProperty("空间id")
    private String projectId;
}
