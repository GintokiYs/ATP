package com.hundsun.atp.persister.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hundsun.atp.persister.model.AtpUseCase;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用例详情 Mapper 接口
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Mapper
public interface AtpUseCaseMapper extends BaseMapper<AtpUseCase> {

    void selectUseCaseInfo(String foldId, String name, String checkResult);
}
