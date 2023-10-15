package com.hundsun.atp.common.domain.dto.usecase;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
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
public class DeleteUsecaseDto implements Serializable {
    private static final long serialVersionUID = -1L;

    private String id;

    private String operatorCode;

}
