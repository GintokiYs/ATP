package com.hundsun.atp.common.domain.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.common.domain.dto.tag
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-15 15:58
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-15 15:58
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpTagInfo查询Dto对象", description = "AtpTagInfo查询Dto对象")
@AllArgsConstructor
@NoArgsConstructor
public class AtpTagInfoQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用例集Id")
    private String caseId;

    @ApiModelProperty("空间id")
    private String projectId;
}
