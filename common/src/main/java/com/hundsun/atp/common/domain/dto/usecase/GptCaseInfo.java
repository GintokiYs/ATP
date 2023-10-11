package com.hundsun.atp.common.domain.dto.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.common.domain.dto.usecase
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-11 22:17
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-11 22:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GptCaseInfo {
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
