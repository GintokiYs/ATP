package com.hundsun.atp.common.domain.vo.usecase;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hundsun.atp.common.domain.dto.usecase.GptCaseInfo;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InterfaceUsecaseVo extends AbstractUsecaseVo{


    /**
     * 普通接口用例描述详情
     */

    private GptCaseInfo gptCaseInfo;

}
