package com.yyh.com.hundsun.atp.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试用例实例
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("atp_use_case_instance")
@ApiModel(value = "AtpUseCaseInstance对象", description = "测试用例实例")
public class AtpUseCaseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("实例id")
    private String instanceId;

    @ApiModelProperty("所属用例Id")
    private String useCaseId;

    @ApiModelProperty("执行状态")
    private String executeStatus;

    @ApiModelProperty("启动用户")
    private String startUser;

    @ApiModelProperty("启动时间")
    private LocalDateTime startTime;

    @ApiModelProperty("停止时间")
    private LocalDateTime stopTime;

    @ApiModelProperty("状态更新时间")
    private LocalDateTime bussinessTime;

    @ApiModelProperty("停止用户")
    private String stopUser;
}
