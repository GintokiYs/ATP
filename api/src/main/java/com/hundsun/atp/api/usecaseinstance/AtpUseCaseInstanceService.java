package com.hundsun.atp.api.usecaseinstance;


import com.hundsun.atp.common.domain.dto.usecaseinstance.InterfaceUsecaseInstanceDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;

/**
 * <p>
 * 测试用例实例 服务类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
public interface AtpUseCaseInstanceService {

    RpcResultDTO<Boolean> editInstanceTag(InterfaceUsecaseInstanceDto interfaceUsecaseInstanceDto);
}
