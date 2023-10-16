package com.hundsun.atp.common.domain.dto.usecase;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 接口测试用例
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.api.usecase
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-09-27 14:47
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-09-27 14:47
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName(value = "1")
public class InterfaceUsecaseDto extends AbstractUsecaseDto {
    /**
     * 普通接口用例描述详情
     */
    private String interfaceContent;

    private GptCaseInfo gptCaseInfo;
}
