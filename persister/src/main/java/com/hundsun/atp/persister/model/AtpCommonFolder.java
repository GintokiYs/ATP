package com.hundsun.atp.persister.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通用文件夹
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@TableName("atp_common_folder")
@ApiModel(value = "AtpCommonFolder对象", description = "通用文件夹")
public class AtpCommonFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("父文件夹id")
    private String parentId;

    @ApiModelProperty("文件夹名称")
    private String folderName;

    @ApiModelProperty("文件夹类型")
    private Integer folderType;

    private String parentUtreeid;

    @ApiModelProperty("前端utree展示用")
    private String utreeid;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    private Boolean enabled;

    @ApiModelProperty("空间ID")
    private String projectId;

    @ApiModelProperty("执行配置,Map<String,String>")
    private String executeConfig;

    @ApiModelProperty("创建者")
    private String createUser;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改者")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    private String remark;
}
