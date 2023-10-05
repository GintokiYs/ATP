package com.hundsun.atp.common.domain.dto.usecase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 用例
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
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "caseType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InterfaceUsecaseDto.class, name = "1")
})
public abstract class AbstractUsecaseDto implements Serializable {
    private String caseId;

    private String name;

    private String folderId;

    private String checkRule;

    private String caseType;

    private Map<String, String> executeConfig;

    private Boolean enabled;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;

    private String remark;
}
