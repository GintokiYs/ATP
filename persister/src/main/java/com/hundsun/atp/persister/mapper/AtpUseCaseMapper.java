package com.hundsun.atp.persister.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import com.hundsun.atp.persister.model.AtpUseCase;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<AtpUseCaseWithInstance> selectUseCaseWithInstanceInfo(@Param("caseIdList") List<String> caseIdList,
                                                               @Param("checkResult") String checkResult);

    List<AtpUseCase> selectUseCaseInfo(@Param("folderId") String foldId,
                                       @Param("name") String name);
}
