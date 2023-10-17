package com.hundsun.atp.servers.service.convert;

import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseStatistics;
import com.hundsun.atp.common.domain.entity.usecase.AtpUseCaseWithInstance;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.persister.model.AtpUseCaseInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UseCaseConvert {
    UseCaseConvert INSTANCE = Mappers.getMapper(UseCaseConvert.class);

    AtpUseCaseStatistics enhanceStatistics(AtpUseCaseWithInstance atpUseCaseWithInstance);

}
