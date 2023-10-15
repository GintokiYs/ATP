package com.hundsun.atp.servers.service.business;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.usecaseinstance.InterfaceUsecaseInstanceDto;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpUseCaseInstanceMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
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
@Service
public class AtpUseCaseInstanceBusiness extends ServiceImpl<AtpUseCaseInstanceMapper, AtpUseCaseInstance> {
}
