package com.hundsun.atp.persister.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用例与标签之间的关联关系 Mapper 接口
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Mapper
public interface AtpRefTagUseCaseMapper extends BaseMapper<AtpRefTagUseCase> {

}
