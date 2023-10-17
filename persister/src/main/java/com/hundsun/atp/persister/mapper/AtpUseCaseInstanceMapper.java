package com.hundsun.atp.persister.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 测试用例实例 Mapper 接口
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Mapper
public interface AtpUseCaseInstanceMapper extends BaseMapper<AtpUseCaseInstance> {

    AtpUseCaseInstance selectByInstanceId(String instanceId);

}
