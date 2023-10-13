package com.hundsun.atp.servers.service.business.caserun.impl.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.servers.service.business.caserun.ICaseRun;
import com.hundsun.atp.servers.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpPostCaseRunBusiness extends ICaseRun<HttpPostCaseParams, JsonNode> {

    private Logger logger = LoggerFactory.getLogger(HttpPostCaseRunBusiness.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public JsonNode excuteHttpPostCase(HttpPostCaseParams httpPostCaseParams) throws IOException {
        String result = HttpUtils.sendPostRequest(httpPostCaseParams.getUrl(), httpPostCaseParams.gettCaseJson(), httpPostCaseParams.getRequestProperties());
        JsonNode jsonNode = null;
        try {
            jsonNode = OBJECT_MAPPER.readTree(result);
        } catch (JsonProcessingException e) {
            logger.error("接口请求结果解析异常,返回内容为: {}.", result);
            throw e;
        }
        return jsonNode;
    }




}
