package com.hundsun.atp.servers.service.business;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hundsun.atp.persister.mapper.AtpTagInfoMapper;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.persister.model.AtpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtpUseCaseBusiness extends ServiceImpl<AtpUseCaseMapper, AtpUseCase> {

    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    public List<AtpUseCase> queryByCaseIds(ArrayList<String> caseIds) {
        List<AtpUseCase> atpUseCases = atpUseCaseMapper.selectByCaseIds(caseIds);
        return atpUseCases;
    }
}
