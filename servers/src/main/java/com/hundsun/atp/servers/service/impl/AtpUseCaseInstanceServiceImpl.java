package com.hundsun.atp.servers.service.impl;

import com.hundsun.atp.api.usecaseinstance.AtpUseCaseInstanceService;
import com.hundsun.atp.common.domain.dto.usecaseinstance.InterfaceUsecaseInstanceDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.util.RpcResultUtils;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import com.hundsun.atp.servers.service.business.AtpUseCaseInstanceBusiness;
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
    @Override
    public RpcResultDTO<Boolean> editInstanceTag(InterfaceUsecaseInstanceDto interfaceUsecaseInstanceDto) {
        return null;
    }
}
