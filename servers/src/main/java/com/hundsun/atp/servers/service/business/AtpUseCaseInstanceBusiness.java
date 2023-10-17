package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.usecaseinstance.InterfaceUsecaseInstanceDto;
import com.hundsun.atp.common.exception.PlatformException;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpUseCaseInstanceMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 测试用例实例 处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Slf4j
@Service
public class AtpUseCaseInstanceBusiness extends ServiceImpl<AtpUseCaseInstanceMapper, AtpUseCaseInstance> {

    public AtpUseCaseInstance selectByInstanceId(String instanceId){
        AtpUseCaseInstance atpUseCaseInstance;
        try {
            atpUseCaseInstance = baseMapper.selectByInstanceId(instanceId);
        } catch (Exception e){
            log.error("查询用例实例失败,用例实例id为：{}", instanceId);
            throw new PlatformException("查询用例实例失败", e);
        }
        return atpUseCaseInstance;
    }


}
