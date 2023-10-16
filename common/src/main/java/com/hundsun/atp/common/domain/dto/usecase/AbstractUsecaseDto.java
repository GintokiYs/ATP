package com.hundsun.atp.common.domain.dto.usecase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "caseType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InterfaceUsecaseDto.class, name = "1")
})
public abstract class AbstractUsecaseDto implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用例id")
    private String caseId;

    @ApiModelProperty("用例名称")
    private String name;

    @ApiModelProperty("所属用例集Id")
    private String folderId;

    @ApiModelProperty("检查规则")
    private String checkRule;

    @ApiModelProperty("用例类型, 1:接口测试用例")
    private Integer caseType;

    @ApiModelProperty("执行配置,Map<String,String>")
    private Map<String, String> executeConfig;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    private List<AtpTagInfoDto> tags;

    @ApiModelProperty("操作用户")
    private String operatorCode;

    @ApiModelProperty("备注")
    private String remark;
}
