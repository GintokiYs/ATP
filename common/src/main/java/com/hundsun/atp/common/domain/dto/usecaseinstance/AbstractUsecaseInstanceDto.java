package com.hundsun.atp.common.domain.dto.usecaseinstance;

import com.hundsun.atp.common.domain.dto.AtpBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpCommonFolder对象", description = "通用文件夹")
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUsecaseInstanceDto extends AtpBaseDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
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
    private Date startTime;

    @ApiModelProperty("停止时间")
    private Date stopTime;

    @ApiModelProperty("状态更新时间")
    private Date bussinessTime;

    @ApiModelProperty("停止用户")
    private String stopUser;
}
