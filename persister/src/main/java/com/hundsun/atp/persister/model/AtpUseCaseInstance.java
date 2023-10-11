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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 测试用例实例
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@TableName("atp_use_case_instance")
@ApiModel(value = "AtpUseCaseInstance对象", description = "测试用例实例")
@AllArgsConstructor
@NoArgsConstructor
public class AtpUseCaseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("实例id")
    @TableField(value = "instance_id")
    private String instanceId;

    @ApiModelProperty("所属用例Id")
    @TableField(value = "use_case_id")
    private String useCaseId;

    @ApiModelProperty("执行状态")
    @TableField(value = "execute_status")
    private String executeStatus;

    @ApiModelProperty("启动用户")
    @TableField(value = "start_user")
    private String startUser;

    @ApiModelProperty("启动时间")
    @TableField(value = "start_time")
    private Date startTime;

    @ApiModelProperty("停止时间")
    @TableField(value = "stop_time")
    private Date stopTime;

    @ApiModelProperty("状态更新时间")
    @TableField(value = "bussiness_time")
    private Date bussinessTime;

    @ApiModelProperty("停止用户")
    @TableField(value = "stop_user")
    private String stopUser;
}
