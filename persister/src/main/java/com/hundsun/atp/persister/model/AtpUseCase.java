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
public class AtpUseCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("用例id")
    private String caseId;

    @ApiModelProperty("用例名称")
    private String name;

    @ApiModelProperty("所属用例集Id")
    private String folderId;

    @ApiModelProperty("检查规则")
    private String checkRule;

    @ApiModelProperty("用例类型")
    private String caseType;

    @ApiModelProperty("执行配置,Map<String,String>")
    private String executeConfig;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    private Boolean enabled;

    @ApiModelProperty("创建用户")
    private String createUser;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改用户")
    private String updateUser;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("用例详情(具体的接口请求内容)")
    private String interfaceContent;

    @ApiModelProperty("备注")
    private String remark;
}
