package com.hundsun.atp.servers.service.business.caserun.impl.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.business.caserun.AbstractCaseRunHandle;
import com.hundsun.atp.servers.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class HttpPostCaseRunHandle extends AbstractCaseRunHandle<HttpPostCaseParams, HttpPostCaseResult> {

    private Logger logger = LoggerFactory.getLogger(HttpPostCaseRunHandle.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public HttpPostCaseResult excuteCase(HttpPostCaseParams httpPostCaseParams) throws IOException {
        String response = HttpUtils.sendPostRequest(httpPostCaseParams.getUrl(), httpPostCaseParams.gettCaseJson(), httpPostCaseParams.getRequestProperties());
        JsonNode jsonNode = null;
        try {
            jsonNode = OBJECT_MAPPER.readTree(response);
        } catch (JsonProcessingException e) {
            logger.error("接口请求结果解析异常,返回内容为: {}.", response);
            throw e;
        }
        HttpPostCaseResult result = new HttpPostCaseResult();
        result.setResponseBody(jsonNode);
        return result;
    }

    @Override
    public HttpPostCaseParams caseTransform(AtpUseCase atpUseCase, AtpCommonFolder atpCommonFolder) throws JsonProcessingException {
        HttpPostCaseParams httpPostCaseParams = new HttpPostCaseParams();
        httpPostCaseParams.settCaseJson(atpUseCase.getInterfaceContent());
        Map<String, String> executeConfig;
        try {
            TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>() {};
            executeConfig = OBJECT_MAPPER.readValue(atpCommonFolder.getExecuteConfig(), typeReference);
        } catch (JsonProcessingException e) {
            logger.error("测试集配置格式异常, 测试集名称：{}, 测试集配置：{}。", atpCommonFolder.getFolderName(), atpCommonFolder.getExecuteConfig());
            throw e;
        }
        String url = executeConfig.get("url");
        httpPostCaseParams.setUrl(url);
        return httpPostCaseParams;
    }


}
