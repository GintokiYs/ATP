package com.hundsun.atp.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.common.domain.dto
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-09 11:07
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-09 11:07
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@SuperBuilder
@ToString(callSuper =true)
@Data
@ApiModel(value = "基础DTO对象", description = "基础DTO对象")
@AllArgsConstructor
@NoArgsConstructor
public abstract class AtpBaseDto implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * token
     */
    @ApiModelProperty("token")
    private String userToken;

    /**
     * 操作用户id
     */
    @ApiModelProperty("操作用户id")
    private String operatorCode;

    /**
     * 空间ID
     */
    @ApiModelProperty("空间ID")
    private String projectId;
}
