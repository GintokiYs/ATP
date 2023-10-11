package com.hundsun.atp.servers.service.impl;

import com.hundsun.atp.api.AtpUseCaseInstanceService;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用例实例 服务实现类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpUseCaseInstanceServiceImpl implements AtpUseCaseInstanceService {

    @Autowired
    private AtpTagInfoBusiness atpTagInfoBusiness;
}
