package com.hundsun.atp.servers.service.business.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import cn.hutool.core.collection.CollectionUtil;
import com.hundsun.atp.common.enums.UseCaseTypeEnum;
import com.hundsun.atp.servers.service.business.AbstractUseCaseBusiness;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UseCaseBusinessFactory {
    private Map<UseCaseTypeEnum, AbstractUseCaseBusiness> businessMap;

    @Autowired
    private List<AbstractUseCaseBusiness> useCaseBusinesses;

    @PostConstruct
    public void init() {
        if (CollectionUtil.isEmpty(useCaseBusinesses)) {
            return;
        }
        this.businessMap = new HashMap<>(useCaseBusinesses.size());
        for (AbstractUseCaseBusiness useCaseBusiness : useCaseBusinesses) {
            UseCaseTypeEnum useCaseTypeEnum = useCaseBusiness.getUseCaseTypeEnum();
            if (businessMap.get(useCaseTypeEnum) != null) {
                throw new RuntimeException("AbstractUseCaseBusiness ");
            }
            businessMap.put(useCaseTypeEnum, useCaseBusiness);
        }
    }

    public AbstractUseCaseBusiness buildBusiness(UseCaseTypeEnum useCaseTypeEnum) {
        if (this.businessMap.get(useCaseTypeEnum) == null) {
            throw new RuntimeException("AbstractUseCaseBusiness= " + useCaseTypeEnum);
        }
        return this.businessMap.get(useCaseTypeEnum);
    }
}