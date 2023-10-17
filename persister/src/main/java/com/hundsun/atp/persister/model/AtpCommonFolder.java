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
import java.util.Date;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtpCommonFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("父文件夹id")
    @TableField(value = "parent_id")
    private String parentId;

    @ApiModelProperty("文件夹名称")
    @TableField(value = "folder_name")
    private String folderName;

    @ApiModelProperty("文件夹类型")
    @TableField(value = "folder_type")
    private Integer folderType;

    @TableField(value = "parent_utreeid")
    private String parentUtreeid;

    @ApiModelProperty("前端utree展示用")
    @TableField(value = "utreeid")
    private String utreeid;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    @TableField(value = "enabled")
    private Integer enabled;

    @ApiModelProperty("空间ID")
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 执行配置,Map<String,String>
     * @description:
     * http接口用例的配置结构:
     *      {"url":"http://xxxx/xx/xxx"}
     */
    @ApiModelProperty("执行配置,Map<String,String>")
    @TableField(value = "execute_config")
    private String executeConfig;

    @ApiModelProperty("创建者")
    @TableField(value = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty("修改者")
    @TableField(value = "update_user")
    private String updateUser;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("备注")
    @TableField(value = "remark")
    private String remark;
}
