package com.hundsun.atp.common.domain.vo.usecase;

import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用例统计详情
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpUseCase对象的统计信息(带实例信息)", description = "AtpUseCase对象的统计信息(带实例信息)")
@AllArgsConstructor
@NoArgsConstructor
public class AtpUseCaseStatisticsVo extends AtpUseCaseWithInstance {

    @ApiModelProperty("实例总数")
    private Integer totalCount;

    @ApiModelProperty("成功执行的实例总数")
    private Integer successCount;

    @ApiModelProperty("成功执行的比例")
    private Integer successRate;

    @ApiModelProperty("失败执行的实例总数")
    private Integer failCount;

    @ApiModelProperty("错误执行的比例")
    private Integer failRate;
}