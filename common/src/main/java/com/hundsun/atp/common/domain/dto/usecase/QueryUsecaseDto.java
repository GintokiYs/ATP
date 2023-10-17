package com.hundsun.atp.common.domain.dto.usecase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用例抽象类
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
public class QueryUsecaseDto implements Serializable {
    private static final long serialVersionUID = -1L;

    private String foldId;

//    private String projectId;

    private String name;

    private List<String> tagIdList;

    private List<String> checkResult;

    private Integer pageSize;

    private Integer pageNum;
}
