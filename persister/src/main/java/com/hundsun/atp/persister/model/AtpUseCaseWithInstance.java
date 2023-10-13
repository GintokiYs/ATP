package com.hundsun.atp.persister.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

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
@ApiModel(value = "AtpUseCase对象(带实例信息)", description = "用例详情")
@AllArgsConstructor
@NoArgsConstructor
public class AtpUseCaseWithInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
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
    private Integer caseType;

    @ApiModelProperty("执行配置,Map<String,String>")
    private String executeConfig;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    private Integer enabled;

    @ApiModelProperty("创建用户")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改用户")
    private String updateUser;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("用例详情(具体的接口请求内容)")
    private String interfaceContent;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("实例ID")
    private String instanceId;

    @ApiModelProperty("实例执行结果")
    private String executeStatus;

    @ApiModelProperty("实例变更时间")
    private String bussinessTime;
}
