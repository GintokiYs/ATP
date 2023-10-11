package com.hundsun.atp.api;

import com.hundsun.atp.common.domain.dto.usecase.AbstractUsecaseDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;

public interface AtpUsecaseService {
    // 用例新建（编辑、删除、查询）
    public Boolean createUsecase(AbstractUsecaseDto usecase);

    // 用例执行、用例详情查询
}
