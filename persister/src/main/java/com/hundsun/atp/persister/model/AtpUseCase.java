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
import org.apache.ibatis.annotations.Delete;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用例详情
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@TableName("atp_use_case")
@ApiModel(value = "AtpUseCase对象", description = "用例详情")
@AllArgsConstructor
@NoArgsConstructor
public class AtpUseCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseId;

    @ApiModelProperty("用例名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("所属用例集Id")
    @TableField(value = "folder_id")
    private String folderId;

    @ApiModelProperty("检查规则")
    @TableField(value = "check_rule")
    private String checkRule;

    @ApiModelProperty("用例类型")
    @TableField(value = "case_type")
    private Integer caseType;

    @ApiModelProperty("执行配置,Map<String,String>")
    @TableField(value = "execute_config")
    private String executeConfig;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    @TableField(value = "enabled")
    private Integer enabled;

    @ApiModelProperty("创建用户")
    @TableField(value = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty("修改用户")
    @TableField(value = "update_user")
    private String updateUser;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("用例详情(具体的接口请求内容)")
    @TableField(value = "interface_content")
    private String interfaceContent;

    @ApiModelProperty("备注")
    @TableField(value = "remark")
    private String remark;
}
