package com.hundsun.atp.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.common.domain.entity
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-10-07 10:28
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-10-07 10:28
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtpUser {
    private long id;

    private String username;

    @JsonIgnore
    private String password;

}
