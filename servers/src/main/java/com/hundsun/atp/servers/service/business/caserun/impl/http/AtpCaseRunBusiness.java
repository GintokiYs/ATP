package com.hundsun.atp.servers.service.business.caserun.impl.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.servers.service.business.caserun.ICaseRun;
import com.hundsun.atp.servers.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AtpCaseRunBusiness implements ICaseRun<HttpCaseParams, String> {

    private Logger logger = LoggerFactory.getLogger(AtpCaseRunBusiness.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String excuteHttpPostCase(HttpCaseParams httpCaseParams) throws IOException {

        String result = HttpUtils.sendPostRequest(httpCaseParams.getUrl(), httpCaseParams.gettCaseJson(), httpCaseParams.getRequestProperties());
        return result;
    }




}
