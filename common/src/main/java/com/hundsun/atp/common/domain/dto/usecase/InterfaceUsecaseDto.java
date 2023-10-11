package com.hundsun.atp.common.domain.dto.usecase;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    /**
     * GPT生成的用例的前缀名称
     */
    private String gptCaseNamePrefix;

    /**
     * GPT生成的用例时的描述信息
     */
    private String gptInterfaceDes;

    /**
     * GPT生成的用例时的补充说明
     */
    private String gptInterfaceComplement;
    /**
     * 期望GPT生成的用例个数
     */
    private Integer gptCaseNum;
}
