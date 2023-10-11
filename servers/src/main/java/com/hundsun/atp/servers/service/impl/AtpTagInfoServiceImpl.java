package com.hundsun.atp.servers.service.impl;


import com.hundsun.atp.api.AtpTagInfoService;
import com.hundsun.atp.servers.service.business.AtpUseCaseInstanceBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签明细 服务实现类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpTagInfoServiceImpl implements AtpTagInfoService {
    @Autowired
    private AtpUseCaseInstanceBusiness atpUseCaseInstanceBusiness;
}
