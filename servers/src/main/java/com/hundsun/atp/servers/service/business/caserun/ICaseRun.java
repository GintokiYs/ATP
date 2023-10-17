package com.hundsun.atp.servers.service.business.caserun;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;

import java.io.IOException;

public interface ICaseRun<IN, OUT> {

    /**
     * 用例执行方法
     * @param caseRunParams
     * @return
     * @throws IOException
     */
    OUT excuteCase(IN caseRunParams) throws IOException;

    /**
     * 根据用例信息/用例集信息生成用例执行参数
     * @param atpUseCase 用例信息
     * @param atpCommonFolder 用例集信息
     * @return
     */
    IN caseTransform(AtpUseCase atpUseCase, AtpCommonFolder atpCommonFolder) throws Exception;

}
